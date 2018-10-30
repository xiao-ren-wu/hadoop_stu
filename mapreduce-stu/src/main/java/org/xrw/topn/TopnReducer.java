package org.xrw.topn;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/10/30 17:44
 */


public class TopnReducer extends Reducer<Text,IntWritable,Text,IntWritable> {

    private TreeMap<PageCount,Integer> treeMap = new TreeMap<>();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        for(IntWritable value:values){
            count++;
        }
        treeMap.put(new PageCount(key.toString(),count),1);
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        Configuration conf = context.getConfiguration();
        int topn = conf.getInt("topn",5);
        Set<Map.Entry<PageCount, Integer>> set = treeMap.entrySet();
        for (Map.Entry<PageCount, Integer> entry:set){
            context.write(new Text(entry.getKey().getPage()),new IntWritable(entry.getKey().getCount()));
            if(--topn==0){
                return;
            }
        }
    }
}
