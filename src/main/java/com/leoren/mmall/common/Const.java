package com.leoren.mmall.common;

/**
 * @ClassName Const
 * @Auther Leoren
 * @Date 2019/2/16 11:07
 * @Desc :需要用到的常量类
 * @Version v1.0
 **/

public class Const {

    public static final String CURRENT_USER = "currentUser";

    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    public interface Role{
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_ADMIN = 1; //管理员
    }


}
