package dk.kea.taskz.Services;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {

    private static BasicDataSource dataSource = new BasicDataSource();

    private static BasicDataSource ds;

    static {
        dataSource.setUrl("jdbc:mysql://den1.mysql2.gear.host:3306/taskz");
        dataSource.setUsername("taskz");
        dataSource.setPassword("taskz!");
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    private DBCPDataSource() {

    }

}
