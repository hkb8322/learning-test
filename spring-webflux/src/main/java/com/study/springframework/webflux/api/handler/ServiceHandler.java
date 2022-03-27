package com.study.springframework.webflux.api.handler;

import com.study.springframework.webflux.api.exception.CustomException;
import com.study.springframework.webflux.api.util.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
@AllArgsConstructor
public class ServiceHandler {

    public Mono<ServerResponse> preProcess(ServerRequest request) {
        String token = CommonUtil.getCookieValue(request, "system-token");

        return validateToken(token)
                .flatMap(valid -> {
                    if (!valid) {
                        return CommonUtil.redirect("https://www.nexon.com");
                    }

                    // 세션 생성
                    return  createSessionResponse();
                })
                .onErrorResume(e -> {
                    log.error("{}", "error");
                    return CommonUtil.redirect("https://www.nexon.com");
                });
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return preProcess(request)
                .flatMap(response -> {
                    Map<String, String> result = new HashMap<>();
                    result.put("data", "All");

                    return ServerResponse.from(response)
                            .bodyValue(result);
                });
    }

    public Mono<ServerResponse> getError(ServerRequest request) {
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new CustomException("errorCode", "errorDescription")));
    }


    private Mono<Boolean> validateToken(String token) {
        return Mono.just(StringUtils.hasText(token));
    }

    private Mono<ServerResponse> createSessionResponse() {
        ResponseCookie cookie = ResponseCookie.from("session-id", UUID.randomUUID().toString())
                .path("/")
                .maxAge(Integer.MAX_VALUE)
                .httpOnly(true)
                .build();

        return ok()
                .cookie(cookie)
                .build();
    }
}
