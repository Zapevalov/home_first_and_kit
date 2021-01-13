package com.homekit.home_first_and_kit

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
@EnableJpaAuditing
class HomeFirstAndKitApplication

fun main(args: Array<String>) {
	runApplication<HomeFirstAndKitApplication>(*args)
}

