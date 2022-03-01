package com.study.springframework.webflux.api.util;

import org.springframework.http.HttpCookie;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

public class CommonUtil {

    public static String getCookieValue(ServerRequest request, String cookieName) {
        MultiValueMap<String, HttpCookie> cookies = request.cookies();

        for (String key : cookies.keySet()) {
            if (key.equals(cookieName)) {
                return cookies.getFirst(key).getValue();
            }
        }

        return null;
    }

    public static Mono<ServerResponse> redirect(String location) {
        return ServerResponse
                .temporaryRedirect(URI.create(location))
                .build();
    }
}
