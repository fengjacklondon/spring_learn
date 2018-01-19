package com.fxh.service;

import com.fxh.dao.LoginLogDao;
import com.fxh.dao.UserDao;
import com.fxh.domin.LoginLog;
import com.fxh.domin.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class UserService {
    private UserDao userDao;
    private LoginLogDao loginLogDao;




    @Autowired
    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    @Autowired
    public void setLoginLogDao(LoginLogDao loginLogDao){
        this.loginLogDao = loginLogDao;
    }

    public User findUserByUserName(String userName) {
        return userDao.findUserByUserName(userName);
    }

    public boolean hasMatchUser(String userName,String password){
        System.out.print(userName);
        int matchCount = userDao.getMatchCount(userName,password);
        return matchCount>0;
    }

    @Transactional
    public void loginSuccess(User user){
       user.setCredits(5+user.getCredits());
       LoginLog loginLog = new LoginLog();
       loginLog.setUserId(user.getUserId());
       loginLog.setIp(user.getLastIp());
       loginLog.setLoginDate(user.getLastVisit());
       userDao.updateLoginInfo(user);
       loginLogDao.insertLoginLog(loginLog);
    }


}
