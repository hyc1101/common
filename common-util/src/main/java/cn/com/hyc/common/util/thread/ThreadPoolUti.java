package cn.com.hyc.common.util.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: hyc
 * @time: 2020/5/25 15:41
 */
public class ThreadPoolUti {

    private static final Map<PoolType, ExecutorService> POOLS = new HashMap<>();

    public enum PoolType {
        TEST_THREAD_POOL
    }

    static {

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("threadFacotry-%d").build();

        ExecutorService pool = new ThreadPoolExecutor(10, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        pool = Executors.newFixedThreadPool(10, namedThreadFactory);
        POOLS.put(PoolType.TEST_THREAD_POOL, pool);
    }

    /**
     * MethodName: getThreadPool
     * param: type 线程池类型
     * return:
     * describe: 获取线程池
     **/
    public static ExecutorService getThreadPool(PoolType type) {
        return POOLS.get(type);
    }


    /**
     * MethodName: submit
     * param: task 异步任务，type 线程池类型
     * return:  futuretask
     * describe: 根据指定类型线程池运行一个异步任务
     **/
    public static <T> Future<T> submit(Callable<T> task, PoolType type) {

        ExecutorService pool = POOLS.get(type);

        if (pool == null || pool.isShutdown()) {
            throw new IllegalArgumentException("pool not exists or had been shut down type:" + type);
        }

        return pool.submit(task);
    }

    /**
     * 同步等待多线程结果
     *
     * @param futureList 任务列表
     * @param waitTime   等待时间   毫秒
     * @param <T>
     * @throws Exception
     */
    public static <T> void result(List<Future<T>> futureList, long waitTime) throws Exception {
        int runningTask;
        do {
            Thread.sleep(waitTime);
            runningTask = futureList.size();
            for (Future<T> taskResult : futureList) {
                if (taskResult.isDone()) {
                    runningTask--;
                }
            }
        } while (runningTask > 0);
    }

}
