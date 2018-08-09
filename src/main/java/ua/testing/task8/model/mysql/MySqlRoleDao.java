package ua.testing.task8.model.mysql;

import ua.testing.task8.model.dao.AbstractJDBCDao;
import ua.testing.task8.model.dao.DaoFactory;
import ua.testing.task8.model.dao.PersistException;
import ua.testing.task8.model.entity.authentication.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySqlRoleDao extends AbstractJDBCDao<Role, Integer> {

    private class PersistRole extends Role {
        public void setId(int id) {
            super.setId(id);
        }
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM dbrf.roles";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO dbrf.roles (role_name) \n" +
                "VALUES (?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE dbrf.roles \n" +
                "SET role_name = ? \n" +
                "WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM dbrf.roles WHERE id= ?;";
    }

    @Override
    public Role create() throws PersistException {
        Role r = new Role();
        return persist(r);
    }

    public MySqlRoleDao(DaoFactory<Connection> parentFactory, Connection connection) {
        super(parentFactory, connection);
    }

    @Override
    protected List<Role> parseResultSet(ResultSet rs) throws PersistException {
        LinkedList<Role> result = new LinkedList<>();
        try {
            while (rs.next()) {
                PersistRole role = new PersistRole();
                role.setId(rs.getInt("id"));
                role.setRoleName(rs.getString("role_name"));

                result.add(role);

            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Role object) throws PersistException {
        try {
            statement.setString(1, object.getRoleName());
            statement.setInt(2, object.getId());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Role object) throws PersistException {
        try {
            statement.setString(1, object.getRoleName());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

}