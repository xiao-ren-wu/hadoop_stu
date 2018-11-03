package org.xrw.ordertopn;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
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
    public static class OrderTopnMapper extends Mapper<LongWritable, Text, OrderBean, NullWritable> {
        OrderBean orderBean = new OrderBean();
        NullWritable v = NullWritable.get();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] fields = value.toString().split(",");

            orderBean.set(fields[0], fields[1], fields[2], Float.parseFloat(fields[3]), Integer.parseInt(fields[4]));

            context.write(orderBean, v);
        }
    }

    public static class OrderTopnReducer extends Reducer<OrderBean, NullWritable, OrderBean, NullWritable> {
        @Override
        protected void reduce(OrderBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
            int i = 0;
            for (NullWritable v : values) {
                context.write(key, v);
                if (++i == 3) {
                    return;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.setInt("topn", 5);
        Job job = Job.getInstance(conf);

        job.setJarByClass(OrderTopn.class);

        job.setMapperClass(OrderTopnMapper.class);
        job.setReducerClass(OrderTopnReducer.class);

        job.setMapOutputValueClass(NullWritable.class);
        job.setMapOutputKeyClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        job.setGroupingComparatorClass(OrderIdGroupingComparator.class);

        job.setNumReduceTasks(2);

        job.setPartitionerClass(OrderIdPartitioner.class);

        FileInputFormat.setInputPaths(job, new Path("/topn/input"));
        FileOutputFormat.setOutputPath(job, new Path("/topn/output"));

        boolean b = job.waitForCompletion(true);

    }
}
