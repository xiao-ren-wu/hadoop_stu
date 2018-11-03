package org.xrw.join;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/11/3 17:09
 */


public class ReduceSideJoin {
    public static class JoinMapper extends Mapper<LongWritable,Text,Text,JoinBean>{
        private String fileName;
        JoinBean bean = new JoinBean();
        Text k = new Text();

        /**map task左数据处理的时候会调用该方法一次，只调用一次
         * 之后才调用自己实现的map方法
         * */
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            FileSplit inputSplit = (FileSplit) context.getInputSplit();
            fileName = inputSplit.getPath().getName();
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] fields = value.toString().split(",");
            if(fileName.startsWith("order")){
                bean.set(fields[0],fields[1],"NULL",-1,"-1","order");
            }else{
                bean.set("NULL", fields[0], fields[1], Integer.parseInt(fields[2]), fields[3], "user");
            }
            k.set(bean.getUserId());
            context.write(k, bean);
        }
    }
    public static class JoinReducer extends Reducer<Text,JoinBean,JoinBean,NullWritable>{
        @Override
        protected void reduce(Text key, Iterable<JoinBean> beans, Context context)
                throws IOException, InterruptedException {
            ArrayList<JoinBean> orderList = new ArrayList<>();
            JoinBean userBean = null;

            try {
                // 区分两类数据
                for (JoinBean bean : beans) {
                    if ("order".equals(bean.getTableName())) {
                        JoinBean newBean = new JoinBean();
                        BeanUtils.copyProperties(newBean, bean);
                        orderList.add(newBean);
                    }else{
                        userBean = new JoinBean();
                        BeanUtils.copyProperties(userBean, bean);
                    }

                }

                // 拼接数据，并输出
                for(JoinBean bean:orderList){
                    bean.setUserName(userBean.getUserName());
                    bean.setUserAge(userBean.getUserAge());
                    bean.setUserFriend(userBean.getUserFriend());

                    context.write(bean, NullWritable.get());

                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }
}
































