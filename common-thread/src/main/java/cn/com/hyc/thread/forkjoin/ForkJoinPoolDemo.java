package cn.com.hyc.thread.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * ForkJoin 并发执行任务，提高效率，在大数据量表现显著。最适合的是计算密集型的任务。
 * 
 * @author: hyc
 * @time: 2021/1/13 10:57
 */
public class ForkJoinPoolDemo extends RecursiveTask<Long> {

    // ForkJoin工作原理
    // 是将大量的数据分成多个子任务处理，然后合并。

    // ForkJoin特点
    // 工作窃取：该线程的任务执行完之后，就会去窃取其他线程没有执行完的任务，把任务拿到自己这里来执行，提高效率。
    // 用的双端队列

    private Long start;
    private Long end;
    private final long Max = 10000L; // 规定一个临界值

    public ForkJoinPoolDemo(Long start, Long end) {

        this.start = start;
        this.end = end;
    }

    // 递归合并
    @Override
    protected Long compute() {

        // 如果计算的两个值在临界值以内，就直接使用for循环计算
        if (end - start <= Max) {
            Long sum = 0L;
            for (Long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {

            // 计算中间值，将其分为两个线程两个对象去进行计算，在new的新对象中，
            // 也会去判断是否在临界值以内，否则还会继续new一个新对象，进入新线程中计算
            Long middle = (start + end) / 2;
            ForkJoinPoolDemo leftTask = new ForkJoinPoolDemo(start, middle);
            ForkJoinPoolDemo rightTask = new ForkJoinPoolDemo(middle + 1, end);

            // fork()是执行，join()是返回结果
            leftTask.fork();
            rightTask.fork();
            return leftTask.join() + rightTask.join();// 将两个任务的返回值，合并在一起
        }

    }
}
