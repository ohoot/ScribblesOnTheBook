package com.example.joo.scribblesonthebook.data.manager;

/**
 * Created by Joo on 2016-03-22.
 */
public class UserPropertyManager {
    private static UserPropertyManager instance;

    public static UserPropertyManager getInstance() {
        if (instance == null) {
            instance = new UserPropertyManager();
        }
        return instance;
    }

    private UserPropertyManager(){}

    public int userId;
}
