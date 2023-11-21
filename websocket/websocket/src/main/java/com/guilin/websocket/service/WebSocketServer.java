package com.guilin.websocket.service;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jae
 * @ServerEndpoint("/webSocket/{uid}") Front end can connect to this endpoint
 */

@ServerEndpoint("/webSocket/{uid}")
@Component
public class WebSocketServer {

    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    // current connect number, should use Atomic to ensure thread safety
    private static final AtomicInteger onlineNum = new AtomicInteger(0);

    //concurrent set to store all the client's websocket session
    private static CopyOnWriteArraySet<Session> sessionPools = new CopyOnWriteArraySet<Session>();

    /**
     * connection established
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "uid") String uid){
        sessionPools.add(session);
        onlineNum.incrementAndGet();
        log.info(uid + "join webSocket！current count is " + onlineNum);
    }

    /**
     * connection closed
     */
    @OnClose
    public void onClose(Session session) {
        sessionPools.remove(session);
        int cnt = onlineNum.decrementAndGet();
        log.info("There is connection closed，current connect number is：{}", cnt);
    }

    /**
     * send message
     */
    public void sendMessage(Session session, String message) throws IOException {
        if(session != null){
            synchronized (session) {
                session.getBasicRemote().sendText(message);
            }
        }
    }

    /**
     * broadcast message
     */
    public void broadCastInfo(String message) throws IOException {
        for (Session session : sessionPools) {
            if(session.isOpen()){
                sendMessage(session, message);
            }
        }
    }

    /**
     * error handling
     */
    @OnError
    public void onError(Session session, Throwable throwable){
        log.error("error happened！");
        throwable.printStackTrace();
    }

}
