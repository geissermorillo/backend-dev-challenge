package com.directa24.main.challenge.service.httpClient.bodyHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class JSonBodyHandler<T> implements HttpResponse.BodyHandler<T> {

    private final Class<T> tClass;

    public JSonBodyHandler(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public HttpResponse.BodySubscriber<T> apply(HttpResponse.ResponseInfo responseInfo) {
        HttpResponse.BodySubscriber<String> upstream = HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8);

        return HttpResponse.BodySubscribers.mapping(
                upstream,
                (String body) -> {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        return objectMapper.readValue(body, this.tClass);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
    }
}
