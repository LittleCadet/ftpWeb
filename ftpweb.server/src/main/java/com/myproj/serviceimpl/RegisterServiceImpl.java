package com.myproj.serviceimpl;

import com.myproj.entity.CuratorClient;
import com.myproj.service.RegisterService;
import com.myproj.tools.CuratorUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author LettleCadet
 * @Date 2019/2/24
 */
public class RegisterServiceImpl implements RegisterService
{
    private static final Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Autowired
    private CuratorClient client;

    private String childNodePath;

    private String serviceName;

    //为“|”添加转译符
    private String separator = "\\|";

    private String identification = "/";

    private String service = "serviceInstance";

    //存放已注册服务
    private Map<String,List<String>> registerService = new HashMap<String,List<String>>();

    private List<String> list = new ArrayList<String>();

    /**
     *
     * 通过Curator完成服务注册成功后，zk会立刻同步节点信息到注册的主机上，但是如果直接在linux上，zk不会同步节点信息
     */
    public Boolean registerService()
    {
        CuratorFramework curatorClient = null;
        ServiceDiscovery serviceDiscovery = null;
        try
        {
            curatorClient = client.init();

            if(logger.isDebugEnabled())
            {
                logger.debug("RegisterController.registerService():CuratorFramework was started !");
            }

            //获取获取serviceDiscovery
            serviceDiscovery = CuratorUtil.getServiceDiscovery(curatorClient,client.getRootNode());

            if(logger.isDebugEnabled())
            {
                logger.debug("RegisterController.registerService():ServiceDiscovery was started !");
            }

            String[] serviceNames = serviceName.split(separator);

            for(int i = 0;i < serviceNames.length;i++)
            {
                //仅允许未注册的服务，可以在zk上注册
                if(CollectionUtils.isEmpty(registerService.get(service)) || !registerService.get(service).contains(serviceNames[i]))
                {
                    //除了根节点以外的节点路径
                    CuratorUtil.register(childNodePath + identification + serviceNames[i],client.getZkServer(),Integer.valueOf(client.getPort()));
                    list.add(serviceNames[i]);
                    registerService.put(service,list);
                }
            }

            if(logger.isDebugEnabled())
            {
                logger.debug("RegisterController.registerService():register service succeed !,serviceName is" + serviceName );
            }
        }
        catch (Exception e)
        {
            logger.error("RegisterController.registerService():something wrong with curator , exception : " + e);

            return false;
        }
        finally
        {
            try
            {
                CuratorUtil.closeResource(curatorClient,serviceDiscovery);

                if(logger.isDebugEnabled())
                {
                    logger.debug("RegisterController.registerService():CuratorClient and ServiceDiscovery were closed");
                }
            }
            catch (IOException e)
            {
                logger.error("RegisterController.registerService():IO exception when close CuratorClient and ServiceDiscovery, exception : " + e);

                return false;
            }
        }

        return true;
    }

    public void setChildNodePath(String childNodePath)
    {
        this.childNodePath = childNodePath;
    }

    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }
}
