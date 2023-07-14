package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoJDBCImpl.class);

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute("""
                    DROP TABLE IF EXISTS users;
                    create table users
                    (
                        id        bigserial       not null,
                        name      varchar(255) not null,
                        last_name varchar(255) not null,
                        age       integer      not null,
                        primary key (id)
                    );
                        
                    alter table users
                        owner to postgres;""");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute("DROP TABLE IF EXISTS users;");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (final PreparedStatement statement =
                     Util.getConnection().prepareStatement("insert into users (name, last_name, age) values (?,?,?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try (final PreparedStatement statement =
                     Util.getConnection().prepareStatement("delete from users where id = ?")) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        try (final Statement statement = Util.getConnection().createStatement()) {
            final ResultSet rs = statement.executeQuery("SELECT * FROM users");
            final List<User> users = new ArrayList<>();
            while (rs.next()) {
                final User user = new User(rs.getString(2), rs.getString(3), rs.getByte(4));
                user.setId(rs.getLong(1));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public void cleanUsersTable() {
        try (final Statement statement = Util.getConnection().createStatement()) {
            statement.execute("TRUNCATE TABLE users");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}
