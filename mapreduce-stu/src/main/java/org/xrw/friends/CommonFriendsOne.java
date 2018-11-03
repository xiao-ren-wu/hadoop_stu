package org.xrw.friends;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/11/3 14:02
 */


public class CommonFriendsOne {
    public static class CommonFriendsMapper extends Mapper<LongWritable,Text,Text,Text>{
        Text k = new Text();
        Text v = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] userAndFriends = value.toString().split(":");
            String user = userAndFriends[0];
            String[] friends = userAndFriends[1].split(",");
            for(String friend:friends){
                k.set(friend);
                context.write(k,v);
            }
        }
    }
    public static class CommonFriendsReducer extends Reducer<Text,Text,Text,Text>{
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            super.reduce(key, values, context);
        }
    }
}
