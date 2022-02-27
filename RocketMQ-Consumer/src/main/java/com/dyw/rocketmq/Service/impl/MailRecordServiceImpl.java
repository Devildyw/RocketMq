package com.dyw.rocketmq.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dyw.rocketmq.Service.MailRecordService;
import com.dyw.rocketmq.mapper.MailRecordMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocketmq.dao.entity.MailRecord;
import rocketmq.result.Result;
import rocketmq.vo.MailVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Devil
 * @create 2022-02-26 16:36
 */
@Log4j2
@Service
public class MailRecordServiceImpl implements MailRecordService {
    @Autowired
    private MailRecordMapper mailRecordMapper;

    @Override
    public List<MailVo> selectMailVoList() {
        LambdaQueryWrapper<MailRecord> mailRecordLambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<MailRecord> mailRecords = mailRecordMapper.selectList(mailRecordLambdaQueryWrapper);
        return copyList(mailRecords);
    }

    @Override
    public Result userRegister(String name, String email) {
        MailRecord mailRecord = new MailRecord();
        mailRecord.setName(name);
        mailRecord.setEMail(email);
        mailRecordMapper.insert(mailRecord);
        log.info("注册成功");
        return Result.succeed(null);
    }

    @Override
    public String selectUserNameByEMAIl(String eMail) {
        LambdaQueryWrapper<MailRecord> mailRecordLambdaQueryWrapper = new LambdaQueryWrapper<>();
        mailRecordLambdaQueryWrapper.eq(MailRecord::getEMail, eMail).last("limit 1");
        MailRecord mailRecord = mailRecordMapper.selectOne(mailRecordLambdaQueryWrapper);
        return mailRecord.getName();
    }

    private List<MailVo> copyList(List<MailRecord> mailRecords) {
        ArrayList<MailVo> mailVos = new ArrayList<>();
        for (MailRecord mailRecord : mailRecords) {
            mailVos.add(copy(mailRecord));
        }

        return mailVos;
    }

    private MailVo copy(MailRecord mailRecord) {
        MailVo mailVo = new MailVo();
        BeanUtils.copyProperties(mailRecord, mailVo);
        return mailVo;
    }
}
