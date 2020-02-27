package com.r2dbc.deadlock.elements.service

import com.r2dbc.deadlock.elements.data.Element
import com.r2dbc.deadlock.elements.repositories.ElementRepository
import java.util.function.Function
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.ConcurrencyFailureException
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ElementService(private val elementRepository: ElementRepository) {

    val log: Logger = LoggerFactory.getLogger(ElementService::class.java)

    fun lookup(): Flux<Element> {
        return elementRepository.findElements()
                .doOnSubscribe { log.info("Looking for elements") }
                .retry(5) { it is ConcurrencyFailureException }
                .collectList()
                .flatMapIterable(Function.identity())
                .onErrorResume {
                    log.error("Error encountered on lookup", it)
                    Flux.empty<Element>()
                }
    }

    fun update(element: Element): Mono<Element> {
        return elementRepository.updateElement(element.id, element.newData)
                .thenReturn(element)
                .doOnNext {
                    log.info("Processed element {}", mapOf(
                            "element" to it
                    ))
                }
                .retry(5) { it is ConcurrencyFailureException }
                .onErrorResume {
                    log.error("Error encountered updating: $it")
                    Mono.empty<Element>()
                }
    }
}
