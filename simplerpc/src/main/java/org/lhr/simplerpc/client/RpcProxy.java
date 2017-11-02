package org.lhr.simplerpc.client;

import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.Proxy;
import org.lhr.simplerpc.enity.RpcRequest;
import org.lhr.simplerpc.enity.RpcResponse;
import org.lhr.simplerpc.register.ServiceDiscovery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @Author : ChinaLHR
 * @Date : Create in 10:32 2017/11/2
 * @Email : 13435500980@163.com
 *
 * RPC代理（用于创建RPC代理）
 */
public class RpcProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcProxy.class);

    private String serviceAddress;

    private ServiceDiscovery serviceDiscovery;

    public RpcProxy(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public RpcProxy(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    @SuppressWarnings("unchecked")
    public <T>T create(final Class<?> interfaceClass) {return create(interfaceClass,"");}

    @SuppressWarnings("unchecked")
    public <T>T create(final Class<?> interfaceClass,final String serviceVersion){
        //创建动态代理
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //创建RPC请求对象并设置请求属性
                        RpcRequest request = new RpcRequest();
                        request.setRequestId(UUID.randomUUID().toString());
                        request.setInterfaceName(method.getDeclaringClass().getName());
                        request.setServiceVersion(serviceVersion);
                        request.setMethodName(method.getName());
                        request.setParameterTypes(method.getParameterTypes());
                        request.setParameters(args);
                        //获取RPC服务地址
                        if (serviceDiscovery != null){
                            String serviceName = interfaceClass.getName();
                            if (!StringUtils.isEmpty(serviceVersion))
                            {
                                serviceName += "-" +serviceVersion;
                            }
                            serviceAddress = serviceDiscovery.discover(serviceName);
                            LOGGER.debug("discover service :{} ——> {}",serviceName,serviceAddress);
                        }
                        if (StringUtils.isEmpty(serviceAddress))
                        {
                            throw new RuntimeException("server address is empty");
                        }
                        // 从 RPC 服务地址中解析主机名与端口号
                        String[] array = StringUtils.split(serviceAddress, ":");
                        String host = array[0];
                        int port = Integer.parseInt(array[1]);
                        //创建RPC客户端对象并发送RPC请求
                        RpcClient client = new RpcClient(host, port);
                        long time = System.currentTimeMillis();
                        RpcResponse response = client.send(request);
                        LOGGER.debug("time : {} ms ",System.currentTimeMillis() - time);
                        if (response == null)
                            throw new RuntimeException("response is null");
                        //返回RPC响应结果
                        if (response.hasException()){
                            throw response.getException();
                        }else {
                            return response.getResult();
                        }
                    }

                }
        );
    }
}
