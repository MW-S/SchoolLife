package net.mw.school.service;

import net.mw.school.pojo.po.FoodPO;
import net.mw.system.pojo.po.RolePO;
import net.mw.system.pojo.po.UserPO;
import net.mw.system.result.ResultMessage;
import net.mw.system.service.BaseService;
import org.springframework.data.domain.PageRequest;

public interface RoleService extends BaseService<RolePO> {
    public ResultMessage getList();
}
