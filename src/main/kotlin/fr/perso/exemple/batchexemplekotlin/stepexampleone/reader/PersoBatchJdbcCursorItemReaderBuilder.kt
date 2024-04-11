package fr.perso.exemple.batchexemplekotlin.stepexampleone.reader

import org.springframework.jdbc.core.*
import org.springframework.util.Assert
import org.springframework.util.StringUtils
import javax.sql.DataSource


class PersoBatchJdbcCursorItemReaderBuilder<T : Any> {
    private lateinit var dataSource: DataSource
    private var fetchSize = -1
    private var maxRows = -1
    private var queryTimeout = -1
    private var ignoreWarnings = false
    private var verifyCursorPosition = true
    private var driverSupportsAbsolute = false
    private var useSharedExtendedConnection = false
    private lateinit var preparedStatementSetter: PreparedStatementSetter
    private lateinit var sql: String
    private lateinit var rowMapper: RowMapper<Any?>
    private var saveState = true
    private lateinit var name: String
    private var maxItemCount = Int.MAX_VALUE
    private var currentItemCount = 0
    private var connectionAutoCommit = false
    fun saveState(saveState: Boolean): PersoBatchJdbcCursorItemReaderBuilder<T> {
        this.saveState = saveState
        return this
    }

    fun name(name: String): PersoBatchJdbcCursorItemReaderBuilder<T> {
        this.name = name
        return this
    }

    fun maxItemCount(maxItemCount: Int): PersoBatchJdbcCursorItemReaderBuilder<T> {
        this.maxItemCount = maxItemCount
        return this
    }

    fun currentItemCount(currentItemCount: Int): PersoBatchJdbcCursorItemReaderBuilder<T> {
        this.currentItemCount = currentItemCount
        return this
    }

    fun dataSource(dataSource: DataSource): PersoBatchJdbcCursorItemReaderBuilder<T> {
        this.dataSource = dataSource
        return this
    }

    fun fetchSize(fetchSize: Int): PersoBatchJdbcCursorItemReaderBuilder<T> {
        this.fetchSize = fetchSize
        return this
    }

    fun maxRows(maxRows: Int): PersoBatchJdbcCursorItemReaderBuilder<T> {
        this.maxRows = maxRows
        return this
    }

    fun queryTimeout(queryTimeout: Int): PersoBatchJdbcCursorItemReaderBuilder<T> {
        this.queryTimeout = queryTimeout
        return this
    }

    fun ignoreWarnings(ignoreWarnings: Boolean): PersoBatchJdbcCursorItemReaderBuilder<T> {
        this.ignoreWarnings = ignoreWarnings
        return this
    }

    fun verifyCursorPosition(verifyCursorPosition: Boolean): PersoBatchJdbcCursorItemReaderBuilder<T> {
        this.verifyCursorPosition = verifyCursorPosition
        return this
    }

    fun driverSupportsAbsolute(driverSupportsAbsolute: Boolean): PersoBatchJdbcCursorItemReaderBuilder<T> {
        this.driverSupportsAbsolute = driverSupportsAbsolute
        return this
    }

    fun useSharedExtendedConnection(useSharedExtendedConnection: Boolean): PersoBatchJdbcCursorItemReaderBuilder<T> {
        this.useSharedExtendedConnection = useSharedExtendedConnection
        return this
    }

    fun preparedStatementSetter(preparedStatementSetter: PreparedStatementSetter): PersoBatchJdbcCursorItemReaderBuilder<T> {
        this.preparedStatementSetter = preparedStatementSetter
        return this
    }

    fun queryArguments(vararg args: Any?): PersoBatchJdbcCursorItemReaderBuilder<T> {
        preparedStatementSetter = ArgumentPreparedStatementSetter(args)
        return this
    }

    fun queryArguments(args: Array<Any?>?, types: IntArray?): PersoBatchJdbcCursorItemReaderBuilder<T> {
        preparedStatementSetter = ArgumentTypePreparedStatementSetter(args, types)
        return this
    }

    fun queryArguments(args: List<*>): PersoBatchJdbcCursorItemReaderBuilder<T> {
        Assert.notNull(args, "The list of arguments must not be null")
        preparedStatementSetter = ArgumentPreparedStatementSetter(args.toTypedArray())
        return this
    }

    fun sql(sql: String): PersoBatchJdbcCursorItemReaderBuilder<T> {
        this.sql = sql
        return this
    }

    fun rowMapper(rowMapper: RowMapper<Any?>): PersoBatchJdbcCursorItemReaderBuilder<T> {
        this.rowMapper = rowMapper
        return this
    }

    fun beanRowMapper(mappedClass: Class<Any?>): PersoBatchJdbcCursorItemReaderBuilder<T> {
        rowMapper = BeanPropertyRowMapper(mappedClass)
        return this
    }

    fun connectionAutoCommit(connectionAutoCommit: Boolean): PersoBatchJdbcCursorItemReaderBuilder<T> {
        this.connectionAutoCommit = connectionAutoCommit
        return this
    }

    fun build(): PersoBatchJdbcCursorItemReader<Any?> {
        if (saveState) {
            Assert.hasText(name, "A name is required when saveState is set to true")
        }
        Assert.hasText(sql, "A query is required")
        Assert.notNull(dataSource, "A datasource is required")
        Assert.notNull(rowMapper, "A rowmapper is required")
        val reader: PersoBatchJdbcCursorItemReader<Any?> = PersoBatchJdbcCursorItemReader("0")
        if (StringUtils.hasText(name)) {
            reader.setName(name)
        }
        reader.setSaveState(saveState)
        reader.setPreparedStatementSetter(preparedStatementSetter)
        reader.setRowMapper(rowMapper)
        reader.setSql(sql)
        reader.setCurrentItemCount(currentItemCount)
        reader.setDataSource(dataSource)
        reader.setDriverSupportsAbsolute(driverSupportsAbsolute)
        reader.setFetchSize(fetchSize)
        reader.setIgnoreWarnings(ignoreWarnings)
        reader.setMaxItemCount(maxItemCount)
        reader.setMaxRows(maxRows)
        reader.setQueryTimeout(queryTimeout)
        reader.setUseSharedExtendedConnection(useSharedExtendedConnection)
        reader.setVerifyCursorPosition(verifyCursorPosition)
        reader.setConnectionAutoCommit(connectionAutoCommit)

        // On assigne le nom du reader.
        reader.ordre = name
        return reader
    }
}