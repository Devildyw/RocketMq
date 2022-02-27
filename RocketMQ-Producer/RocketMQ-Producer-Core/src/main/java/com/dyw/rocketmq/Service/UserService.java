package com.dyw.rocketmq.Service;


import rocketmq.result.Result;

/**
 * @author Devil
 * @create 2022-02-26 19:23
 */

public interface UserService {
    Result userRegister(String name, String email);
}
