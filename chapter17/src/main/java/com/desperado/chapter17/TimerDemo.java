package com.desperado.chapter17;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer： JDK自带的java.util.Timer；通过调度java.util.TimerTask的方式
 * 让程序按照某一个频度执行，但不能在指定时间运行。 一般用的较少。
 */
public class TimerDemo {

    public static void main(String[] args) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("执行任务:" + LocalDateTime.now());
            }
        };

        Timer timer = new Timer();
        // timerTask：需要执行的任务
        // delay：延迟时间（以毫秒为单位）
        // period：间隔时间（以毫秒为单位）
        timer.schedule(timerTask,3000L,5000L);
    }
}
