package com.myproj.service;

/**
 * @Author LettleCadet
 * @Date 2019/2/24
 */
public interface RegisterService
{

    /**
     * 通过Curator完成服务注册成功后，zk会立刻同步节点信息到注册的主机上，但是如果直接在linux上，zk不会同步节点信息
     */
    public Boolean registerService();

}
