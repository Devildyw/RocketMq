package rocketmq.message;

import lombok.Data;

import java.io.Serializable;


/**
 * @author Devil
 * @create 2022-02-26 17:58
 */
@Data
public class SendMessage implements Serializable {
    private String from;

    private String recipient;

    private String subject;

    private String username;

    private String email;
}
