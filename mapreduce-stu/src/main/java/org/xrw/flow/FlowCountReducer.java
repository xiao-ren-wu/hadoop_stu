package org.xrw.flow;


import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/10/30 12:36
 */


public class FlowCountReducer extends Reducer<Text,FlowBean,Text,FlowBean> {
    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {

        int upSum = 0;
        int dFlow = 0;

        for(FlowBean value:values){
            upSum+=value.getUpFlow();
            dFlow+=value.getdFlow();
        }

        context.write(key,new FlowBean(upSum,dFlow,key.toString()));
    }
}
