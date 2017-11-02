package org.lhr.simpleclient;

import org.lhr.simplerpc.client.RpcProxy;
import org.lhr.simplerpcapi.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author : ChinaLHR
 * @Date : Create in 12:15 2017/11/2
 * @Email : 13435500980@163.com
 */
public class ClientBootStrap {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        RpcProxy rpcProxy = context.getBean(RpcProxy.class);

        HelloService helloService = rpcProxy.create(HelloService.class);
        String result = helloService.hello("lhr");
        System.out.println("进行了远程方法调用,返回值："+result);

        System.exit(0);
    }
}
