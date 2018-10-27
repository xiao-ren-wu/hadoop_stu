package org.xrw.hdfs.datacollection;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.TimerTask;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/10/27 14:49
 */


public class BackupCleanTask extends TimerTask {
    @Override
    public void run() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH");
        long now = System.currentTimeMillis();
        try {
            Properties props = PropertyHolderLazy.getProp();
            File backupDir = new File(props.getProperty(Contants.LOG_BACKUP_BASE_DIR));
            File[] dayBackDir = backupDir.listFiles();
            //判断日期记录是否超过24小时
            for(File file:dayBackDir){
                long time = format.parse(file.getName()).getTime();
                if(now-time>24*60*60*1000L){
                    FileUtils.deleteDirectory(file);
                }

            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }
}
