package org.xrw.hdfs.wordcount;

import java.util.Map;

/**
 * @author XiaoRenwu
 * e-mail 18629015421@163.com
 * github https://github.com/xiao-ren-wu
 * @version 1
 * @since 2018/10/27 16:02
 */


public class WordCountMapper implements Mapper {
    @Override
    public void map(String line, Context context) {
        String[] words = line.split(" ");
        for(String word:words){
            Object value = context.get(word);
            if(null==value){
                context.write(word,1);
            }else{
                int v=(int)value;
                context.write(word,v+1);
            }
        }
    }
}
