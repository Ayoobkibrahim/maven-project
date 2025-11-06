package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;
import static org.awaitility.Awaitility.await;

class AppTest {

    @Test
    void testAdd() {
        App app = new App();
        assertEquals(8, app.add(5, 3));
    }

    @Test
    void testAddNegativeNumbers() {
        App app = new App();
        assertEquals(-5, app.add(-2, -3));
    }

    @Test
    void testAddWithZero() {
        App app = new App();
        assertEquals(10, app.add(0, 10));
    }

    @Test
    void testMainServerStart() {
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

            await().atMost(5, TimeUnit.SECONDS)
                    .until(() -> isServerRunning(freePort));

            String response = sendHttpRequest("http://localhost:" + freePort + "/");
            assertEquals("Hello World Maven Project!", response);

            serverThread.interrupt();
        });
    }

    private int findFreePort() throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            return socket.getLocalPort();
        }
    }

    private String sendHttpRequest(String urlStr) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(urlStr))
                                         .timeout(java.time.Duration.ofSeconds(3))
                                         .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Expected HTTP 200 OK response");
        return response.body();
    }

    private boolean isServerRunning(int port) {
        try (Socket socket = new Socket("localhost", port)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
