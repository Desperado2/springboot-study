package com.desperado.chapter24;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Webscoket
 * WebSocket 是 HTML5 新增的一种在单个 TCP 连接上进行全双工通讯的协议，与 HTTP 协议没有太大关系….
 *
 * 在 WebSocket API 中，浏览器和服务器只需要做一个握手的动作，然后，浏览器和服务器之间就形成了一条快速通道。两者之间就直接可以数据互相传送。
 *
 * 浏览器通过 JavaScript 向服务器发出建立 WebSocket 连接的请求，连接建立以后，客户端和服务器端就可以通过 TCP 连接直接交换数据。
 *
 * 当你获取 WebSocket 连接后，你可以通过 send() 方法来向服务器发送数据，并通过 onmessage() 事件来接收服务器返回的数据..
 *
 * 长连接
 *
 * 与 AJAX 轮训的方式差不多，但长连接不像 AJAX 轮训一样，而是采用的阻塞模型（一直打电话，没收到就不挂电话）；客户端发起连接后，如果没消息，就一直不返回 Response 给客户端。直到有消息才返回，返回完之后，客户端再次建立连接，周而复始。
 *
 * 在没有 WebSocket 之前，大家常用的手段应该就是轮训了，比如每隔几秒发起一次请求，但这样带来的就是高性能开销，都知道一次 HTTP 响应是需要经过三次握手和四次挥手，远不如 TCP 长连接来的划算
 *
 * 本章目标
 * 利用 Spring Boot 与 WebSocke 打造 一对一 和 一对多 的在线聊天….
 */
@EnableWebSocket
@SpringBootApplication
public class Chapter24Application {

    public static void main(String[] args) {
        SpringApplication.run(Chapter24Application.class, args);
    }


    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}



