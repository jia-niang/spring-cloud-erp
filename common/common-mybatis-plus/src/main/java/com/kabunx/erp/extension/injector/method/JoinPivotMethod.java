package com.kabunx.erp.extension.injector.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

public class JoinPivotMethod extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlTemplate joinPivot = SqlTemplate.JOIN_PIVOT_WRAPPER;
        String sql = String.format(
                joinPivot.getSql(),
                customSqlSelectColumns(tableInfo),              // columns
                tableInfo.getTableName(),                       // table
                sqlJoinInfo(tableInfo),                         // join
                sqlWhereEntityWrapper(true, tableInfo)  // where
        );
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForTable(mapperClass, joinPivot.getMethod(), sqlSource, tableInfo);
    }

    // 凭借中间表字段
    protected String customSqlSelectColumns(TableInfo tableInfo) {
        return sqlSelectColumns(tableInfo, true) + ",${pivot_table}.${pivot_foreign_key} AS pivot_foreign_id";
    }

    protected String sqlJoinInfo(TableInfo table) {
        return String.format(
                "INNER JOIN ${pivot_table} ON %s.%s = ${pivot_table}.${pivot_related_key}",
                table.getTableName(),
                table.getKeyProperty()
        );
    }
}