package org.example;


import org.example.netty.NettyServer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Sylphy
 * @Description
 * @create 2025/7/30
 */
@Component
public class Init implements ApplicationRunner {
    @Resource
    private NettyServer nettyServer;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        nettyServer.startNetty();
    }
}
