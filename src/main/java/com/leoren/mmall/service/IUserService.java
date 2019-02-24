package com.leoren.mmall.service;

import com.leoren.mmall.common.ServerResponse;
import com.leoren.mmall.pojo.User;

/**
 * @ClassName IUService
 * @Auther Leoren
 * @Date 2019/2/15 23:02
 * @Desc :
 * @Version v1.0
 **/

public interface IUserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str, String type);

    ServerResponse selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username, String question, String answer);

    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);

    ServerResponse<User> updateInformation(User user);

    ServerResponse<User> getInformation(Integer userId);

    ServerResponse checkAdminRole(User user);

}
