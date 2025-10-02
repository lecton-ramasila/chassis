package org.lectonia.controller

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class HealthController(private val meterRegistry: MeterRegistry) {

    private val logger = LoggerFactory.getLogger(HealthController::class.java)
    private val healthCheckCounter = Counter.builder("health_check_total")
        .description("Total health check requests")
        .register(meterRegistry)
    private val requestTimer = Timer.builder("api_request_duration")
        .description("API request duration")
        .register(meterRegistry)

    @GetMapping("/health")
    fun health(): Map<String, String> {
        healthCheckCounter.increment()
        logger.info("Health check requested")
        return requestTimer.recordCallable {
            mapOf("status" to "UP", "service" to "chassis")
        }!!
    }

    @GetMapping("/info")
    fun info(): Map<String, Any> {
        logger.info("Application info requested")
        return requestTimer.recordCallable {
            mapOf(
                "application" to "chassis",
                "version" to "1.0-SNAPSHOT",
                "description" to "Spring Boot application with Swagger, Prometheus, and Grafana"
            )
        }
    }
}