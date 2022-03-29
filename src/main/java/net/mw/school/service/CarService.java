package net.mw.school.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.mw.school.pojo.po.CanteenPO;
import net.mw.school.pojo.po.CarPO;
import net.mw.system.pojo.po.UserPO;
import net.mw.system.result.ResultMessage;
import org.springframework.data.domain.PageRequest;

public interface CarService extends IService<CarPO> {


	public ResultMessage getList(PageRequest page, UserPO user);

	public ResultMessage getByPoId(Long id);

	public ResultMessage savePo(CarPO po);

	public ResultMessage delByIds(String[] ids);

}
