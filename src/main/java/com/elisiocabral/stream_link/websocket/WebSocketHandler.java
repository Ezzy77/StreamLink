package com.elisiocabral.stream_link.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.elisiocabral.stream_link.service.WebRTCSignalingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final WebRTCSignalingService webRTCSignalingService;
    private final WebSocketSignalHandler webSocketSignalHandler;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public WebSocketHandler(WebRTCSignalingService webRTCSignalingService, WebSocketSignalHandler webSocketSignalHandler) {
        this.webRTCSignalingService = webRTCSignalingService;
        this.webSocketSignalHandler = webSocketSignalHandler;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String userId = getUserIdFromSession(session);
        webSocketSignalHandler.registerSession(userId, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(message.getPayload());
        String type = jsonNode.get("type").asText();
        String fromUserId = getUserIdFromSession(session);
        String toUserId = jsonNode.get("toUserId").asText();

        switch (type) {
            case "offer" -> {
                String offer = jsonNode.get("offer").asText();
                webRTCSignalingService.handleOffer(fromUserId, toUserId, offer);
            }
            case "answer" -> {
                String answer = jsonNode.get("answer").asText();
                webRTCSignalingService.handleAnswer(fromUserId, toUserId, answer);
            }
            case "ice-candidate" -> {
                String candidate = jsonNode.get("candidate").asText();
                webRTCSignalingService.handleIceCandidate(fromUserId, toUserId, candidate);
            }
            // Handle other message types
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String userId = getUserIdFromSession(session);
        webSocketSignalHandler.unregisterSession(userId);
    }

    private String getUserIdFromSession(WebSocketSession session) {
        return session.getAttributes().get("userId").toString();
    }
}
