package com.elisiocabral.stream_link.websocket;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import java.io.IOException;

@Component
public class WebSocketSignalHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    // Register a new client session
    public void registerSession(String userId, WebSocketSession session) {
        sessions.put(userId, session);
    }

    // Unregister an existing client session
    public void unregisterSession(String userId) {
        sessions.remove(userId);
    }

    // Send a signal to a specific client
    public void sendSignal(String userId, String message) {
        WebSocketSession session = sessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                // Handle the exception
            }
        }
    }

    // Broadcast a signal to all connected clients (if needed)
    public void broadcastSignal(String message) {
        sessions.forEach((userId, session) -> {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    // Handle the exception
                }
            }
        });
    }

    // Handle incoming signal from a client
    public void handleIncomingSignal(String userId, String message) {
        // Process the incoming message if needed
    }
}
