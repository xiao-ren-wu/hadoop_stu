package org.xrw.hdfs.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/10/27 16:07
 */


public class HdfsWordCount {
    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, URISyntaxException, InterruptedException {
        /**
         * 初始化工作
         */
        Properties props = new Properties();
        props.load(HdfsWordCount.class.getClassLoader().getResourceAsStream("job.properties"));

        Path input = new Path(props.getProperty("INPUT_PATH"));
        Path output = new Path(props.getProperty("OUTPUT_PATH"));

        Class<?> mapper_class = Class.forName(props.getProperty("MAPPER_CLASS"));

        Mapper mapper = (Mapper)mapper_class.newInstance();
        Context context = new Context();

        /**
         * 处理数据
         */
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://hdp00:9000"), new Configuration(), "root");
        RemoteIterator<LocatedFileStatus> iter = fileSystem.listFiles(input, false);

        while(iter.hasNext()){
            LocatedFileStatus file = iter.next();
            FSDataInputStream in = fileSystem.open(file.getPath());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            //逐行读取
            while((line=br.readLine())!=null){
                //调用一个方法对业务进行处理
                mapper.map(line,context);
            }
            br.close();
            in.close();
        }

        /**
         * 输出结果
         */
        HashMap<Object, Object> contextMap = context.getContextMap();
        if(fileSystem.exists(output)){
            throw new RuntimeException("指定的目录已经存在，请更换目录");
        }
        FSDataOutputStream out = fileSystem.create(new Path("res.dat"));

        Set<Map.Entry<Object, Object>> entrySet = contextMap.entrySet();
        for(Map.Entry entry:entrySet){
            out.write((entry.getKey().toString()+"\t"+entry.getValue()+"\n").getBytes());
        }

        out.close();
        fileSystem.close();

    }



}
