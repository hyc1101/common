package cn.com.hyc.thread.forkjoin;

/**
 * ForkJoin 并发执行任务，提高效率，在大数据量表现显著。最适合的是计算密集型的任务。
 * 
 * @author: hyc
 * @time: 2021/1/13 10:57
 */
public class ForkJoinPoolDemo {

    // ForkJoin工作原理
    // 是将大量的数据分成多个子任务处理，然后合并。

    // ForkJoin特点
    // 工作窃取：该线程的任务执行完之后，就会去窃取其他线程没有执行完的任务，把任务拿到自己这里来执行，提高效率。
    // 用的双端队列



}
