package com.myproj.ftp;

import com.myproj.tools.FtpUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.*;

/**
 * ftp批量下载：支持将服务器指定目录下的文件下载到指定的本地位置上
 * 沈燮
 * 2019/1/4
 **/
public class FtpBatchDownload
{
    private FTPClient client = FtpUtil.init();

    //分隔符‘\’
    private final String SPLIT_BACKSLASH = "\\";

    //分隔符“/”
    private final String SPLIT_FORWARD_SLASH = "/";

    //是文件的标志“.”
    private final String IS_FILE = ".";

    //批量下载的文本的路径
    private String batchDownloadFilePath;

    //指定下载到本地的路径：服务器路径：本地路径
    private Map<String,String> filePaths = new HashMap<String,String>();

    //存放下载失败的文件的路径
    private Map<String,String> fails = new HashMap<String,String>();

    /**
     * 下载文件
     * @return 判定是否下载完成
     */
    public boolean batchDownload()
    {
        boolean flag = false;

        //数据预处理
        if(preProcess())
        {
            //建立ftp连接
            if(FtpUtil.connectToFtp())
            {
                //下载操作
                flag = downloadProcess();
            }
        }
        return flag;

    }

    /**
     * 下载的具体操作
     * @return  判定是否下载完成
     */
    public boolean downloadProcess()
    {
        FileOutputStream os = null;

        String localDownloadFilePath = null;
        String remoteDownloadFilePath = null;
        List<Boolean> result = new ArrayList<Boolean>();
        try
        {
            for (Map.Entry<String, String> entry : filePaths.entrySet())
            {
                remoteDownloadFilePath = entry.getKey();
                localDownloadFilePath = entry.getValue();

                int index = remoteDownloadFilePath.lastIndexOf(SPLIT_FORWARD_SLASH);

                //获取最后一个“|”之后的文件名
                String lastFileName = remoteDownloadFilePath.substring(index+1);

                if(null != lastFileName)
                {
                    //如果remoteDownloadFilePath是目录，则需要获取到所有该目录下的文件，之后下载到指定本地目录
                    if (!lastFileName.contains(IS_FILE))
                    {
                        File file = new File(remoteDownloadFilePath);

                        //linux开启本地被动模式
                        client.enterLocalPassiveMode();

                        client.changeWorkingDirectory(remoteDownloadFilePath);

                        FTPFile[] files = client.listFiles();

                        for (FTPFile remoteFile : files)
                        {
                            if("".equals(lastFileName))
                            {
                                remoteDownloadFilePath = entry.getKey() + remoteFile.getName();
                            }
                            else
                            {
                                remoteDownloadFilePath = entry.getKey() + SPLIT_FORWARD_SLASH + remoteFile.getName();
                            }

                            //判定本地目录是否存在,不存在，则创建目录
                            localDownloadFilePath = isLocalDir(remoteDownloadFilePath,entry.getValue());

                            System.out.println("remoteDownloadFilePath:" + remoteDownloadFilePath + "\n" + "localDownloadFilePath:" + localDownloadFilePath);

                            //下载细节处理
                            result = detailProcess(os,localDownloadFilePath,remoteDownloadFilePath,result);
                        }
                    }
                    else
                    {
                        //判定本地目录是否存在,不存在，则创建目录
                        localDownloadFilePath = isLocalDir(remoteDownloadFilePath,localDownloadFilePath);

                        System.out.println("remoteDownloadFilePath:" + remoteDownloadFilePath + "\n" + "localDownloadFilePath:" + localDownloadFilePath);

                        //下载细节处理
                        //如果remoteDownloadFilePath是文件，则直接下载即可
                        result = detailProcess(os,localDownloadFilePath,remoteDownloadFilePath,result);
                    }
                }
                else
                {
                    System.out.println("ftpBatchDownload.txt中的数据有异常：服务器的路径不包含“/”，请及时处理");
                }

            }
        }
        catch (IOException e)
        {
            System.out.println("something wrong with io exception,exception:"+e + "localDownloadFilePath:"+localDownloadFilePath);

            return false;
        }
        finally
        {
            //关闭资源
            FtpUtil.closeResources(null, os, client);
        }

        if (!CollectionUtils.isEmpty(fails))
        {
            System.out.println("****************下载失败的文件路径****************");
            for (Map.Entry<String, String> fail : fails.entrySet())
            {
                System.out.println("remoteDownloadFilePath:" + fail.getKey());
                System.out.println("localDownloadFilePath:" + fail.getValue());
            }
        }
        else
        {
            System.out.println("--------------全部下载成功--------------");
        }

        if (result.contains(false))
        {
            return false;
        }

        return true;
    }

