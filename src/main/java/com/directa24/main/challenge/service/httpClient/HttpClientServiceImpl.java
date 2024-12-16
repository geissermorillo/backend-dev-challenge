package com.directa24.main.challenge.service.httpClient;

import com.directa24.main.challenge.service.httpClient.bodyHandler.JSonBodyHandler;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientServiceImpl<U> implements IHttpClientService<U> {
    @Override
    public HttpResponse<U> executeGet(String url, Class<U> aClass) {
        try {
            HttpRequest httpRequest = HttpRequest
              .newBuilder(URI.create(url))
              .GET()
              .build();

            return HttpClient
              .newHttpClient()
              .send(httpRequest, new JSonBodyHandler<>(aClass));
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
