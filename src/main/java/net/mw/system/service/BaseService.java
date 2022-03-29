package net.mw.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.mw.system.pojo.po.UserPO;
import net.mw.system.result.ResultMessage;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

public interface BaseService <T> extends IService<T> {


    public ResultMessage getList(PageRequest page, UserPO user);

    public ResultMessage getByPoId(Long id);

    public ResultMessage savePo(T po);

    public ResultMessage delByIds(String[] ids);
}
