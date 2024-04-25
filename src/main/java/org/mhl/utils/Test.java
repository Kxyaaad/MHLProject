package org.mhl.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author k
 * 2024/4/25 下午5:28
 * @version 1.0
 */
public class Test {

    public static void main(String[] args) throws SQLException {

        String sql = "SELECT * FROM actor";
        Connection connection = JDBCDUtilsByDruid.getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
        while (resultSet.next()) {
            System.out.println("查询" + resultSet.getString(1));
        }

    }

}
