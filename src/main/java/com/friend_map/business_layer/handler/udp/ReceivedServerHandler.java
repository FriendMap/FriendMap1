package com.friend_map.business_layer.handler.udp;

import com.friend_map.business_layer.handler.Coordinates;
import com.friend_map.persistence_layer.pojo.coordinate.Coordinate;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ReceivedServerHandler extends ChannelInboundMessageHandlerAdapter<DatagramPacket> {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    Gson gson;

    private static final String coordinate_key = "coordinate_key";

    @Override
    public void messageReceived(ChannelHandlerContext ctx, DatagramPacket packet) {
        String message = packet.content().toString(CharsetUtil.UTF_8);
        Coordinate coordinate = gson.fromJson(message, Coordinate.class);
        put(coordinate);
    }

    @SuppressWarnings("unchecked")
    @Async
    void put(Coordinate coordinate) {
        //String json = gson.toJson(coordinate);
        String id = coordinate.getUser().getId().toString();
        //System.out.println(json);
        System.out.println(id);
        Coordinates.map.put(id, coordinate);
        //redisTemplate.opsForHash().put(coordinate_key, id, json);
    }

}