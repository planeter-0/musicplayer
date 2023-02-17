package com.starnet.musicplayer.common.component;

import javax.websocket.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Planeter
 * @description: 每个Websocket连接对应的日志线程，防止InputStream阻塞处理WebSocket的线程
 * @date 2023/2/17 12:34
 * @status ok
 */
public class LogThread extends Thread {

    private final BufferedReader reader;
    private final Session session;

    public LogThread(InputStream in, Session session) {
        this.reader = new BufferedReader(new InputStreamReader(in));
        this.session = session;

    }

    @Override
    public void run() {
        String line;
        try {
            while((line = reader.readLine()) != null) {
                // 将实时日志通过WebSocket发送给客户端，给每一行添加一个HTML换行
                session.getBasicRemote().sendText(line + "<br>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
