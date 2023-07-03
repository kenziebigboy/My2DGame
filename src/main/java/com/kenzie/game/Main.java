package com.kenzie.game;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class Main {

    public static JFrame window;

    public static void main (String[] args){
/*
        String URLString = "http://localhost:5001/example/fd72eb63-916a-45a2-8d30-949052a7fb68";

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create(URLString);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Accept", "application/json")
                .GET()
                .build();

        try {

            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = httpResponse.statusCode();

            if (statusCode == 200){
                System.out.println(httpResponse.body());
            } else {
                System.out.println(String.format("GET request failed: %d status code received", statusCode));

            }

        } catch (IOException | InterruptedException e){
            System.out.println(e.getMessage());
        }
        login panel
        character panel
        stats panel

        System.exit(0);*/

        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Quest for the JAVA gods");
        new Main().setIcon();

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        gamePanel.config.loadConfig();
        if(gamePanel.fullScreenOn){
            window.setUndecorated(true);
        }

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();

    }

    public void setIcon(){

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("player/boy_down_1.png")));
        window.setIconImage(icon.getImage());
    }

}
