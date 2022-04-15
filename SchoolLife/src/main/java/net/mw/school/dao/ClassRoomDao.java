package net.mw.school.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.mw.school.pojo.po.ClassRoomPO;
import net.mw.school.pojo.po.SeatPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ClassRoomDao extends BaseMapper<ClassRoomPO> {
    
}
