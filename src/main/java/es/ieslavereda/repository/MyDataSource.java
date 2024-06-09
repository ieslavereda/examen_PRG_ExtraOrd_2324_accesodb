package es.ieslavereda.repository;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;

public class MyDataSource {
    public static DataSource getMySQLDataSorce() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("examen");
        dataSource.setPassword("1234");
        dataSource.setURL("jdbc:mysql://localhost:3306/examenPRG2324");
        return dataSource;
    }
}
