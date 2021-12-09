package com.kabunx.erp.extension.injector.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

public class SoleMethod extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        SqlTemplate sole = SqlTemplate.SOLE;
        String sql = String.format(
                sole.getSql(),
                sqlFirst(),
                sqlSelectColumns(tableInfo, true),
                tableInfo.getTableName(),
                sqlWhereEntityWrapper(true, tableInfo),
                sqlOrderBy(tableInfo),
                sqlComment()
        );
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addSelectMappedStatementForTable(mapperClass, sole.getMethod(), sqlSource, tableInfo);
    }
}
