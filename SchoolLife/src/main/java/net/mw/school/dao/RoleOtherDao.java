package net.mw.school.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.mw.school.pojo.po.CarPO;
import net.mw.system.pojo.po.RolePO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoleOtherDao extends BaseMapper<RolePO> {
    
}
