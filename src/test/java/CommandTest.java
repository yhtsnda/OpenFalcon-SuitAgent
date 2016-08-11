/*
 * www.yiji.com Inc.
 * Copyright (c) 2016 All Rights Reserved
 */
/*
 * 修订记录:
 * guqiu@yiji.com 2016-07-15 10:59 创建
 */

import com.yiji.falcon.agent.util.CommandUtil;
import com.yiji.falcon.agent.util.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

/**
 * @author guqiu@yiji.com
 */
public class CommandTest {

    @Test
    public void test() throws IOException {
        int count = 5;
        String address = "www.deh4.com";

        CommandUtil.ExecuteResult executeResult = CommandUtil.execWithTimeOut(String.format("ping -c %d %s",count,address),
                5, TimeUnit.SECONDS);
        if(executeResult.isSuccess){
            List<Float> times = new ArrayList<>();
            String msg = executeResult.msg;
            for (String line : msg.split("\n")) {
                for (String ele : line.split(" ")) {
                    if(ele.toLowerCase().contains("time=")){
                        float time = Float.parseFloat(ele.replace("time=",""));
                        times.add(time);
                    }
                }
            }

            if(times.isEmpty()){
                System.out.println(String.format("ping 地址 %s 无法连通",address));
            }else{
                float sum = 0;
                for (Float time : times) {
                    sum += time;
                }
                System.out.println(String.format("地址 %s 的%s次ping平均延迟 %s",address,count,sum / times.size()));
            }

        }
    }

    @Test
    public void testJMXRemoteUrl() throws IOException {
        int pid = 0;
        String cmd = "ps aux | grep " + 6346;
        String keyStr = "-Dcom.sun.management.jmxremote.port";

        CommandUtil.ExecuteResult result = CommandUtil.execWithTimeOut(cmd,10,TimeUnit.SECONDS);
        if(result.isSuccess){
            String msg = result.msg;
            StringTokenizer st = new StringTokenizer(msg," ",false);
            while( st.hasMoreElements() ){
                String split = st.nextToken();
                if(!StringUtils.isEmpty(split) && split.contains(keyStr)){
                    String[] ss = split.split("=");
                    if(ss.length == 2){
                        System.out.println(ss[1]);
                    }
                }
            }
        }else{
            System.out.println("命令 " + cmd + " 执行失败");
        }

    }

}