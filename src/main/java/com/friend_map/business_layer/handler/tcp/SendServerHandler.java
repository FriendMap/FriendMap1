package com.friend_map.business_layer.handler.tcp;


import com.friend_map.business_layer.coordinate.FriendCoordinateService;
import com.friend_map.business_layer.handler.ChannelListService;
import com.friend_map.business_layer.handler.UserKeyService;
import com.friend_map.components.CommandStatusResult;
import com.friend_map.components.converter.JsonConverter;
import com.friend_map.components.enums.CommandStatus;
import com.friend_map.persistence_layer.entities.user.User;
import com.google.gson.Gson;
import io.netty.channel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@ChannelHandler.Sharable
@Scope("prototype")
public class SendServerHandler extends ChannelInboundMessageHandlerAdapter<String> {

    Logger logger = Logger.getLogger(SendServerHandler.class.getName());

    @Autowired
    JsonConverter jsonConverter;
    @Autowired
    FriendCoordinateService coordinateService;
    @Autowired
    Gson gson;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        ctx.channel().write("[SERVER] " + incoming.remoteAddress() + gson.toJson(new CommandStatusResult(CommandStatus.OK)) + "\n");
        logger.info(incoming.remoteAddress() + " has joined ");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        logger.info(incoming.remoteAddress() + " has remove ");
        ctx.channel().write("[SERVER] " + incoming.remoteAddress() + " Bye bey");
        ChannelListService.remove(ctx.channel());
    }

    @Override
    public void messageReceived(ChannelHandlerContext channelHandlerContext, String message) throws Exception {
        logger.info("MessageReceived: " + message);
        Channel incoming = channelHandlerContext.channel();
        User user = UserKeyService.userMap.get(message);
        if (user != null) {
            try {
                String json = gson.toJson(coordinateService.getFriendsCoordinates(message));
                logger.info(json);
                logger.info(String.valueOf(coordinateService.getFriendsCoordinates(message).size()));
                incoming.write(coordinateService.getFriendsCoordinates(message) + "\n");
                incoming.write(json + "\n");
                incoming.write(coordinateService.getFriendsCoordinates(message).toString() + "\n");
            } catch (Exception e) {
                e.printStackTrace();
                channelHandlerContext.close();
            }
        } else {
            channelHandlerContext.channel().write("[SERVER] " + incoming.remoteAddress() + gson.toJson(new CommandStatusResult(CommandStatus.ACCESS_DENIED)) + "\n");
            channelHandlerContext.close();
        }
    }
}
