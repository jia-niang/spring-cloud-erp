package com.kabunx.erp.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@MappedJdbcTypes(value = {JdbcType.OTHER}, includeNullJdbcType = true)
public class ArrayJsonTypeHandler<E> extends BaseTypeHandler<E[]> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E[] parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public E[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public E[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public E[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
