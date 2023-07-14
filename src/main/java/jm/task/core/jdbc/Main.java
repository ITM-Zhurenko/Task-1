package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class Main {
    private static final int USERS_COUNT = 4;
    final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
        final UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        for (int i = 0; i < USERS_COUNT; i++) {
            final User user = new User("A" + i, "B", (byte) 30);
            userService.saveUser(user.getName(), user.getLastName(), user.getAge());
            logger.info(String.format("User: %s was added", user.getName()));
        }
        final List<User> users = userService.getAllUsers();
        users.forEach(user -> logger.info(String.format(user.toString())));
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
