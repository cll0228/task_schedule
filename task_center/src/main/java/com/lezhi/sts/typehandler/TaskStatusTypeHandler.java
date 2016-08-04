package com.lezhi.sts.typehandler;

import com.lezhi.sts.enums.TaskStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskStatusTypeHandler extends BaseTypeHandler<TaskStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, TaskStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.dbval);
    }

    @Override
    public TaskStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int dbval = rs.getInt(columnName);
        return fromDb(dbval);
    }

    @Override
    public TaskStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int dbval = rs.getInt(columnIndex);
        return fromDb(dbval);
    }

    @Override
    public TaskStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int dbval = cs.getInt(columnIndex);
        return fromDb(dbval);
    }


    private TaskStatus fromDb(Serializable dbval) {
        if (dbval == null) {
            return null;
        }

        for (TaskStatus ms : TaskStatus.values()) {
            if (dbval.equals(ms.dbval)) {
                return ms;
            }
        }
        return null;
    }
}