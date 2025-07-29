package org.example.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.example.netty.handler.WebSocketHandler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Sylphy
 * @Description
 * @create 2025/7/16
 */
@Component
public class NettyServer {
    private final NioEventLoopGroup nioBossEventLoopGroup = new NioEventLoopGroup(1);
    private final NioEventLoopGroup nioWorkerEventLoopGroup = new NioEventLoopGroup();
    @Resource
    private WebSocketHandler webSocketHandler;
    
    @PreDestroy
    private void distort() {
    }
    
    @Async
    public void startNetty() throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(nioBossEventLoopGroup, nioWorkerEventLoopGroup)
                .channel(NioServerSocketChannel.class)
//                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new HttpServerCodec())
                                .addLast(new HttpObjectAggregator(64 * 104))
                                .addLast(new IdleStateHandler(20, 20, 20, TimeUnit.SECONDS))
                                //将 http 协议升级为 ws 协议
                                .addLast(new WebSocketServerProtocolHandler("/ws", null, true, 64 * 1024, true, true))
                                .addLast(webSocketHandler);
                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind(5051).sync();
        channelFuture.channel().closeFuture().sync();
    }
}
