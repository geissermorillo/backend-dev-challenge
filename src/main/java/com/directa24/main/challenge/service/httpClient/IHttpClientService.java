package com.directa24.main.challenge.service.httpClient;

import java.net.http.HttpResponse;

public interface IHttpClientService<T> {
    HttpResponse<T> executeGet(String url, Class<T> aClass);
}
