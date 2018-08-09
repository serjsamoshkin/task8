package ua.testing.task8.controller;

import ua.testing.task8.model.dao.DaoFactory;
import ua.testing.task8.model.dao.GenericDao;
import ua.testing.task8.model.dao.PersistException;
import ua.testing.task8.model.entity.authentication.Role;
import ua.testing.task8.model.entity.authentication.User;
import ua.testing.task8.model.mysql.MySqlDaoFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "MainServlet",
        urlPatterns = {"/servlet"})
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print("Hello from servlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DaoFactory<Connection> factory = new MySqlDaoFactory();
        Connection connection = factory.getContext();

        GenericDao<User, Integer> daoUser = factory.getDao(connection, User.class);
        GenericDao<Role, Integer> daoRole = factory.getDao(connection, Role.class);


        String userName = request.getParameter("name");
        String userPassword = request.getParameter("password");


        User user = new User();
        user.setUserName(userName);
        user.setPassword(userPassword);


        Role role = daoRole.getAll().stream().filter(r -> r.getRoleName().equals("User")).findFirst().orElseGet(Role::new);
        role.setRoleName("User");

        user.setRole(role);

        try {
            daoUser.persist(user);
        }catch (PersistException e){
            request.setAttribute("incorrect_name", "Пользователь с таким именем уже зарегестрирован в системе");
            request.setAttribute("name_r", userName);
            request.getServletContext().getRequestDispatcher("/").forward(request, response);
        }

    }
}
