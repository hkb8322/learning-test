package com.study.springframework.webflux.api.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class ServiceHandler {
    // TODO: 추후 고정 데이터가 아닌 NOSQL/RDBMS 연동을 통하여 조회하도록 수정할 것
    Map<String, String> result = new HashMap<>();

    public Mono<ServerResponse> findAll(ServerRequest request) {
        result.put("data", "All");
        return ok().contentType(MediaType.APPLICATION_JSON).bodyValue(result);
    }
}
