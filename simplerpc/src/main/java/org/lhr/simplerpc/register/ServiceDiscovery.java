package org.lhr.simplerpc.register;

/**
 * @Author : ChinaLHR
 * @Date : Create in 16:50 2017/11/1
 * @Email : 13435500980@163.com
 *
 * 服务发现接口
 */
public interface ServiceDiscovery {

    /**
     * 根据服务名称查找服务地址
     *
     * @param serviceName
     * @return
     */
    String discover(String serviceName);

}
