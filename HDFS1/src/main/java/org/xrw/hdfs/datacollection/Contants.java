package org.xrw.hdfs.datacollection;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/10/27 14:03
 *
 * 收集参数名称
 */


public class Contants {
    /**
     * 日志源目录
     */
    public static final String LOG_SOURCE_DIR = "LOG_SOURCE_DIR";

    /**
     * 日志待上传目录
     */
    public static final String LOG_TOUPLOAD_DIR = "LOG_TOUPLOAD_DIR";

    /**
     * 日志备份目录
     */
    public static final String LOG_BACKUP_BASE_DIR = "LOG_BACKUP_BASE_DIR";

    /**
     * 超时时间
     */
    public static final String LOG_BACKUP_TIMEOUT = "LOG_BACKUP_TIMEOUT";


    /**
     * 日志文件名称前缀
     */
    public static final String LOG_LEGAL_PREFIX = "LOG_LEGAL_PREFIX";

    /**
     * HDFS的URI
     */
    public static final String HDFS_URI = "HDFS_URI";

    /**
     * 日志在HDFS中的存储路径
     */
    public static final String HDFS_DEST_BASE_DIR = "HDFS_DEST_BASE_DIR";

    /**
     * HDFS中存储的日志名称前缀
     */
    public static final String HDFS_FILE_PREFIX = "HDFS_FILE_PREFIX";

    /**
     * HDFS中存储的日志名称后缀
     */
    public static final String HDFS_FILE_SUFFIX = "HDFS_FILE_SUFFIX";
}
