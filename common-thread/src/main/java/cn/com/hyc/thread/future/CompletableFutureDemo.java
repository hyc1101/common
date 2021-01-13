package cn.com.hyc.thread.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 没有返回值
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> System.out.println("已执行runAsync ----"));
        runAsync.get(); // 获取结果值，如果一直执行不完，该方法就会被阻塞，一直等待去get

        // 有返回值
        CompletableFuture<Integer> supplyAsync = CompletableFuture.supplyAsync(() -> {
            System.out.println("已执行supplyAsync ----");
            return 200;
        });
//        System.out.println(supplyAsync.get());

        // 当成功完成
        Integer info = supplyAsync.whenComplete((t, e) -> {
            System.out.println(t); // 获得成功执行完成的返回值，如果执行出错为null
            System.out.println(e); // 如果执行不成功，获取异常信息，如果没有异常为null
        }).exceptionally((e) -> { // 如果没有异常不执行
            e.getMessage(); // 将执行失败的异常信息获取出来
            return 404; // 有返回值
        }).get();

        System.out.println(info);

    }
}
