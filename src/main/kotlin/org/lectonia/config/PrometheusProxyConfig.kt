package org.lectonia.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class PrometheusProxyConfig {

    @Bean
    fun webClient(): WebClient = WebClient.builder().build()

    @Bean
    fun prometheusProxyRoutes(webClient: WebClient): RouterFunction<ServerResponse> = router {
        GET("/prometheus/**") { request ->
            val path = request.path().removePrefix("/prometheus")
            val queryParams = request.queryParams()
            
            webClient.get()
                .uri { uriBuilder ->
                    uriBuilder.scheme("http")
                        .host("localhost")
                        .port(9090)
                        .path(path)
                        .also { builder ->
                            queryParams.forEach { (key, values) ->
                                builder.queryParam(key, *values.toTypedArray())
                            }
                        }
                        .build()
                }
                .retrieve()
                .bodyToMono(String::class.java)
                .onErrorReturn("<h1>Prometheus not available</h1><p>Prometheus service is not running. Use Docker Compose locally or check <a href='/actuator/prometheus'>raw metrics</a></p>")
                .flatMap { body ->
                    ServerResponse.ok()
                        .header("Content-Type", "text/html")
                        .bodyValue(body)
                }
        }
    }
}