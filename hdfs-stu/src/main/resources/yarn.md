## yarn是一个分布式程序的运行调度平台
yarn中有两大核心角色：
1. Resource Manager
接受用户提交的分布式计算程序，并为其划分资源
管理、监控各个Node Manager上的资源情况，以便于均衡负载


2. Node Manager
管理它所在机器的运算资源（cpu + 内存）
负责接受Resource Manager分配的任务，创建容器、回收资源
### YARN的安装
node manager在物理上应该跟data node部署在一起;
resource manager在物理上应该独立部署在一台专门的机器上.
1. 修改配置文件：vi yarn-site.xml
~~~
        <property>
                <name>yarn.resourcemanager.hostname</name>
                <value>hdp00</value>
        </property>

        <property>
                <name>yarn.nodemanager.aux-services</name>
                <value>mapreduce_shuffle</value>
        </property>

        <property>
                <name>yarn.nodemanager.resource.memory-mb</name>
                <value>1024</value>
        </property>
        <property>
                <name>yarn.nodemanager.resource.cpu-vcores</name>
                <value>2</value>
        </property>
~~~
2. scp这个yarn-site.xml到其他节点
3. 启动yarn集群：start-yarn.sh  （注：该命令应该在resourcemanager所在的机器上执行）
4. 用jps检查yarn的进程，用web浏览器查看yarn的web控制台http://hdp20-01:8088















