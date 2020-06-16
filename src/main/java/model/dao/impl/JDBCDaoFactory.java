package model.dao.impl;


import model.dao.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

    private final DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(getConnection());
    }

    @Override
    public AuthorDao createAuthorDao() {
        return new JDBCAuthorDao(getConnection());
    }

    @Override
    public BookDao createBookDao() {
        return new JDBCBookDao(getConnection());
    }

    @Override
    public ShelfDao createShelfDao() {
        return new JDBCShelfDao(getConnection());
    }

    @Override
    public TagDao createTagDao() {
        return new JDBCTagDao(getConnection());
    }

    @Override
    public OrderDao createOrderDao() {
        return new JDBCOrderDao(getConnection());
    }

    @Override
    public AuthorSearchDao createAuthorSearchDao() {
        return new JDBCAuthorSearchDao(getConnection());
    }

    @Override
    public TagSearchDao createTagSearchDao() {
        return new JDBCTagSearchDao(getConnection());
    }


    private Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
