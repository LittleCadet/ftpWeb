package com.myproj.tools;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceInstanceBuilder;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.omg.CORBA.ServiceDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * zk的工具类：实现zk的服务注册，超时重连等功能
 *
 * @Author LettleCadet
 * @Date 2019/2/13
 */
public class CuratorUtil
{
    private static final Logger logger = LoggerFactory.getLogger(CuratorUtil.class);

    private static ServiceDiscovery<ServiceDetail> serviceDiscovery;

    private static String separator = "/";

    /**
     * 获取serviceDiscovery
     */
    public static ServiceDiscovery getServiceDiscovery(CuratorFramework zkClient,String rootNode)
        throws Exception
    {
        serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceDetail.class)
            .client(zkClient)
            .serializer(new JsonInstanceSerializer<ServiceDetail>(ServiceDetail.class))
            .basePath(rootNode)
            .build();

        serviceDiscovery.start();

        return serviceDiscovery;
    }

    /**
     * 注册服务
     */
    public static void register(String serviceInstance,String host,Integer port)
        throws Exception
    {
        /**
         * 指定服务的 地址，端口，名称
         */
        ServiceInstanceBuilder<ServiceDetail> sib = ServiceInstance.builder();
        sib.address(host);
        sib.port(port);
        sib.name(serviceInstance);

        sib.payload(new ServiceDetail());

        ServiceInstance<ServiceDetail> instance = sib.build();
        //服务注册
        serviceDiscovery.registerService(instance);
    }

    /**
     * 改服务
     *
     * @param instance
     * @throws Exception
     */
    public static void updateService(ServiceInstance<ServiceDetail> instance)
        throws Exception
    {
        serviceDiscovery.updateService(instance);
    }

    /**
     * 注册服务
     *
     * @param instance
     * @throws Exception
     */
    public static void registerService(ServiceInstance<ServiceDetail> instance)
        throws Exception
    {
        serviceDiscovery.registerService(instance);
    }

    /**
     * 删除服务
     *
     * @param instance
     * @throws Exception
     */
    public static void unregisterService(ServiceInstance<ServiceDetail> instance)
        throws Exception
    {
        serviceDiscovery.unregisterService(instance);
    }

    /**
     * 查服务
     *
     * @param name
     * @return
     * @throws Exception
     */
    public static Collection<ServiceInstance<ServiceDetail>> queryForInstances(String name)
        throws Exception
    {
        return serviceDiscovery.queryForInstances(name);
    }

    /**
     * 查服务
     *
     * @param name
     * @param id
     * @return
     * @throws Exception
     */
    public static ServiceInstance<ServiceDetail> queryForInstance(String name, String id)
        throws Exception
    {
        return serviceDiscovery.queryForInstance(name, id);
    }

    /**
     * 获取注册的服务
     * @param curatorClient
     * @throws Exception
     */
    public static List<String> getServices(CuratorFramework curatorClient,String nodePath)
        throws Exception
    {
        return curatorClient.getChildren().forPath(nodePath);
    }

    /**
     * 获取指定节点的内容
     *
     * @param curatorClient
     * @return
     */
    public static byte[] getNodeContent(CuratorFramework curatorClient, String nodePath)
        throws Exception
    {
        byte[] datas = null;
        datas = curatorClient.getData().forPath(nodePath);

        if (0 != datas.length)
        {
            for (byte data : datas)
            {
                System.out.println("data:" + data);
                if(logger.isDebugEnabled())
                {
                    logger.debug("CuratorTools.getNodeContent():the node content is :" + data);
                }
            }
        }
        else
        {
            if(logger.isDebugEnabled())
            {
                logger.debug("CuratorTools.getNodeContent():the node content is empty");
            }
        }
        return datas;
    }

    /**
     * 关闭资源
     *
     * @param curatorClient
     */
    public static void closeResource(CuratorFramework curatorClient, ServiceDiscovery serviceDiscovery)
        throws IOException
    {
        if (null != serviceDiscovery)
        {
            serviceDiscovery.close();
        }
        if (null != curatorClient)
        {
            curatorClient.close();
        }
    }
}
