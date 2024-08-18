package com.elisiocabral.stream_link.websocket;

import com.elisiocabral.stream_link.service.WebRTCSignalingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    WebRTCSignalingService webRTCSignalingService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();



//    @Autowired
//    public WebSocketHandler(WebRTCSignalingService webRTCSignalingService) {
//        this.webRTCSignalingService = webRTCSignalingService;
//    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String userId = getUserIdFromSession(session);
        sessions.put(userId, session);
    }



    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws JsonProcessingException {
        // Handle incoming messages
        JsonNode jsonNode = objectMapper.readTree(message.getPayload());
        String type = jsonNode.get("type").asText();
        String fromUserId = getUserIdFromSession(session);
        String toUserId = jsonNode.get("toUserId").asText();

        switch (type) {
            case "offer":
                String offer = jsonNode.get("offer").asText();
                webRTCSignalingService.handleOffer(fromUserId, toUserId, offer);
                break;
            case "answer":
                String answer = jsonNode.get("answer").asText();
                webRTCSignalingService.handleAnswer(fromUserId, toUserId, answer);
                break;
            case "ice-candidate":
                String candidate = jsonNode.get("candidate").asText();
                webRTCSignalingService.handleIceCandidate(fromUserId, toUserId, candidate);
                break;
            // Handle other message types
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String userId = getUserIdFromSession(session);
        sessions.remove(userId);
    }

    public void sendMessage(String userId, String message) {
        WebSocketSession session = sessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                // Handle exception
            }
        }
    }

    private String getUserIdFromSession(WebSocketSession session) {
        return session.getAttributes().get("userId").toString();
    }


}
