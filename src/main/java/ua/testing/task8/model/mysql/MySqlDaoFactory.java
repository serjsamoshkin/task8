package ua.testing.task8.model.mysql;


import ua.testing.task8.model.dao.DaoFactory;
import ua.testing.task8.model.dao.GenericDao;
import ua.testing.task8.model.dao.PersistException;
import ua.testing.task8.model.entity.authentication.Role;
import ua.testing.task8.model.entity.authentication.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MySqlDaoFactory implements DaoFactory<Connection> {

    private String user = "root";//Логин пользователя
    private String password = "SDgsdgas&567Ig";//Пароль пользователя
    private String url = "jdbc:mysql://localhost:3306/dbrf";//URL адрес
    private String driver = "com.mysql.jdbc.Driver";//Имя драйвера
    private Map<Class, DaoCreator> creators;

    public Connection getContext() throws PersistException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return  connection;
    }

    @Override
    public GenericDao getDao(Connection connection, Class dtoClass) throws PersistException {
        DaoCreator creator = creators.get(dtoClass);
        if (creator == null) {
            throw new PersistException("Dao object for " + dtoClass + " not found.");
        }
        return creator.create(connection);
    }

    public MySqlDaoFactory() {
        creators = new HashMap<>();
        creators.put(User.class, (DaoCreator<Connection>) connection -> new MySqlUserDao(MySqlDaoFactory.this, connection));
        creators.put(Role.class, (DaoCreator<Connection>) connection -> new MySqlRoleDao(MySqlDaoFactory.this, connection));
    }
}