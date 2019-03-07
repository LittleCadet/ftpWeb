package com.myproj.entity;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * zk实体类
 *
 * @Author LettleCadet
 * @Date 2019/2/13
 */
public class CuratorClient
{
    private CuratorFramework curatorClient;

    //使用sringBoot完成属性值的自动注入时，属性值不能使用static修饰，因为会自动初始化为null
    //application.properties会默认使用父工程的
    //zk服务器ip
    public String zkServer;

    //zk端口号
    private String port;

    //会话超时时间【至关重要：如果会话时间短了，可能连注册服务都不行】
    private Integer sessionTimeOutMs;

    //连接超时时间
    private Integer connectTimeOutMs;

    //重连次数【针对会话超时】
    private Integer reTryTimes;

    private Integer baseSleepTimeMs;

    private String rootNode;

    private String singal = ":";

    public CuratorFramework init()
    {
        //4种重连策略:
        //1.RetryUntilElapsed(int maxElapsedTimeMs, int sleepMsBetweenRetries) ：指定时间内，每隔一段时间重连一次，超过指定时间，即放弃重连
        //2.RetryNTimes(int n, int sleepMsBetweenRetries)：指定重连次数
        //3.RetryOneTime(int sleepMsBetweenRetry)：重试一次
        //4.ExponentialBackoffRetry
        //ExponentialBackoffRetry(int baseSleepTimeMs, int maxRetries) ：每隔多久，重连几次
        //ExponentialBackoffRetry(int baseSleepTimeMs, int maxRetries, int maxSleepMs)
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, reTryTimes);
        curatorClient = CuratorFrameworkFactory.builder()
            .connectString(zkServer + singal + port)
            .retryPolicy(retryPolicy)
            .sessionTimeoutMs(sessionTimeOutMs)
            .connectionTimeoutMs(connectTimeOutMs)
            .build();
        curatorClient.start();

        return curatorClient;
    }

    public CuratorFramework getClient()
    {
        return curatorClient;
    }

    /**
     * 关闭客户端
     */
    public void stop()
    {
        curatorClient.close();
    }

    /**
     * spring bean注入，依靠无参构造
     */
    public CuratorClient()
    {
    }

    public CuratorClient(String zkServer, String port, Integer sessionTimeOutMs, Integer connectTimeOutMs,
        Integer reTryTimes, Integer baseSleepTimeMs, String rootNode)
    {
        this.zkServer = zkServer;
        this.port = port;
        this.sessionTimeOutMs = sessionTimeOutMs;
        this.connectTimeOutMs = connectTimeOutMs;
        this.reTryTimes = reTryTimes;
        this.baseSleepTimeMs = baseSleepTimeMs;
        this.rootNode = rootNode;
    }

    public String getZkServer()
    {
        return zkServer;
    }

    public void setZkServer(String zkServer)
    {
        this.zkServer = zkServer;
    }

    public String getPort()
    {
        return port;
    }

    public void setPort(String port)
    {
        this.port = port;
    }

    public CuratorFramework getZkClient()
    {
        return curatorClient;
    }

    public void setZkClient(CuratorFramework curatorClient)
    {
        this.curatorClient = curatorClient;
    }

    public Integer getSessionTimeOutMs()
    {
        return sessionTimeOutMs;
    }

    public void setSessionTimeOutMs(Integer sessionTimeOutMs)
    {
        this.sessionTimeOutMs = sessionTimeOutMs;
    }

    public Integer getConnectTimeOutMs()
    {
        return connectTimeOutMs;
    }

    public void setConnectTimeOutMs(Integer connectTimeOutMs)
    {
        this.connectTimeOutMs = connectTimeOutMs;
    }

    public Integer getReTryTimes()
    {
        return reTryTimes;
    }

    public void setReTryTimes(Integer reTryTimes)
    {
        this.reTryTimes = reTryTimes;
    }

    public int getBaseSleepTimeMs()
    {
        return baseSleepTimeMs;
    }

    public void setBaseSleepTimeMs(int baseSleepTimeMs)
    {
        this.baseSleepTimeMs = baseSleepTimeMs;
    }

    public CuratorFramework getCuratorClient()
    {
        return curatorClient;
    }

    public void setCuratorClient(CuratorFramework curatorClient)
    {
        this.curatorClient = curatorClient;
    }

    public void setBaseSleepTimeMs(Integer baseSleepTimeMs)
    {
        this.baseSleepTimeMs = baseSleepTimeMs;
    }

    public String getRootNode()
    {
        return rootNode;
    }

    public void setRootNode(String rootNode)
    {
        this.rootNode = rootNode;
    }
}
