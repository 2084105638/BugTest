package org.example.netty;


import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;


/**
 * @author Sylphy
 * @Description
 * @create 2025/7/21
 */
@Component
public class ChannelContextUtils {
    
    public static final ConcurrentHashMap<String, Channel> MAP_USERID_CHANNEL = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, ChannelGroup> MAP_GROUPID_CHANNEL_GROUP = new ConcurrentHashMap<>();
    
    public void add(Channel channel) {
        String userId = "U123";
        MAP_USERID_CHANNEL.put(userId, channel);
    }
    
    public void removeContext(String userId) {
        System.out.println("ChannelContextUtils实例hash：" + this.hashCode());
        
        Channel channel = MAP_USERID_CHANNEL.get(userId);
        if (channel == null) {
            System.out.println("找不到channel");
            return;
        }
        System.out.println("channelId = " + channel.id());
        MAP_USERID_CHANNEL.remove(userId);
    }
    
}
