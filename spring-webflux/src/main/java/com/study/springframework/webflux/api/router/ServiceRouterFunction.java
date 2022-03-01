package com.study.springframework.webflux.api.router;

import com.study.springframework.webflux.api.handler.ServiceHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Slf4j
@Configuration
@AllArgsConstructor
public class ServiceRouterFunction {

    @Bean
    public RouterFunction<?> routerFunction(ServiceHandler serviceHandler) {
        return route().path("/api/v1/test", builder -> builder
                .GET("/", accept(MediaType.ALL), serviceHandler::findAll)
                .GET("/hello-world", accept(MediaType.ALL), request -> ServerResponse.ok().body(fromObject("Hello-World")))
                .GET("/sub1", accept(MediaType.APPLICATION_JSON), serviceHandler::findAll)
                .GET("/sub1/{id}", accept(MediaType.APPLICATION_JSON), serviceHandler::findAll)
                .GET("/sub2", accept(MediaType.APPLICATION_JSON), serviceHandler::findAll)
                .GET("/sub2/{id}", accept(MediaType.APPLICATION_JSON), serviceHandler::findAll)
                .GET("/sub3", accept(MediaType.APPLICATION_JSON), serviceHandler::findAll)
        ).build();
    }
}
