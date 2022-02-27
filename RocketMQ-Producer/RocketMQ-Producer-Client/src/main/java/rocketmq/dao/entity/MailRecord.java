package rocketmq.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Devil
 * @create 2022-02-26 16:26
 */
@Data
@TableName(value = "mail_record",autoResultMap = true)
public class MailRecord {
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private String id;

    private String name;

    private String eMail;
}
