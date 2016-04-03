package com.friend_map.business_layer.handler;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import org.springframework.stereotype.Service;

public class ChannelListService {

    public static final ChannelGroup channels = new DefaultChannelGroup();

    public static ChannelGroup getChannels() {
        return channels;
    }

    public static Channel get(int i) {
        return channels.find(i);
    }

    public static void add(Channel channel) {
        channels.add(channel);
    }

    public static void remove(Channel channel) {
        channels.remove(channel);
    }
}
