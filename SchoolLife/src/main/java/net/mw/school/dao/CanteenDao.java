package net.mw.school.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import net.mw.school.pojo.po.CanteenPO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CanteenDao extends BaseMapper<CanteenPO> {

    @Select("select * from canteen ")
    public List<CanteenPO>  getList();

    @Select("select * from canteen where id = #{id}")
    public CanteenPO getById(Long id);
}
