package org.xrw.hdfs.wordcount;

import java.util.HashMap;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/10/27 15:58
 */


public class Context {
    private HashMap<Object,Object> contextMap = new HashMap<>();

    public void write(Object key,Object value){
        contextMap.put(key,value);
    }

    public Object get(Object key){
        return contextMap.get(key);
    }

    public HashMap<Object,Object> getContextMap(){
        return contextMap;
    }

}
