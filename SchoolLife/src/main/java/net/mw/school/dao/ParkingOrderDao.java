package net.mw.school.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import net.mw.school.pojo.po.ParkingOrderPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ParkingOrderDao extends BaseMapper<ParkingOrderPO> {
    
}
