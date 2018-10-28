package org.xrw.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/10/26 21:08
 */


public class HdfsClientDemo {
    private FileSystem fileSystem;

    @Before
    public void init() throws URISyntaxException, IOException, InterruptedException {
        /*
         * new Configuration（）容器从下项目中的classpath中加载core-default.xml
         * core-site.xml hdfs-site.xml等文件
         *
         * 加载对象中的机制：
         * 构造时加载jar包中默认配置xx-default.xml
         * 再加载用户配置xx-size.xml，覆盖默认参数
         *
         * 构造完成之后还可以加载sonf.set(...)再次覆盖配置文件中的参数值
         */
        Configuration conf = new Configuration();
        //指定客户端上传文件到hdfs时需要保存的副本数为2个
        conf.set("dfs.replication","2");
        conf.set("dfs.blocksize","64m");


        /*
         * 构造一个访问指定HDFS系统的客户端对象
         * 参数1：HDFS系统指定的URI
         * 参数2：客户端指定的参数
         * 参数3：客户端的身份（用户名）
         */
        fileSystem = FileSystem.get(new URI("hdfs://hdp00:9000/"), conf, "root");

    }

    @After
    public void close() throws IOException {
        fileSystem.close();
    }

    @Test
    public void test() throws IOException {
        fileSystem.rename(new Path("/aaa"),new Path("/bbb"));
    }


    @Test
    public void mkdir() throws IOException {
        //创建文件夹
        fileSystem.mkdirs(new Path("xx/yy/zz"));
    }

    @Test
    public void list() throws IOException {
        //获取文件列表
        RemoteIterator<LocatedFileStatus> locatedFileStatusRemoteIterator = fileSystem.listFiles(new Path("/"), true);
        while(locatedFileStatusRemoteIterator.hasNext()){
            LocatedFileStatus next = locatedFileStatusRemoteIterator.next();
            System.out.println(next.getGroup());
        }
    }




}

























