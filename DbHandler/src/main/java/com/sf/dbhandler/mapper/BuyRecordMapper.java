package com.sf.dbhandler.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sf.dbhandler.bean.BuyRecord;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyRecordMapper extends BaseMapper<BuyRecord>{
    public int insertBuyRecord(BuyRecord record);
}
