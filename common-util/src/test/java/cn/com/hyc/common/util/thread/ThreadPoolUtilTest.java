package cn.com.hyc.common.util.thread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.junit.Test;

/**
 * @author: hyc
 * @time: 2020/11/17 10:52
 */
public class ThreadPoolUtilTest {

    @Test
    public void t1() throws IOException {

        for (int i = 0; i < 10; i++) {
            ThreadPoolUtil.submit(new T1(), ThreadPoolUtil.PoolType.TEST_THREAD_POOL);
        }
        System.in.read();
    }

    @Test
    public void t2() throws Exception {

        // 建议将数据分隔为核心线程数量进行处理，处理完成后及时返回后在进行下一批次处理
        List<Future<String>> futureList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            futureList.add(ThreadPoolUtil.submit(new T1(), ThreadPoolUtil.PoolType.TEST_EX_POOL));
        }
        List<String> result = ThreadPoolUtil.result(futureList, 1000);
        result.stream().forEach(s -> System.out.println(s));
    }

    class T1 implements Callable<String> {

        @Override
        public String call() throws Exception {

            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "-- is running");
            return Thread.currentThread().getName() + "---t1";
        }
    }
}
