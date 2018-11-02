package org.xrw.ordertopn;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


import java.io.IOException;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/11/2 20:44
 */


public class OrderTopn {
    public static class OrderTopnMapper extends Mapper{
        @Override
        protected void map(Object key, Object value, Context context) throws IOException, InterruptedException {
            super.map(key, value, context);
        }
    }
    public static class OrderTopnReducer extends Reducer{
        @Override
        protected void reduce(Object key, Iterable values, Context context) throws IOException, InterruptedException {
            super.reduce(key, values, context);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.setInt("topn",5);
        Job job = Job.getInstance(conf);

        job.setJarByClass(OrderTopn.class);

        job.setMapperClass(OrderTopnMapper.class);
        job.setReducerClass(OrderTopnReducer.class);

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
