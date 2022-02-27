package rocketmq.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Devil
 * @create 2022-02-26 16:33
 */
@Data
public class MailVo implements Serializable {
    private String name;

    private String eMail;
}
