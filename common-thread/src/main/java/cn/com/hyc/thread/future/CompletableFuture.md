##  CompletableFuture<T>类 异步回调 JDK1.8
>   java.util.concurrent.CompletableFuture<T>类，异步回调类。如同前端的Ajax。如在主线程new了该类，该类会创建一个新的线程去执行任务，并且主线程和该新线程互不影响。可同时进行。
### 常用方法，静态的
    public static CompletableFuture<Void> runAsync(Runnable runnable) 
    异步完成任务，并没有返回值
>
    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) 
    异步完成任务，有返回值
>
    public CompletableFuture<T> whenComplete(BiConsumer<? super T,? super Throwable> action)
    当成功完成异步任务的回调函数。
>
    public CompletableFuture<T> exceptionally(Function<Throwable,? extends T> fn) 
    捕获异常，并由返回值。
### 案例 CompletableFutureDemo.java