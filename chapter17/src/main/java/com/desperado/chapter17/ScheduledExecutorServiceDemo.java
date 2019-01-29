package com.desperado.chapter17;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ScheduledExecutorService： JDK1.5新增的，位于java.util.concurrent包中；是基于线程池设计的定时任务类，每个调度任务都会被分配到线程池中，
 * 并发执行，互不影响。
 * 与Timer很类似，但它的效果更好，多线程并行处理定时任务时，Timer运行多个TimeTask时，只要其中有一个因任务报错没有捕获抛出的异常，
 * 其它任务便会自动终止运行，使用 ScheduledExecutorService 则可以规避这个问题
 */
public class ScheduledExecutorServiceDemo {
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        service.scheduleAtFixedRate(()-> System.out.println("执行任务"+LocalDateTime.now()),0,3,TimeUnit.SECONDS);
    }
}
