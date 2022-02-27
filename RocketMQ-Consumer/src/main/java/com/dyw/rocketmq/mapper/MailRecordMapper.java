package com.dyw.rocketmq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import rocketmq.dao.entity.MailRecord;

/**
 * @author Devil
 * @create 2022-02-27 14:50
 */
@Mapper
public interface MailRecordMapper extends BaseMapper<MailRecord> {
}
