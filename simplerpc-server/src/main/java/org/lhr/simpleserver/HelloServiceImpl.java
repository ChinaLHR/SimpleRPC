package org.lhr.simpleserver;

import org.lhr.simplerpc.server.RpcService;
import org.lhr.simplerpcapi.HelloService;

/**
 * @Author : ChinaLHR
 * @Date : Create in 11:47 2017/11/2
 * @Email : 13435500980@163.com
 */

@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService{
    @Override
    public String hello(String name) {
        return "Hello ! " + name;
    }
}
