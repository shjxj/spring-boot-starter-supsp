package com.supsp.springboot.core.events;

import com.supsp.springboot.core.config.CoreProperties;
import com.supsp.springboot.core.enums.StatementType;
import com.supsp.springboot.core.threads.CacheTables;
import com.supsp.springboot.core.utils.CacheUtils;
import com.supsp.springboot.core.utils.CommonUtils;
import com.supsp.springboot.core.utils.StrUtils;
import com.supsp.springboot.core.vo.CacheTable;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.upsert.Upsert;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class TransEventListener {

    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Resource
    private CoreProperties coreProperties;

    private final static List<StatementType> updateTypes = Arrays.asList(
            StatementType.Insert,
            StatementType.Update,
            StatementType.Upsert,
            StatementType.Delete,
            StatementType.Truncate
    );

    /**
     * 读操作
     *
     * @param event
     */
    private void eventReadTables(TransEvent event) {
//        if (!CommonTools.isEnvProduct()) {
//            log.debug(
//                    "■■■■■■ CACHE [READ TABLES] ■■■■■■\n{}\n{}",
//                    event.getSql(),
//                    JsonUtil.toJSONString(event.getTables())
//            );
//        }
        if (
                event == null
                        || event.getStatementType() == null
                        || !event.getStatementType().equals(StatementType.Select)
                        || CommonUtils.isEmpty(event.getTables())
        ) {
            return;
        }

        // Map<String, CacheTable> cacheTableMap = new HashMap<>();
        for (String table : event.getTables()) {
            CacheTable cacheTable = CacheTable.cacheTable(table);
            CacheTables.set(
                    CacheUtils.cacheTableNameKey(cacheTable),
                    cacheTable
            );
        }
    }

    /**
     * 写操作
     *
     * @param event
     */
    private void eventWriteTables(TransEvent event) {
//        if (!CommonTools.isEnvProduct()) {
//            log.debug(
//                    "■■■■■■ CACHE [WRITE TABLES] ■■■■■■\n{}\n{}",
//                    event.getSql(),
//                    JsonUtil.toJSONString(event.getTables())
//            );
//        }

        if (
                event == null
                        || event.getStatementType() == null
                        || !updateTypes.contains(event.getStatementType())
                        || CommonUtils.isEmpty(event.getTables())
        ) {
            return;
        }

        List<String> tables = event.getTables().stream().toList();

        for (String table : tables) {
            CacheTable cacheTable = CacheTable.cacheTable(table);
            CacheUtils.setCacheTable(cacheTable);
        }
    }

    /**
     * 消费 sql 事件
     *
     * @param event
     */
    @TransactionalEventListener(
            fallbackExecution = true,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void processTransactionalEvent(TransEvent event) {
        if (
                event == null
                        || event.getStatementType() == null
                        || CommonUtils.isEmpty(event.getTables())
        ) {
            return;
        }

        if (event.getStatementType().equals(StatementType.None)) {
            return;
        }

        if (event.getStatementType().equals(StatementType.Select)) {
            // eventReadTables(event);
            return;
        }

        eventWriteTables(event);
    }

    /**
     * 发布 sql 事件
     *
     * @param statement
     * @param sql
     */
    public void publish(
            Statement statement,
            String sql
    ) {
        if (statement == null || StrUtils.isEmpty(sql)) {
            return;
        }
        StatementType statementType = getStatementType(statement);
        Set<String> tableList = CommonUtils.getTables(statement);
//        if (!CommonTools.isEnvProduct()) {
//            log.debug(
//                    "■■■■■■ CACHE [TABLES] ■■■■■■\n{}\n{}",
//                    sql,
//                    JsonUtil.toJSONString(tableList)
//            );
//        }
        TransEvent event = new TransEvent(this);
        event.setStatementType(statementType);
        event.setSql(sql);
        event.setTables(tableList);
        publish(event);
    }

    private static StatementType getStatementType(Statement statement) {
        StatementType statementType = StatementType.None;
        switch (statement) {
            case Select select -> statementType = StatementType.Select;
            case Insert insert -> statementType = StatementType.Insert;
            case Upsert upsert -> statementType = StatementType.Upsert;
            case Update update -> statementType = StatementType.Update;
            case Delete delete -> statementType = StatementType.Delete;
            case Truncate truncate -> statementType = StatementType.Truncate;
            default -> {
            }
        }
        return statementType;
    }

    /**
     * 发布 sql 事件
     *
     * @param event
     */
    public void publish(TransEvent event) {
        applicationEventPublisher.publishEvent(event);
    }

}
