package com.supsp.springboot.core.config.interceptors;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.supsp.springboot.core.config.CoreProperties;
import com.supsp.springboot.core.events.TransEventListener;
import com.supsp.springboot.core.threads.CacheTables;
import com.supsp.springboot.core.utils.CacheUtils;
import com.supsp.springboot.core.utils.CommonUtils;
import com.supsp.springboot.core.vo.CacheTable;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.upsert.Upsert;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

@Component
@Slf4j
public class CacheObjectInterceptor extends JsqlParserSupport implements InnerInterceptor {

    @Resource
    private CoreProperties coreProperties;

    @Resource
    private TransEventListener transEventListener;

    @Override
    protected void processSelect(Select select, int index, String sql, Object obj) {
        Set<String> tableList = CommonUtils.getTables(select);
        
        for (String table : tableList) {
//            if (!CommonTools.isEnvProduct()) {
//                log.debug(
//                        """
//                                \n■■■■■ CacheObjectInterceptor
//                                table: {}
//                                """,
//                        table
//                );
//            }
            CacheTable cacheTable = CacheTable.cacheTable(table);
            CacheTables.set(
                    CacheUtils.cacheTableNameKey(cacheTable),
                    cacheTable
            );
        }
    }

    @Override
    protected void processInsert(Insert insert, int index, String sql, Object obj) {
    }

    protected void processUpsert(Upsert upsert, int index, String sql, Object obj) {
    }

    @Override
    protected void processUpdate(Update update, int index, String sql, Object obj) {
    }

    @Override
    protected void processDelete(Delete delete, int index, String sql, Object obj) {

    }

    @Override
    protected String processParser(Statement statement, int index, String sql, Object obj) {
        //
        sql = statement.toString();
        try {
            switch (statement) {
                case Select select -> {
                    this.processSelect((Select) statement, index, sql, obj);
                }
                case Insert insert -> {
                    transEventListener.publish(
                            statement,
                            sql
                    );
                }
                case Upsert upsert -> {
                    transEventListener.publish(
                            statement,
                            sql
                    );
                }
                case Update update -> {
                    transEventListener.publish(
                            statement,
                            sql
                    );
                }
                case Delete delete -> {
                    transEventListener.publish(
                            statement,
                            sql
                    );
                }
                default -> {
                }
            }

            if (statement instanceof Insert) {
                this.processInsert((Insert) statement, index, sql, obj);
            } else if (statement instanceof Select) {
                this.processSelect((Select) statement, index, sql, obj);
            } else if (statement instanceof Update) {
                this.processUpdate((Update) statement, index, sql, obj);
            } else if (statement instanceof Delete) {
                this.processDelete((Delete) statement, index, sql, obj);
            }
            transEventListener.publish(
                    statement,
                    sql
            );
        } catch (Exception e) {
            // log.error("transEventListener error:" + e ) ;
        }
        return sql;
    }

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        // log.debug("■■■■■ CacheObjectInterceptor beforeQuery");
        SqlCommandType sct = ms.getSqlCommandType();
        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        String sql = this.parserSingle(mpBs.sql(), mpBs);
        mpBs.sql(this.parserSingle(mpBs.sql(), mpBs));
    }

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        // log.debug("■■■■■ CacheObjectInterceptor beforePrepare");
        PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
        MappedStatement ms = mpSh.mappedStatement();
        SqlCommandType sct = ms.getSqlCommandType();
        PluginUtils.MPBoundSql mpBs = mpSh.mPBoundSql();
        mpBs.sql(this.parserMulti(mpBs.sql(), mpBs));
    }
}
