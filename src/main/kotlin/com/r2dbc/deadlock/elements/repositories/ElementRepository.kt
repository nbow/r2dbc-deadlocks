package com.r2dbc.deadlock.elements.repositories

import com.r2dbc.deadlock.elements.data.Element
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.ConcurrencyFailureException
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class ElementRepository(private val databaseClient: DatabaseClient) {

    val log: Logger = LoggerFactory.getLogger(ElementRepository::class.java)

    val myLookupQuery = """
        Select TOP 100 *
        FROM dbo.Elements
    """.trimIndent()

    val myUpdateQuery = """
        Update dbo.Elements
        SET new_data = :newData
        WHERE id = :id
    """.trimIndent()

    fun findElements(): Flux<Element> {
        return databaseClient
                .execute(myLookupQuery)
                .map { rs ->
                    Element(
                            id = rs.get("id")!!.toString(),
                            data = rs.get("data")!!.toString(),
                            newData = ""
                    )
                }
                .all()
                .onErrorContinue(ConcurrencyFailureException::class.java) { throwable, ob ->
                    log.error("Hey over here!!", throwable)
                    log.error("Hey over here!!", ob)
                    // This never gets called.
                }
    }

    fun updateElement(id: String, newData: String): Mono<Element> {
        return databaseClient
                .execute(myUpdateQuery)
                .bind("id", id)
                .bind("newData", newData)
                .fetch()
                .first()
                .map { rs ->
                    Element(
                            id = rs["id"]!!.toString(),
                            data = rs["data"]!!.toString(),
                            newData = rs["newData"]!!.toString()
                    )
                }
                .onErrorContinue(ConcurrencyFailureException::class.java) { throwable, ob ->
                    log.error("Hey now over here!!", throwable)
                    log.error("Hey now over here!!", ob)
                    // This never gets called.
                }
    }
}
