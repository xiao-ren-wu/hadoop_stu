package org.xrw.wordcount.submit;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.xrw.wordcount.WordCountMapper;
import org.xrw.wordcount.WordCountReduce;

import java.io.IOException;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/10/29 17:48
 *
 * 在linux系统上直接提交任务
 *
 * 如果要在hadoop集群上的某台机器上启动job提交客户端的话
 * conf 里面就不需要指定fs.defaultFS mapreduce.framework.name
 *
 * 因为在集群上用hadoop jar main类的全称限定名 命令来启动客户端main方法时，
 * hadoop jar 这个命令会将所有机器上的hadoop安装目录中的jar包和配置文件加入到和运行时的classpath中
 *
 *
 */


public class JobSubmitWithLinux {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(JobSubmitWithLinux.class);

        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReduce.class);

        job.setNumReduceTasks(2);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.setInputPaths(job,new Path("/word"));
        FileOutputFormat.setOutputPath(job,new Path("/wordcount"));

        boolean res = job.waitForCompletion(true);

        System.exit(res?0:1);

    }
}
