package com.desperado.chapter13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 所谓延时消息就是指当消息被发送以后，并不想让消费者立即拿到消息，而是等待指定时间后，消费者才拿到这个消息进行消费。
 *
 * 延迟队列
 * 延迟队列能做什么？
 *
 * 订单业务： 在电商/点餐中，都有下单后 30 分钟内没有付款，就自动取消订单。
 * 短信通知： 下单成功后 60s 之后给用户发送短信通知。
 * 失败重试： 业务操作失败后，间隔一定的时间进行失败重试。
 * 这类业务的特点就是：非实时的，需要延迟处理，需要进行失败重试。一种比较笨的方式是采用定时任务，轮训数据库，方法简单好用，但性能底下，在高并发情况下容易弄死数据库，间隔时间不好设置，时间过大，影响精度，过小影响性能，而且做不到按超时的时间顺序处理。另一种就是用Java中的DelayQueue 位于java.util.concurrent包下，本质是由PriorityQueue和BlockingQueue实现的阻塞优先级队列。，这玩意最大的问题就是不支持分布式与持久化
 *
 * RabbitMQ 实现思路
 * RabbitMQ队列本身是没有直接实现支持延迟队列的功能，但可以通过它的Time-To-Live Extensions 与 Dead Letter Exchange 的特性模拟出延迟队列的功能。
 *
 * Time-To-Live Extensions
 *
 * RabbitMQ支持为队列或者消息设置TTL（time to live 存活时间）。TTL表明了一条消息可在队列中存活的最大时间。当某条消息被设置了TTL或者当某条消息进入了设置了TTL的队列时，这条消息会在TTL时间后死亡成为Dead Letter。如果既配置了消息的TTL，又配置了队列的TTL，那么较小的那个值会被取用。
 *
 * Dead Letter Exchange
 *
 * 死信交换机，上文中提到设置了 TTL 的消息或队列最终会成为Dead Letter。如果为队列设置了Dead Letter Exchange（DLX），那么这些Dead Letter就会被重新发送到Dead Letter Exchange中，然后通过Dead Letter Exchange路由到其他队列，即可实现延迟队列的功能。
 */
@SpringBootApplication
public class Chapter13Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter13Application.class, args);
    }

}

