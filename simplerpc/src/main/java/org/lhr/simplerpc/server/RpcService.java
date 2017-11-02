package org.lhr.simplerpc.server;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author : ChinaLHR
 * @Date : Create in 16:20 2017/11/1
 * @Email : 13435500980@163.com
 *
 * RPC服务注解定义
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcService {

    /**
     * 服务接口类
     * @return
     */
    Class<?> value();

    /**
     * 服务版本号
     * @return
     */
    String verson() default "";
}
