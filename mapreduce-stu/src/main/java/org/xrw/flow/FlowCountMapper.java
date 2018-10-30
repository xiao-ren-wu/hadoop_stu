package org.xrw.flow;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


import java.io.IOException;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/10/30 10:24
 */


public class FlowCountMapper extends Mapper<LongWritable,Text,Text,FlowBean> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String msg = value.toString();
        String[] fields = msg.split("\t");

        String phone = fields[1];

        int upFlow = Integer.parseInt(fields[fields.length-3]);
        int dFlow = Integer.parseInt(fields[fields.length-2]);
        context.write(new Text(phone),new FlowBean(upFlow,dFlow,phone));
    }
}
