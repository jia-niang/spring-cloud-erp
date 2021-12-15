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
                sqlFirst(),
                sqlSelectColumns(tableInfo, true),
                tableInfo.getTableName(),
                sqlWhereEntityWrapper(true, tableInfo),
                sqlOrderBy(tableInfo),
                sqlComment()
        );
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForTable(mapperClass, joinPivot.getMethod(), sqlSource, tableInfo);
    }
}
