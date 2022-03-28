package com.study.springframework.webflux.api.util;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpCookie;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

public class CommonUtil {

    public static String getCookieValue(ServerRequest request, String cookieName) {
        MultiValueMap<String, HttpCookie> cookies = request.cookies();

        for (String key : cookies.keySet()) {
            if (key.equals(cookieName)) {
                return cookies.getFirst(cookieName).getValue();
            }
        }

        return "";
    }

    public static <T, P extends Publisher<T>> Mono<ServerResponse> getDefaultServerResponse(P publisher, Class<T> elementClass) {
        return ok()
                .contentType(APPLICATION_JSON)
                .body(fromPublisher(publisher, elementClass))
                .switchIfEmpty(noContent().build());
    }

    public static Mono<ServerResponse> redirect(String requestUri) {
        return ServerResponse
                .temporaryRedirect(URI.create(requestUri))
                .build();
    }

    public static String getRequestHeaderValue(ServerRequest request, String headerName) {
        return Optional.of(request.headers().header(headerName).get(0)).orElse("");
    }
}
