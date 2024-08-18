package com.elisiocabral.stream_link.service;

import com.elisiocabral.stream_link.websocket.WebSocketHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebRTCSignalingService {

    private final WebSocketHandler webSocketHandler;

    @Autowired
    public WebRTCSignalingService(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    public void handleOffer(String fromUserId, String toUserId, String offer) {
        // Process and send offer to the target user
        String message = createOfferMessage(fromUserId, offer);
        webSocketHandler.sendMessage(toUserId, message);
    }

    public void handleAnswer(String fromUserId, String toUserId, String answer) {
        // Process and send answer to the target user
        String message = createAnswerMessage(fromUserId, answer);
        webSocketHandler.sendMessage(toUserId, message);
    }

    public void handleIceCandidate(String fromUserId, String toUserId, String candidate) {
        // Process and send ICE candidate to the target user
        String message = createIceCandidateMessage(fromUserId, candidate);
        webSocketHandler.sendMessage(toUserId, message);
    }

    private String createOfferMessage(String fromUserId, String offer) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> message =  new HashMap<>();
            message.put("fromUserId", fromUserId);
            message.put("offer", offer);
            return objectMapper.writeValueAsString(message);

        }catch (JsonProcessingException e){
            e.printStackTrace();
            return null;
        }
    }

    private String createAnswerMessage(String fromUserId, String answer) {
        // Create JSON message for answer
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> message =  new HashMap<>();
            message.put("fromUserId", fromUserId);
            message.put("answer", answer);
            return objectMapper.writeValueAsString(message);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

    }

    private String createIceCandidateMessage(String fromUserId, String candidate) {
        // Create JSON message for ICE candidate
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> message =  new HashMap<>();
            message.put("fromUserId", fromUserId);
            message.put("candidate", candidate);
            return objectMapper.writeValueAsString(message);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

}
