package org.xrw.index;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/11/2 15:05
 */


public class JobSubmit {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setReducerClass(IndexStepOneReducer.class);
        job.setMapperClass(IndexStepOneMapper.class);

        job.setJarByClass(JobSubmit.class);

        job.setNumReduceTasks(4);

        job.setOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);

        FileInputFormat.setInputPaths(job,new Path("/index/input"));
        FileOutputFormat.setOutputPath(job,new Path("/index/output"));

        job.waitForCompletion(true);
    }
}
