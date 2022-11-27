package com.tobyspring.studytobyspring.dao;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import com.tobyspring.studytobyspring.domain.User;
import com.tobyspring.studytobyspring.enums.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Component
public class UserDao {
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    private final int MAX_RETRY = 3;

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setLevel(Level.valueOf(rs.getInt("level")));
        user.setLogin(rs.getInt("login"));
        user.setRecommend(rs.getInt("recommend"));
        return user;
    };


    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    public void add(final User user) {
        this.jdbcTemplate.update("insert into users(id, name, password, level, login, recommend) values (?, ?, ?, ?, ?, ?)",
                user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend());
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?",
                new Object[]{id}, userRowMapper);
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        this.jdbcTemplate.update("delete from users");
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        return this.jdbcTemplate.query(con -> con.prepareStatement("select count(*) from users"), rs -> {
            rs.next();
            return rs.getInt(1);
        });
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id", userRowMapper);
    }

}
