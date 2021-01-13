package cn.com.hyc.thread.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class ForkJoinTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // test01(); // 结果：500000000500000000 时间：723
        test02(); // 结果：500000000500000000 时间：641
        //test03(); // 结果：500000000500000000 时间：467
    }

    // 普通方法for循环计算
    public static void test01() {

        long start = System.currentTimeMillis();
        long sum = 0L;
        for (int i = 1; i <= 10_0000_0000L; i++) {
            sum += i;
        }
        long end = System.currentTimeMillis();
        System.out.println("结果：" + sum + "  时间：" + (end - start));
    }

    // 使用ForkJoin 并行计算
    public static void test02() throws ExecutionException, InterruptedException {

        long start = System.currentTimeMillis();
        long sum = 0L;

        ForkJoinPool joinPool = new ForkJoinPool();
        ForkJoinPoolDemo joinDemon = new ForkJoinPoolDemo(0L, 10_0000_0000L);
        ForkJoinTask<Long> submit = joinPool.submit(joinDemon);
        sum = submit.get();

        long end = System.currentTimeMillis();
        System.out.println("结果：" + sum + "  时间：" + (end - start));
    }

    // 使用Stream流 并行计算
    public static void test03() {

        long start = System.currentTimeMillis();

        // 设置计算范围，增1计算，使用并行，将并行线程的值合并成一个值，返回
        long sum = LongStream.rangeClosed(0, 10_0000_0000L).parallel().reduce(0, (a, b) -> a + b);

        long end = System.currentTimeMillis();
        System.out.println("结果：" + sum + "  时间：" + (end - start));
    }
}
