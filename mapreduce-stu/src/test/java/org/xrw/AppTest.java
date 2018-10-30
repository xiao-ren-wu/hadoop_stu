package org.xrw;

import static org.junit.Assert.assertTrue;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.Test;


import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws IOException {
        assertTrue( true );

        String[] msg = {"hello", "world", "lalalala", "shutdown", "xiaorenwu", "dear", "tom", "tony", "lueluelue"};
        Random random = new Random();
        int count = 1;
        for(int i=0;i<10;i++){
            BufferedWriter bw = new BufferedWriter(new FileWriter("F://" + count++ + ".txt"));
            int loop = random.nextInt(1000);
            System.out.println(loop);
            for(int j=0;j<loop;j++){
                bw.write(msg[random.nextInt(msg.length-1)]+" ");
            }
            bw.close();
        }
        System.out.println("ojbk");
    }
    @Test
    public void test() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hdp00:9000"), conf, "root");
        fs.copyFromLocalFile(new Path("F://request.dat"),new Path("/topon/input"));
        //fs.rename(new Path("/word"),new Path("/wordcount/input"));
    }


}
