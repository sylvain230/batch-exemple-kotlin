package fr.perso.exemple.batchexemplekotlin.stepexampleone.reader

import org.springframework.batch.item.database.JdbcCursorItemReader

class PersoBatchJdbcCursorItemReader<T>(
    var ordre: String? = null
): JdbcCursorItemReader<Any>(), Comparable<PersoBatchJdbcCursorItemReader<Any?>> {

    override fun compareTo(other: PersoBatchJdbcCursorItemReader<Any?>): Int {
        return ordre?.compareTo(other.ordre!!) ?: throw Exception()
    }
}