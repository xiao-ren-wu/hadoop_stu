package org.xrw.topn;

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
 * @since 2018/10/30 18:04
 */


public class JobSubmitter {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.setInt("topn",5);
        Job job = Job.getInstance(conf);

        job.setJarByClass(JobSubmitter.class);

        job.setMapperClass(TopnMapper.class);
        job.setReducerClass(TopnReducer.class);

        job.setMapOutputValueClass(IntWritable.class);
        job.setMapOutputKeyClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setNumReduceTasks(3);

        FileInputFormat.setInputPaths(job,new Path("/topn/input"));
        FileOutputFormat.setOutputPath(job,new Path("/topn/output"));

        boolean b = job.waitForCompletion(true);

    }
}
