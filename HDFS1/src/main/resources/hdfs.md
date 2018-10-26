## hdfs整体运行机制

hdfs：分布式文件系统
**hdfs有着文件系统共同的特征：**
1. 有目录结构，顶层目录是：  /
2. 系统中存放的就是文件
3. 系统可以提供对文件的：创建、删除、修改、查看、移动等功能


**hdfs跟普通的单机文件系统有区别：**
1. 单机文件系统中存放的文件，是在一台机器的操作系统中
2. hdfs的文件系统会横跨N多的机器
3. 单机文件系统中存放的文件，是在一台机器的磁盘上
4. hdfs文件系统中存放的文件，是落在n多机器的本地单机文件系统中（hdfs是一个基于linux本地文件系统之上的文件系统）

**hdfs的工作机制：**
1. 客户把一个文件存入hdfs，其实hdfs会把这个文件切块后，分散存储在N台linux机器系统中（负责存储文件块的角色：data node）<准确来说：切块的行为是由客户端决定的>


2. 一旦文件被切块存储，那么，hdfs中就必须有一个机制，来记录用户的每一个文件的切块信息，及每一块的具体存储机器（负责记录块信息的角色是：name node）

3. 为了保证数据的安全性，hdfs可以将每一个文件块在集群中存放多个副本（到底存几个副本，是由当时存入该文件的客户端指定的）


**综述：一个hdfs系统，由一台运行了namenode的服务器，和N台运行了datanode的服务器组成！**

### 搭建hdfs分布式集群
####  hdfs集群组成结构
!(hdfs集群组成结构)[]

#### 安装hdfs集群的具体步骤

**准备4台服务器：1个namenode节点  + 3 个datanode 节点**

linux下修改host文件位置
~~~
vim /etc/hosts
~~~
修改各台机器的主机名和ip地址
主机名：hdp-01  对应的ip地址：192.168.33.61
主机名：hdp-02  对应的ip地址：192.168.33.62
主机名：hdp-03  对应的ip地址：192.168.33.63
主机名：hdp-04  对应的ip地址：192.168.33.64

**关闭防火墙：**

关闭防火墙：service iptables stop  
关闭防火墙自启： chkconfig iptables off

**安装jdk：（hadoop体系中的各软件都是java开发的）**
1. 利用alt+p 打开sftp窗口，然后将jdk压缩包拖入sftp窗口
2. 然后在linux中将jdk压缩包解压到/root/apps 下
3. 配置环境变量：JAVA_HOME   PATH
~~~
vi /etc/profile   
#在文件的最后，加入：
export JAVA_HOME=/root/apps/jdk1.8.0_60
export PATH=$PATH:$JAVA_HOME/bin
~~~
source /etc/profile使配置生效

---------------------------------
**下载hadoop**[传送门](https://hadoop.apache.org/releases.html)

**修改配置文件**
|核心配置参数                           |
|:------------------------------------------:|
| 指定hadoop的默认文件系统为：hdf            |
| 指定hdfs的namenode节点为哪台机器           |
| 指定namenode软件存储元数据的本地目录  |
| 指定datanode软件存放文件块的本地目录 |


hadoop的配置文件在：/root/apps/hadoop安装目录/etc/hadoop/

1) 修改hadoop-env.sh
export JAVA_HOME=/root/apps/jdk1.8.0_60

2) 修改core-site.xml
~~~
<configuration>
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://hdp-01:9000</value>
    </property>
</configuration>
~~~
3) 修改hdfs-site.xml
~~~
<configuration>
    <property>
        <name>dfs.namenode.name.dir</name>
        <value>/root/dfs/name</value>
    </property>

    <property>
        <name>dfs.datanode.data.dir</name>
        <value>/root/dfs/data</value>
    </property>

</configuration>
~~~
4) 拷贝整个hadoop安装目录到其他机器
~~~
scp -r /root/apps/hadoop-2.8.0  hdp-02:/root/apps/
scp -r /root/apps/hadoop-2.8.0  hdp-03:/root/apps/
scp -r /root/apps/hadoop-2.8.0  hdp-04:/root/apps/
~~~
5) 启动HDFS
所谓的启动HDFS，就是在对的机器上启动对的软件

**要运行hadoop的命令，需要在linux环境中配置HADOOP_HOME和PATH环境变量
  vi /etc/profile**
~~~
export JAVA_HOME=/root/apps/jdk1.8.0_60
export HADOOP_HOME=/root/apps/hadoop-2.8.0
export PATH=$PATH:$JAVA_HOME/bin:$HADOOP_HOME/bin:$HADOOP_HOME/sbin
~~~

**首先，初始化namenode的元数据目录**
要在hdp-01上执行hadoop的一个命令来初始化namenode的元数据存储目录
~~~
hadoop namenode -format
~~~
- 创建一个全新的元数据存储目录
- 生成记录元数据的文件fsimage
- 生成集群的相关标识：如：集群id——clusterID

然后，启动namenode进程（在hdp-01上）

~~~
hadoop-daemon.sh start namenode
~~~
启动完后，首先用jps查看一下namenode的进程是否存在
然后，在windows中用浏览器访问namenode提供的web端口：50070
http://hdp-01:50070

然后，启动众datanode们（在任意地方）
hadoop-daemon.sh start datanode

6) 用自动批量启动脚本来启动HDFS

1) 先配置hdp-01到集群中所有机器（包含自己）的免密登陆
2) 配完免密后，可以执行一次  ssh 0.0.0.0
3) 修改hadoop安装目录中/etc/hadoop/slaves（把需要启动datanode进程的节点列入）
~~~
hdp-01
hdp-02
hdp-03
hdp-04
~~~
4) 在hdp-01上用脚本：start-dfs.sh 来自动启动整个集群
5) 如果要停止，则用脚本：stop-dfs.sh

#### hdfs的客户端操作
**客户端的理解**
1. 网页形式
2. 命令行形式
3. 客户端在哪里运行，没有约束，只要运行客户端的机器能够跟hdfs集群联网

文件的切块大小和存储的副本数量，都是由客户端决定！
所谓的由客户端决定，是通过配置参数来定的
hdfs的客户端会读以下两个参数，来决定切块大小、副本数量：
- 切块大小的参数： dfs.blocksize
- 副本数量的参数： dfs.replication

**上面两个参数应该配置在客户端机器的hadoop目录中的hdfs-site.xml中配置**
~~~
<property>
    <name>dfs.blocksize</name>
    <value>64m</value>
</property>

<property>
    <name>dfs.replication</name>
    <value>2</value>
</property>
~~~

#### Hello world
- 构建maven项目，添加相关配置文件
~~~
<dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
    </dependency>

    <dependency>
      <groupId>org.apache.hadoop</groupId>
      <artifactId>hadoop-common</artifactId>
      <version>2.8.5</version>
    </dependency>

    <dependency>
      <groupId>org.apache.hadoop</groupId>
      <artifactId>hadoop-hdfs</artifactId>
      <version>2.8.5</version>
    </dependency>

    <dependency>
      <groupId>org.apache.hadoop</groupId>
      <artifactId>hadoop-client</artifactId>
      <version>2.8.5</version>
    </dependency>
~~~
- test
~~~
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


}
~~~












