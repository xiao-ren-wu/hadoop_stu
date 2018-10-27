package org.xrw.hdfs.wordcount;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/10/27 15:57
 */


public interface Mapper {

    void map(String line,Context context);
}
