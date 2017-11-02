package org.lhr.simplerpc.register;

import org.I0Itec.zkclient.ZkClient;
import org.lhr.simplerpc.common.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author : ChinaLHR
 * @Date : Create in 16:51 2017/11/1
 * @Email : 13435500980@163.com
 */
public class ZooKeeperServiceRegistry implements ServiceRegistry{

    private static final Logger LOGGER = LoggerFactory.getLogger(ZooKeeperServiceRegistry.class);

    private final ZkClient zkClient;

    public ZooKeeperServiceRegistry(String zkAddress) {
        //创建ZooKeeper客户端
        zkClient = new ZkClient(zkAddress, Constant.ZK_SESSION_TIMEOUT,Constant.ZK_CONNECTION_TIMEOUT);
        LOGGER.debug("connect zookeeper");
    }

    @Override
    public void register(String serviceName, String serviceAddress) {
        //创建registry 节点（持久）
        String registryPath = Constant.ZK_REGISTRY_PATH;
        if (!zkClient.exists(registryPath)) {
            zkClient.createPersistent(registryPath);
            LOGGER.debug("create registry node: {}", registryPath);
        }
        //创建 service 节点（持久）
        String servicePath = registryPath + "/" + serviceName;
        if (!zkClient.exists(servicePath)) {
            zkClient.createPersistent(servicePath);
            LOGGER.debug("create service node: {}", servicePath);
        }
        // 创建 address 节点（临时）
        String addressPath = servicePath + "/address-";
        String addressNode = zkClient.createEphemeralSequential(addressPath, serviceAddress);
        LOGGER.debug("create address node: {}", addressNode);
    }
}
