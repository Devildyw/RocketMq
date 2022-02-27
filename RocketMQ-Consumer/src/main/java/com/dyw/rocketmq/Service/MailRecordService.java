package com.dyw.rocketmq.Service;


import rocketmq.result.Result;
import rocketmq.vo.MailVo;

import java.util.List;

/**
 * @author Devil
 * @create 2022-02-26 16:30
 */
public interface MailRecordService {
    List<MailVo> selectMailVoList();

    Result userRegister(String name, String email);

    String selectUserNameByEMAIl(String eMail);
}
