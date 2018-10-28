package org.xrw.hdfs.datacollection;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


import java.io.File;
import java.io.FilenameFilter;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/10/27 10:40
 *
 * 定期向hdfs上传日志文件
 */


public class CollectTask extends TimerTask {
    @Override
    public void run() {
        /*
         * 定时探测日志源目录
         * 获取需要采集的文件
         * 移动这些文件到待上传临时目录
         * 遍历上传目录中各文件，逐一上传到HDFS的目标路径中，同时将传输完成的文件移动到备份目录
         *
         * 日志源路径：E:/logs/accesslog/
         * 待上传临时路径：E:/logs/toupload
         * 备份目录：E：logs/backup/日期
         * HDFS存储路径：/logs/日期
         * HDFS中文件的前缀：access_log_
         * HDFS中文件的后缀：.log
         */
        try{
            //获取配置参数
            final Properties props = PropertyHolderLazy.getProp();
            //构造一个log4j对象
            Logger logger = Logger.getLogger("logRollingFile");

            //获取本次采集的日期
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH");
            String date = format.format(new Date());

            File srcDir = new File(props.getProperty(Contants.LOG_SOURCE_DIR));

            //列出日志源目录中需要采集的文件
            File[] listFiles = srcDir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    if (name.startsWith(props.getProperty(Contants.LOG_LEGAL_PREFIX))) {
                        return true;
                    }
                    return false;
                }
            });

            //记录日志
            logger.info("探测如下文件需要采集："+Arrays.toString(listFiles));

            //将要采集的文件移动到上传临时目录
            File toUploadDir = new File(props.getProperty(Contants.LOG_TOUPLOAD_DIR));
            assert listFiles != null;
            for(File file:listFiles){
                FileUtils.moveFileToDirectory(file,toUploadDir,true);
            }

            //记录日志
            logger.info("上述文件移动到了待上传的目录："+toUploadDir.getAbsolutePath());

            //构建一个HDFS对象
            FileSystem fileSystem = FileSystem.get(new URI(props.getProperty(Contants.HDFS_URI)), new Configuration(), "root");

            File[] toUploadFiles = toUploadDir.listFiles();

            //检查HDFS要存储日志的目录是否存在，不存在则创建
            Path hdfsDestPath = new Path(props.getProperty(Contants.HDFS_DEST_BASE_DIR));
            if(!fileSystem.exists(hdfsDestPath)){
                fileSystem.mkdirs(hdfsDestPath);
            }

            //检查本地的备份目录是否存在，如果不存在则创建
            File backupDir = new File(props.getProperty(Contants.LOG_BACKUP_BASE_DIR));
            if(!backupDir.exists()){
                backupDir.mkdirs();
            }

            assert toUploadFiles != null;
            for(File file:toUploadFiles){
                //传输到HDFS并更改名称
                Path destPath = new Path(hdfsDestPath + "/" + UUID.randomUUID() + props.getProperty(Contants.HDFS_FILE_SUFFIX));

                fileSystem.copyFromLocalFile(new Path(file.getAbsolutePath()),destPath);

                //记录日志
                logger.info("文件传输到hdfs文件完成"+file.getAbsolutePath()+"--->"+destPath);

                //将传输完成的文件移动到备份目录中
                FileUtils.moveFileToDirectory(file,backupDir,true);

                //记录日志
                logger.info("文件备份完成"+file.getAbsolutePath()+"--->"+backupDir);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
