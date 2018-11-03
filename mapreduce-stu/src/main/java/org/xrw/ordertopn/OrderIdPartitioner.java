package org.xrw.ordertopn;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/11/3 11:06
 *
 * 按照订单id分发数据
 */


public class OrderIdPartitioner extends Partitioner<OrderBean,NullWritable> {
    @Override
    public int getPartition(OrderBean orderBean, NullWritable nullWritable, int i) {
        return (orderBean.getOrderId().hashCode()&Integer.MAX_VALUE)%i;
    }
}
