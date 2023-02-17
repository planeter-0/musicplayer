package com.starnet.musicplayer.websocket;

/**
 * @author Planeter
 * @description: TODO
 * @date 2023/2/17 9:54
 * @status dev
 */
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@Component
@Slf4j
@ServerEndpoint("/websocket")
public class WebsocketServer {

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        try {
            log.info("建立连接");
        } catch (Exception e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        log.info("连接关闭！");
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口 {}  的信息:{}" ,session.getId(), message);
    }

    /**
     * 发生错误后调用的方法
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

}
