package com.friend_map.components.beans.bootstrap;

import com.friend_map.business_layer.handler.udp.ReceivedServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


@Service
public class BootstrapReceivedServer {

    @Autowired
    ReceivedServerHandler receivedServerHandler;
    @Autowired
    EventLoopGroup eventLoopGroup;

    //private static final int PORT = Integer.parseInt(System.getProperty("port", "5000"));

    @Bean
    public EventLoopGroup eventLoopGroup() {
        return new NioEventLoopGroup();
    }

    @Bean
    public Bootstrap bootstrap() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioDatagramChannel.class)
                .handler(receivedServerHandler);
        //bootstrap.bind(5000).sync().channel().closeFuture().await();
        return bootstrap;
    }


}