package com.r2dbc.deadlock

import com.r2dbc.deadlock.elements.service.ElementService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class TheApplicationRunner(
    private val elementService: ElementService
) : ApplicationRunner {
    val log: Logger = LoggerFactory.getLogger(TheApplicationRunner::class.java)

    override fun run(args: ApplicationArguments?) {
        elementService.lookup()
                .flatMap(elementService::update)
                .doOnError { log.error("Stream Error", it) }
                .repeat()
            .subscribe()
    }
}
