package org.xrw.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/11/11 16:42
 *
 * 1.构建连接
 * 2.从连接中抽取一个表DDL操作工具
 * 3.创建表描述对象
 */


public class HbaseClientDdl {
    private Connection conn;
    @Before
    public void getConn() throws Exception{
        //会启动加载hbase-site配置文件
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "hdp00:2181,hdp01:2181,hdp02:2181");
        conn = ConnectionFactory.createConnection(conf);
    }
    @Test
    public void testCreateTable()throws Exception{
        //从连接中构造一个DDL操作器
        Admin admin = conn.getAdmin();
        //创建一个表定义描述对象
        HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf("user_info"));

        //创建列定义描述对象
        HColumnDescriptor hColumnDescriptor = new HColumnDescriptor("base_info");
        //设置该列族中存储数据的最大版本数，默认是1
        hColumnDescriptor.setMaxVersions(3);
        HColumnDescriptor hColumnDescriptor1 = new HColumnDescriptor("extra_info");

        //将列族定义信息对象放入定义对象中
        hTableDescriptor.addFamily(hColumnDescriptor);
        hTableDescriptor.addFamily(hColumnDescriptor1);

        //用ddl操作器对象：admin来键表
        admin.createTable(hTableDescriptor);

        //关闭连接
        admin.close();
        conn.close();


    }
}
