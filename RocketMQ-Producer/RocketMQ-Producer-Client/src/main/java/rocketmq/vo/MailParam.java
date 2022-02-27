package rocketmq.vo;

import lombok.Data;

/**
 * @author Devil
 * @create 2022-02-26 14:58
 */
@Data
public class MailParam {
    private String from;//发件人
    private String recipient;//右键收件人
    private String subject;//邮件主题
}
