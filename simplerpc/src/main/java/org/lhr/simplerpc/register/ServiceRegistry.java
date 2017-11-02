package org.lhr.simplerpc.register;

/**
 * @Author : ChinaLHR
 * @Date : Create in 16:43 2017/11/1
 * @Email : 13435500980@163.com
 *
 * 服务注册接口
 */
public interface ServiceRegistry {

    /**
     * 服务注册接口
     * @param serviceName 服务名称
     * @param serviceAddress 服务地址
     */
    void register(String serviceName,String serviceAddress);

}
