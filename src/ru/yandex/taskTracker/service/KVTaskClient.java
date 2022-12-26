package ru.yandex.taskTracker.service;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {

    String url;
    String token;
    HttpClient kvClient;

    public KVTaskClient(String url) throws IOException, InterruptedException {
        this.url = url;
        kvClient = HttpClient.newHttpClient();
        URI uri = URI.create(url + "register");
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        HttpRequest request = requestBuilder
                .GET()
                .uri(uri)
                .build();
        HttpResponse<String> response = kvClient.send(request, HttpResponse.BodyHandlers.ofString());
        token = response.body();
    }

    void put(String key, String json) throws IOException, InterruptedException {

        URI uri = URI.create(url + "save/" + key + "?API_TOKEN=" + token);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        HttpRequest request = requestBuilder
                .POST(body)
                .uri(uri)
                .build();
        kvClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    String load(String key) throws IOException, InterruptedException {

        URI uri = URI.create(url + "load/" + key + "?API_TOKEN=" + token);
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        HttpRequest request = requestBuilder
                .GET()
                .uri(uri)
                .build();
        HttpResponse<String> result = kvClient.send(request, HttpResponse.BodyHandlers.ofString());
        return result.body();
    }
}
