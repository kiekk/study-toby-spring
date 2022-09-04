package com.tobyspring.studytobyspring.dao;

public class DaoFactory {

    public UserDao userDao() {
        return new UserDao(new DConnectionMaker());
    }

}