    /**
     * 判定服务器的文件是否是否存在
     * @return 判定服务器的文件是否是否存在
     */
    public boolean isRemoteDir(String remoteDownloadFilePath)
    {
        int remoteIndex = remoteDownloadFilePath.lastIndexOf(SPLIT_FORWARD_SLASH);
        String remoteDir = remoteDownloadFilePath.substring(0,remoteIndex);
        String fileName = new File(remoteDownloadFilePath).getName();
        try
        {
            //判定目录是否存在
            if(!client.changeWorkingDirectory(remoteDir))
            {
                System.out.println("服务器上不存在该目录，停止下载");

                //关闭资源
                FtpUtil.closeResources(null, null, client);

                return false;
            }

            FTPFile[] ftpFiles = client.listFiles();

            for(FTPFile file : ftpFiles)
            {
                if(fileName.equals(file.getName()))
                {
                    return true;
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("something wrong in ftp operation ,exception:"+e);

            return false;
        }

        return false;
    }

    /**
     * 判定本地目录是否存在
     * @return 判定本地目录是否存在
     */
    public String isLocalDir(String remoteDownloadFilePath,String localDownloadFilePath)
    {
        File localFile = new File(localDownloadFilePath);

        File remoteFile = new File(remoteDownloadFilePath);

        if (!localFile.exists())
        {
            System.out.println("本地文件目录创建" + (String.valueOf(localFile.mkdir()).equals("true") ? "成功！" : "失败！"));
        }

        if(!SPLIT_BACKSLASH.equals(localDownloadFilePath.substring(localDownloadFilePath.lastIndexOf(SPLIT_BACKSLASH))))
        {
            localDownloadFilePath = localDownloadFilePath + SPLIT_BACKSLASH;
        }

        //拼接本地路径，否则在创建输出流时，会报文件找不到异常
        localDownloadFilePath = localDownloadFilePath + remoteFile.getName();

        return localDownloadFilePath;
    }

    /**
     * 数据预处理
     * @return 本地路径和服务器路径都不为空时，返回true
     */
    public boolean preProcess()
    {

        BufferedReader br = null;
        try
        {
            String line = null;
            br = new BufferedReader(new FileReader(batchDownloadFilePath));

            while(null != (line = br.readLine()))
            {
                String[] paths = line.split(" ");
                List<String> li = new ArrayList<String>();
                for(int i = 0;i<paths.length;i++)
                {
                    if(!("".equals(paths[i])))
                    {
                        li.add(paths[i]);
                    }
                }

                for(String path:li)
                {
                    if (line.contains(SPLIT_BACKSLASH) && line.contains(SPLIT_FORWARD_SLASH) && null != path.trim())
                    {
                        if (null != li.get(0) && null != li.get(1))
                        {
                            filePaths.put(li.get(0).trim(), li.get(1).trim());
                        }
                    }
                    else if(line.contains(SPLIT_BACKSLASH) || line.contains(SPLIT_FORWARD_SLASH))
                    {
                        System.out.println("在ftpBatchUpload.txt文件中的数据有异常，请及时处理！");

                        return false;
                    }
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("processData failed in IO operation,e" + e);

            return false;
        }
        catch (IOException e)
        {
            System.out.println("something wrong with IO operation,e" + e);

            return false;
        }
        finally
        {
            if(null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    System.out.println("io close exception,e:" + e);
                }
            }
        }

        return true;
    }

    /**
     * 下载的详细过程
     */
    public List<Boolean> detailProcess(FileOutputStream os,String localDownloadFilePath,String remoteDownloadFilePath,List<Boolean> result)
        throws IOException
    {
        Boolean flag = false;

        String fileName = new File(remoteDownloadFilePath).getName();

        //输出流必须精确到文件：如果精确到目录，会报fileNotFound Exception
        os = new FileOutputStream(localDownloadFilePath);

        //linux设置本地被动模式，在windows默认打开
        client.enterLocalPassiveMode();

        //设置ftp专用的二进制码
        client.setFileType(FTPClient.BINARY_FILE_TYPE);

        //当指定的文件在服务器上不存在时，终止下载操作
        if (!isRemoteDir(remoteDownloadFilePath))
        {
            System.out.println("服务器上不存在该文件，停止下载");

            //关闭资源
            FtpUtil.closeResources(null, os, client);

            fails.put(remoteDownloadFilePath,localDownloadFilePath);

            result.add(false);

            return result;
        }

        System.out.println("用ftp下载开始");

        //下载操作
        flag = client.retrieveFile(fileName, os);

        result.add(flag);

        if (!flag)
        {
            fails.put(remoteDownloadFilePath, localDownloadFilePath);
        }

        System.out.println("用ftp下载" + (String.valueOf(flag).equals("true") ? "成功！" : "失败！"));

        return result;
    }

    public String getBatchDownloadFilePath()
    {
        return batchDownloadFilePath;
    }

    public void setBatchDownloadFilePath(String batchDownloadFilePath)
    {
        this.batchDownloadFilePath = batchDownloadFilePath;
    }
}
