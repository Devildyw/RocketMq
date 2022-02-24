package com.dyw.rocketmq.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Devil
 * @create 2022-02-23 22:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private String username;
    private Integer age;
}
