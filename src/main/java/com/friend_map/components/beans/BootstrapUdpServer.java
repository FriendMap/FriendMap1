package com.friend_map.components.beans;

import com.friend_map.business_layer.handler.UdpServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Service
public class BootstrapUdpServer {

    @Autowired
    UdpServerHandler udpServerHandler;
    @Autowired
    EventLoopGroup eventLoopGroup;
    @Autowired
    Bootstrap bootstrap;

    //private static final int PORT = Integer.parseInt(System.getProperty("port", "5000"));

    @Bean
    public EventLoopGroup eventLoopGroup() {
        return new NioEventLoopGroup();
    }

    @Bean
    public Bootstrap configurationBootstrapServer() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioDatagramChannel.class)
                .handler(udpServerHandler);
        //bootstrap.bind(5000).sync().channel().closeFuture().await();
        return bootstrap;
    }

    @PreDestroy
    public void shutdownBootstrapServer() throws Exception {
        eventLoopGroup.close();
    }

    @PostConstruct
    public void initialize() throws InterruptedException {
        bind();
    }

    @Async
    void bind() throws InterruptedException {
        new Thread(() -> {
            try {
                bootstrap.bind(5000).sync().channel().closeFuture().await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ).start();
    }
}