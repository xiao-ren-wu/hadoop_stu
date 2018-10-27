package org.xrw.hdfs.datacollection;

import java.util.Timer;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/10/27 9:58
 */


public class DataCollection {
    private static final Long ONE_HOUR = 1000*60*60L;
    public static void main(String[] args) {
        Timer timer1 = new Timer();
        timer1.schedule(new CollectTask(),0,ONE_HOUR);

        timer1.schedule(new BackupCleanTask(),0,ONE_HOUR);
    }
}
