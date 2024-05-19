package com.example.taskraken.db.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.taskraken.db.LocalDatabase;
import com.example.taskraken.db.dao.UserDao;
import com.example.taskraken.db.model.User;
import com.example.taskraken.network.schemas.users.UserRead;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepository {

    private static UserRepository userRepository;
    LocalDatabase localDatabase;

    public UserRepository(Context context) {
        super();
        localDatabase = Room.databaseBuilder(
                context,
                LocalDatabase.class,
                "localDB"
        ).allowMainThreadQueries().build();
    }

    public void insert(User user) {
        localDatabase.userDao().insertOne(user);
    }

    public void insert(User... users){
        localDatabase.userDao().insertAll(users);
    }

    public void insert(UserRead userResponse){
        User user = new User(userResponse);
        insert(user);
        pickUser(user);
    }

    public List<User> getAllUsers() {
        return localDatabase.userDao().getAllUsers();
    }

    public User getById(UUID id) {
        return localDatabase.userDao().getById(id).get(0);
    }

    public void update(User user) {
        localDatabase.userDao().updateUser(user);
    }

    public void update(UserRead userResponse){
        update(new User(userResponse));
    }

    public void update(User... users){
        localDatabase.userDao().updateUsers(users);
    }

    public User getPickedUser() {
        List<User> user = localDatabase.userDao().getPickedUser();
        return user.get(0);
    }

    public void pickUser(User user){
        for (User row : getAllUsers()){
            row.unpick();
            update(row);
        }
        user.pick();
        update(user);
    }

    public void pickUser(UUID id){
        User user = getById(id);
        if (user != null){
            pickUser(user);
        }
    }
}
