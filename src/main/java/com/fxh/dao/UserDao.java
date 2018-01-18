package com.fxh.dao;


import com.fxh.domin.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDao {

    private final static String MATCH_COUNT_SQL = "select count(*) from t_user where user_name=? and passowrd =? ";

    private final static String UPDATE_LOGIN_INFO_SQL = "update t_user set last_visit=?,last_ip=?,credits=? where user_id=?";

    private JdbcTemplate  jdbcTemplate;

    public User findUserByUserName (final String userName){
        final User user = new User();
        jdbcTemplate.query(MATCH_COUNT_SQL, new Object[]{userName}, new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(userName);
                user.setCredits(rs.getInt("credits"));
            }
        });

        return  user;

    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getMatchCount(String userName,String password){
        String  sqlStr = "select count(*) from t_user where  "
                +" user_name = ?  and password= ?";
        return jdbcTemplate.queryForObject(sqlStr,new Object[]{userName,password},Integer.class);

    }

    public void updateLoginInfo(User user){
        jdbcTemplate.update(UPDATE_LOGIN_INFO_SQL,new Object[]{user.getLastVisit(),
        user.getLastIp(),user.getCredits(),user.getUserId()});

    }




}
