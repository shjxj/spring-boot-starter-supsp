package com.supsp.springboot.core.utils;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class DBUtils {

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    public SqlSession getSqlSession() {
        return SqlSessionUtils.getSqlSession(
                sqlSessionTemplate.getSqlSessionFactory(),
                sqlSessionTemplate.getExecutorType(),
                sqlSessionTemplate.getPersistenceExceptionTranslator()
        );
    }

    public void closeSqlSession(SqlSession session) {
        SqlSessionUtils.closeSqlSession(
                session,
                sqlSessionTemplate.getSqlSessionFactory()
        );
    }

    public List<Map<String, Object>> excuteQuerySql(String sql) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        PreparedStatement pst = null;
        SqlSession session = getSqlSession();
        ResultSet result = null;
        try {
            pst = session.getConnection().prepareStatement(sql);
            result = pst.executeQuery();
            //获得结果集结构信息,元数据
            ResultSetMetaData md = result.getMetaData();
            //获得列数
            int columnCount = md.getColumnCount();
            while (result.next()) {
                Map<String, Object> rowData = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), result.getObject(i));
                }
                list.add(rowData);
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            log.error(
                   "excuteQuerySql error:",
                    e
            );
        } finally {
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    log.error(
                            "excuteQuerySql error:",
                            e
                    );
                }
            }
            closeSqlSession(session);
        }
        return list;
    }

}
