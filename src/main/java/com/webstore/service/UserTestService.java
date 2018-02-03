package com.webstore.service;


import com.webstore.dao.UserTestDao;
import com.webstore.domain.UserTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserTestService {

    @Autowired
    UserTestDao userTestDao;

    public UserTest getById(int id) {
        return userTestDao.getById(id);
    }

    @Transactional
    public boolean tx() {
        UserTest u1 = new UserTest();
        u1.setId(2);
        u1.setName("222");
        userTestDao.insert(u1);

        UserTest u2 = new UserTest();
        u2.setId(1);
        u2.setName("333");
        userTestDao.insert(u2);

        return true;
    }

//    public void set()
}
