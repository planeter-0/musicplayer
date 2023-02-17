package com.starnet.musicplayer.websocket;

import com.starnet.musicplayer.common.component.LogThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Planeter
 * @description: TODO
 * @date 2023/2/17 12:33
 * @status dev
 */
@Component
@Slf4j
@ServerEndpoint("/log")
public class LogWebsocketHandle {

    private File file;
    private InputStream inputStream;

    /**
     * 新的WebSocket请求开启
     */
    @OnOpen
    public void onOpen(Session session) {
        try {
            // 执行tail -f命令
            file = new File("./logs/spring.log");
            inputStream = new FileInputStream(file);

            // 一定要启动新的线程，防止InputStream阻塞处理WebSocket的线程
            LogThread thread = new LogThread(inputStream, session);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * WebSocket请求关闭
     */
    @OnClose
    public void onClose() {
        try {
            if(inputStream != null)
                inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Throwable thr) {
        thr.printStackTrace();
    }
}
