package org.xrw.hdfs.datacollection;

import java.io.IOException;
import java.util.Properties;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/10/27 13:54
 *
 * 单例模式
 * 获取配置相关信息
 */


public class PropertyHolderLazy {
    private static Properties prop = null;
    public static Properties getProp() throws IOException {
        if(prop==null){
            prop = new Properties();
            prop.load(PropertyHolderLazy.class.getClassLoader().getResourceAsStream("collect.properties"));
        }
        return prop;
    }
}
