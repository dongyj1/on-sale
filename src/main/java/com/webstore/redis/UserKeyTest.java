package com.webstore.redis;



public class UserKeyTest extends BasePrefix {

    private UserKeyTest(String prefix) {
        super(0, prefix);
    }

    public static UserKeyTest getById = new UserKeyTest("id");
    public static UserKeyTest getByName = new UserKeyTest("name");
}
