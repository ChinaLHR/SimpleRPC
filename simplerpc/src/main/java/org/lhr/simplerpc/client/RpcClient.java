package org.lhr.simplerpc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.lhr.simplerpc.codec.RpcDecoder;
import org.lhr.simplerpc.codec.RpcEncoder;
import org.lhr.simplerpc.enity.RpcRequest;
import org.lhr.simplerpc.enity.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author : ChinaLHR
 * @Date : Create in 10:47 2017/11/2
 * @Email : 13435500980@163.com
 *
 * RPC客户端
 */
public class RpcClient extends SimpleChannelInboundHandler<RpcResponse>{

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcClient.class);

    private final String host;

    private final int port;

    private RpcResponse rpcResponse;

    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse response) throws Exception {
        this.rpcResponse = response;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("api caught exception ",cause);
        ctx.close();
    }

    public RpcResponse send(RpcRequest request) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建并初始化Netty客户端BootStrap对象
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel sc) throws Exception {
                    ChannelPipeline pipeline = sc.pipeline();
                    pipeline.addLast(new RpcEncoder(RpcRequest.class));//编码RPC请求
                    pipeline.addLast(new RpcDecoder(RpcResponse.class));//解码RPC响应
                    pipeline.addLast(RpcClient.this);//处理RPC响应
                }
            });
            bootstrap.option(ChannelOption.TCP_NODELAY,true);
            //连接RPC服务器
            ChannelFuture future = bootstrap.connect(host, port).sync();
            //写入RPC请求数据并关闭连接
            Channel channel = future.channel();
            channel.writeAndFlush(request).sync();
            channel.closeFuture().sync();
            //返回RPC响应对象
            return rpcResponse;
        }finally {
            group.shutdownGracefully();
        }
    }
}
