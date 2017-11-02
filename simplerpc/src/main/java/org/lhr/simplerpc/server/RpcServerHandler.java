package org.lhr.simplerpc.server;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;
import org.lhr.simplerpc.enity.RpcRequest;
import org.lhr.simplerpc.enity.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @Author : ChinaLHR
 * @Date : Create in 10:07 2017/11/2
 * @Email : 13435500980@163.com
 *
 * RPC服务端请求处理器（处理RPC请求)
 * 接受 Request ，并执行反射调用，封装结果result到Response,传输给客户端
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest>{

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServerHandler.class);

    private final Map<String,Object> handlerMap;

    public RpcServerHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, RpcRequest request) throws Exception {
        //创建并初始化RPC响应对象
        RpcResponse response = new RpcResponse();
        response.setRequestId(request.getRequestId());
        try {
            Object result = handle(request);
            response.setResult(result);
        }catch (Exception e){
            LOGGER.error("handler result failure ", e);
            response.setException(e);
        }
        //写入RPC响应对象并自动关闭连接
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }


    private Object handle(RpcRequest request) throws InvocationTargetException {
        //创建服务对象
        String serviceName = request.getInterfaceName();
        String serviceVersion = request.getServiceVersion();
        if (!StringUtils.isEmpty(serviceVersion)){
            serviceName += "-" + serviceVersion;
        }
        Object serviceBean = handlerMap.get(serviceName);
        if (serviceBean == null)
            throw new RuntimeException(String.format("can not find service bean by key : %s",serviceName));

        //获取反射调用所需要的参数
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();
        //执行反射调用
        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        return serviceFastMethod.invoke(serviceBean,parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("server caught exception",cause);
        ctx.close();
    }
}
