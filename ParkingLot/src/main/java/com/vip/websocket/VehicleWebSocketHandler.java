
package com.vip.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vip.entity.Vehicle;
import com.vip.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class VehicleWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new ArrayList<>();

    @Autowired
    private VehicleService vehicleService;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        List<Vehicle> vehicles = vehicleService.getAllVehicle();
        String message = new ObjectMapper().writeValueAsString(vehicles);

        session.sendMessage(new TextMessage(message));  // Notify the client

        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    public void sendMessage(String message) {
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
