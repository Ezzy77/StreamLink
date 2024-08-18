package com.elisiocabral.stream_link.service;

import com.elisiocabral.stream_link.websocket.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        // Create JSON message for offer
        return null;
    }

    private String createAnswerMessage(String fromUserId, String answer) {
        // Create JSON message for answer
        return null;
    }

    private String createIceCandidateMessage(String fromUserId, String candidate) {
        // Create JSON message for ICE candidate
        return null;
    }



}
