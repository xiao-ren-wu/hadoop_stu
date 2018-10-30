package org.xrw.flow;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/10/30 10:55
 */


public class FlowBean implements Writable {
    private int upFlow;
    private int dFlow;
    private int amountFlow;
    private String phone;

    public FlowBean() {
    }

    public FlowBean(int upFlow, int dFlow, String phone) {
        this.upFlow = upFlow;
        this.dFlow = dFlow;
        this.phone = phone;
        this.amountFlow = upFlow+dFlow;
    }

    public int getUpFlow() {
        return upFlow;
    }

    public void setUpFlow(int upFlow) {
        this.upFlow = upFlow;
    }

    public int getdFlow() {
        return dFlow;
    }

    public void setdFlow(int dFlow) {
        this.dFlow = dFlow;
    }

    public int getAmountFlow() {
        return amountFlow;
    }

    public void setAmountFlow(int amountFlow) {
        this.amountFlow = amountFlow;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * hadoop系统在序列化该类的时候调用的方法
     * @param dataOutput
     * @throws IOException
     */
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(upFlow);
        dataOutput.writeUTF(phone);
        dataOutput.writeInt(dFlow);
        dataOutput.writeInt(amountFlow);
    }

    /**
     * 反序列化调用
     * @param dataInput
     * @throws IOException
     */
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        upFlow = dataInput.readInt();
        phone = dataInput.readUTF();
        dFlow = dataInput.readInt();
        amountFlow = dataInput.readInt();
    }

    @Override
    public String toString() {
        return "FlowBean{" +
                "upFlow=" + upFlow +
                ", dFlow=" + dFlow +
                ", amountFlow=" + amountFlow +
                ", phone='" + phone + '\'' +
                '}';
    }
}
