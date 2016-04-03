package com.friend_map.components.beans.bootstrap;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class ServersInitializer {

    @Autowired
    Bootstrap bootstrap;
    @Autowired
    ServerBootstrap serverBootstrap;
    @Autowired
    EventLoopGroup eventLoopGroup;

    @PreDestroy
    public void shutdownBootstrapServer() throws Exception {
        eventLoopGroup.shutdownGracefully();
    }

    @PostConstruct
    public void initialize() throws InterruptedException {
        bindBootstrap();
        bindServerBootstrap();
    }

    @Async
    void bindBootstrap() throws InterruptedException {
        new Thread(() -> {
            try {
                bootstrap.bind(5000).sync().channel().closeFuture().await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ).start();
    }

    @Async
    void bindServerBootstrap() throws InterruptedException {
        new Thread(() -> {
            try {
                serverBootstrap.bind(5001).sync().channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ).start();
    }
}
