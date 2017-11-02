package org.lhr.simpleserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author : ChinaLHR
 * @Date : Create in 11:42 2017/11/2
 * @Email : 13435500980@163.com
 */
public class RpcBootStrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcBootStrap.class);

    public static void main(String[] args) {
        LOGGER.debug("start server");
        new ClassPathXmlApplicationContext("spring.xml");
    }
}
