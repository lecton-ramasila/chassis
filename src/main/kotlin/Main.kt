package org.lectonia

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChassisApplication

fun main(args: Array<String>) {
    runApplication<ChassisApplication>(*args)
}