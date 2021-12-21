package com.kabunx.erp.extension.injector.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

public class JoinPivotMethod extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlTemplate joinPivot = SqlTemplate.JOIN_PIVOT;
        String sql = String.format(
                joinPivot.getSql(),
                sqlSelectColumns(tableInfo, true),  // columns
                getTableAlias(tableInfo),                       // table
                sqlJoinInfo(tableInfo),                         // join
                sqlWhereEntityWrapper(true, tableInfo)  // where
        );
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForTable(mapperClass, joinPivot.getMethod(), sqlSource, tableInfo);
    }

    protected String sqlJoinInfo(TableInfo table) {
        return String.format(
                "INNER JOIN ${pivot_table} ON %s.%s = ${pivot_table}.${pivot_related_key}",
                getTableAlias(table),
                table.getKeyProperty()
        );
    }

    protected String getTableAlias(TableInfo tableInfo) {
        return tableInfo.getTableName();
    }
}