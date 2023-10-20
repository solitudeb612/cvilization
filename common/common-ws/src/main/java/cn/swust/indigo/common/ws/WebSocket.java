package cn.swust.indigo.common.ws;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
@ServerEndpoint(value = "/ws/{clientRunId}")
public class WebSocket {


    private static final Logger log = LoggerFactory.getLogger("wsLogger");
    //数据读写锁
    private static ReadWriteLock lock = new ReentrantReadWriteLock();
    //用来记录当前在线连接数
    private static int onlineCount = 0;

    //用来存放每个客户端对应的WebSocket对象
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<WebSocket>();
    Long clientId = 0L;
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {
        for (WebSocket item : webSocketSet) {
            try {
                log.info("ws发送健康信息");
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static void sendMessage(List<Integer> ids, String message) {
        for (WebSocket item : webSocketSet) {
            try {
                log.info("ws发送reset信息");
                for (Integer id : ids) {
                    if (id.intValue() == item.clientId)
                        item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static int getOnlineCount() {
        int result;
        lock.readLock().lock();
        result = onlineCount;
        lock.readLock().unlock();
        return result;
    }

    public static void addOnlineCount(WebSocket ws) {
        lock.writeLock().lock();
        webSocketSet.add(ws);
        WebSocket.onlineCount++;
        lock.writeLock().unlock();
    }

    public static void subOnlineCount(WebSocket ws) {
        lock.writeLock().lock();
        webSocketSet.remove(ws);
        WebSocket.onlineCount--;
        lock.writeLock().unlock();
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session
            , @PathParam("clientRunId") String clientRunId) {
        this.session = session;
        addOnlineCount(this);
        log.info("ws连接", "有新连接加入！当前在线人数为" + getOnlineCount());
        try {
            sendMessage(clientRunId);
        } catch (IOException e) {
            log.error("ws发生信息错误", e.getMessage());

        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        subOnlineCount(this);
        log.info("ws关闭", "有连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
//        log.info("ws信息", "来自客户端的消息:" + message);
        this.session = session;
        //群发消息
        JSONObject obj = JSONUtil.parseObj(message);

        try {
            sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("ws回复信息错误", e.getMessage());
        }
    }

    /**
     * 发生错误时调用
     */

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("ws发生信息错误", error.getMessage());
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        synchronized (this.session) {
            this.session.getAsyncRemote().sendText(message);
        }
    }
}