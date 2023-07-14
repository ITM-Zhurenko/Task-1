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
            userService.saveUser("A" + i, "B" + i, (byte) (30 + i));
            logger.info(String.format("User: %s was added", "A" + i));
        }
        final List<User> users = userService.getAllUsers();
        users.forEach(user -> logger.info(String.format(user.toString())));
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
