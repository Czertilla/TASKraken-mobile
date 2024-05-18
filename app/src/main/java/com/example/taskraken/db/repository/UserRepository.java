package com.example.taskraken.db.repository;

import com.example.taskraken.db.dao.UserDao;
import com.example.taskraken.db.model.User;

import java.util.List;
import java.util.UUID;

public class UserRepository implements UserDao {
    @Override
    public void insert(User user) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User getById(UUID id) {
        return null;
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public User getPickedUser() {
        return null;
    }
    public void pickUser(User user){
        for (User row : getAllUsers()){
            row.unpick();
            updateUser(row);
        }
        user.pick();
        updateUser(user);
    }
}
