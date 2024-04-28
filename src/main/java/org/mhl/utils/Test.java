package org.mhl.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.mhl.view.MHLView;

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
        new MHLView().mainMenu();
    }

}
