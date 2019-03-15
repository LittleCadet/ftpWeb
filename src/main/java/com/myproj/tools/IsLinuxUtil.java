package com.myproj.tools;

import org.junit.Test;

import java.util.Properties;

/**
 * 判定当前系统是否是windows
 * @Author LettleCadet
 * @Date 2019/3/12
 */
public class IsLinuxUtil
{
    private static Integer IS_WINDOWS = 0;
    private static Integer IS_LINUX = 1;

    /**
     * 判定当前系统是否是windows
     * @return
     */
    public static boolean isWindows()
    {
        Properties prop = System.getProperties();

        String os = prop.getProperty("os.name");

        if (os != null && os.toLowerCase().contains("windows"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 判定当前系统是否是linux
     * @return
     */
    public static Integer isLinux()
    {
        Properties prop = System.getProperties();

        String os = prop.getProperty("os.name");

        if (os != null && os.toLowerCase().contains("windows"))
        {
            return IS_WINDOWS;
        }
        else
        {
            return IS_LINUX;
        }
    }
}
