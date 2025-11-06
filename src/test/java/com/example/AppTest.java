package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URL;

public class AppTest {

    @Test
    public void testAdd() {
        App app = new App();
        assertEquals(8, app.add(5, 3), "Addition logic should return correct sum");
    }

    @Test
    public void testAddNegativeNumbers() {
        App app = new App();
        assertEquals(-5, app.add(-2, -3), "Addition should handle negatives correctly");
    }

    @Test
    public void testAddWithZero() {
        App app = new App();
        assertEquals(10, app.add(0, 10), "Adding zero should return same number");
    }

    @Test
    public void testMainServerStart() {
        assertDoesNotThrow(() -> {
            int freePort = findFreePort(); 
            Thread serverThread = new Thread(() -> {
                try {
                    App.main(new String[]{String.valueOf(freePort)});
                } catch (IOException e) {
                    fail("Server failed to start: " + e.getMessage());
                }
            });
            serverThread.start();

            Thread.sleep(2000);

            String response = sendHttpRequest("http://localhost:" + freePort + "/");
            assertEquals("Hello World Maven Project!", response);

            serverThread.interrupt();
        }, "Server should start successfully and respond correctly");
    }

    private int findFreePort() throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            return socket.getLocalPort();
        }
    }

    private String sendHttpRequest(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(2000);
        conn.setReadTimeout(2000);

        assertEquals(200, conn.getResponseCode(), "Expected HTTP 200 OK response");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }
}
