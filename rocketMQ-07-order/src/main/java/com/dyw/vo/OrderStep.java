package com.dyw.vo;

import jdk.jfr.DataAmount;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Devil
 * @create 2022-02-24 20:42
 */
@Data
public class OrderStep implements Serializable {
    private long orderId;

    private String desc;
}
