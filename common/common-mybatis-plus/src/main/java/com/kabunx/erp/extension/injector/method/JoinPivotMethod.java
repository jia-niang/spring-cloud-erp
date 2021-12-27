package com.kabunx.erp.extension.injector.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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

    /**
     * 拼接中间表字段
     * 由于存在中间表，所以带上表名
     */
    protected String customSqlSelectColumns(TableInfo tableInfo) {
        // 假设存在用户自定义的 resultMap 映射返回
        String selectColumns = ASTERISK;
        if (tableInfo.getResultMap() == null || tableInfo.isAutoInitResultMap()) {
            // 未设置 resultMap 或者 resultMap 是自动构建的,视为属于mp的规则范围内
            selectColumns = tableInfo.getAllSqlSelect();
        }
        // 拼接上表名，避免关联表的字段冲突
        selectColumns = sqlJoinSelectColumns(tableInfo.getTableName(), selectColumns);
        return convertChooseEwSelect(selectColumns);
    }

    protected String sqlJoinInfo(TableInfo table) {
        return String.format(
                "INNER JOIN ${pivot_table} ON %s.%s = ${pivot_table}.${pivot_related_key}",
                table.getTableName(),
                table.getKeyProperty()
        );
    }

    protected String sqlJoinSelectColumns(String tableName, String selectColumns) {
        String[] columns = selectColumns.split(",");
        List<String> tableColumns = new ArrayList<>();
        for (String column : columns) {
            if (!column.startsWith(tableName)) {
                column = tableName + "." + column;
            }
            tableColumns.add(column);
        }
        tableColumns.add("${pivot_table}.${pivot_foreign_key} AS pivot_foreign_id");
        return StringUtils.join(tableColumns, ",");
    }
}