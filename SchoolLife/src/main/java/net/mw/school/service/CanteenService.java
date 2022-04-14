package net.mw.school.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.mw.school.pojo.po.CanteenPO;
import net.mw.school.pojo.po.DeliveryOrderPO;
import net.mw.system.pojo.po.UserPO;
import net.mw.system.result.ResultMessage;
import org.springframework.data.domain.PageRequest;

public interface CanteenService extends IService<CanteenPO> {


	public ResultMessage getList(PageRequest page, UserPO user);

	public ResultMessage getByPoId(Long id);

	public ResultMessage savePo(CanteenPO po);

	public ResultMessage delByIds(String[] ids);

}
