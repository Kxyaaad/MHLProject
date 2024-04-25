package org.mhl.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.mhl.utils.JDBCDUtilsByDruid;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author k
 * 2024/4/25 下午2:16
 * @version 1.0
 */
public abstract class BasicDao<T> {

    QueryRunner queryRunner = new QueryRunner();

    //开发通用DML方法
    public int update(String sql, Object... parameters) {
        Connection connection = null;
        try {
            connection =  JDBCDUtilsByDruid.getConnection();
            return queryRunner.update(connection, sql, parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCDUtilsByDruid.close(null, null, connection);
        }
    }

    public List<T> queryMulti(String sql, Class<T> clazz, Object...parameters) {
        Connection connection = null;
        try {
            connection = JDBCDUtilsByDruid.getConnection();
            return queryRunner.query(connection, sql,new  BeanListHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCDUtilsByDruid.close(null, null, connection);
        }
    }

    public T querySingle(String sql, Class<T> clazz,Object...parameters) {
        Connection connection = null;
        try {
            connection = JDBCDUtilsByDruid.getConnection();
            return queryRunner.query(connection, sql,new BeanHandler<T>(clazz), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCDUtilsByDruid.close(null, null, connection);
        }
    }

    public Object queryScalar(String sql, Object...parameters) {
        Connection connection = null;
        try {
            connection = JDBCDUtilsByDruid.getConnection();
            return queryRunner.query(connection, sql,new ScalarHandler<>(), parameters);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCDUtilsByDruid.close(null, null, connection);
        }
    }
}
