package com.friend_map.components.beans.bootstrap;

import com.friend_map.business_layer.handler.tcp.SendServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


@Service
public class BootstrapSendServer {

    @Autowired
    EventLoopGroup eventLoopGroup;

    @Bean
    @Autowired
    public ServerBootstrap serverBootstrap(SendServerInitializer channelServerInitializer) throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(channelServerInitializer);
        //bootstrap.bind(5000).sync().channel().closeFuture().await();
        return serverBootstrap;
    }
}
