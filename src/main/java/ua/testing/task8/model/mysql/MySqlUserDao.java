package ua.testing.task8.model.mysql;

import ua.testing.task8.model.dao.AbstractJDBCDao;
import ua.testing.task8.model.dao.DaoFactory;
import ua.testing.task8.model.dao.PersistException;
import ua.testing.task8.model.entity.authentication.Role;
import ua.testing.task8.model.entity.authentication.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlUserDao extends AbstractJDBCDao<User, Integer> {

    private class PersistUser extends User {
        public void setId(int id) {
            super.setId(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM dbrf.users";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO dbrf.users (user_name, password, roles_id) \n" +
                "VALUES (?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE dbrf.users \n" +
                "SET user_name = ?, password  = ?, roles_id  = ? \n" +
                "WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM dbrf.users WHERE id= ?;";
    }

    @Override
    public User create() throws PersistException {
        User s = new User();
        return persist(s);
    }

    public MySqlUserDao(DaoFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
        addRelation(User.class, "role");
    }

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<User> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistUser user = new PersistUser();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));

                user.setRole((Role) getDependence(Role.class, rs.getInt("roles_id")));

                result.add(user);

            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object) throws PersistException {
        try {

            int groupId = (object.getRole() == null || object.getRole().getId() == null) ? -1
                    : object.getRole().getId();

            statement.setString(1, object.getUserName());
            statement.setString(2, object.getPassword());
            statement.setInt(3, groupId);

            statement.setInt(4, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User object) throws PersistException {
        try {

            int groupId = (object.getRole() == null || object.getRole().getId() == null) ? -1
                    : object.getRole().getId();

            statement.setString(1, object.getUserName());
            statement.setString(2, object.getPassword());
            statement.setInt(3, groupId);

        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

}