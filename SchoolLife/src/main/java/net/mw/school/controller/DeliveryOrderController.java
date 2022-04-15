package net.mw.school.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.mw.school.pojo.po.DeliveryOrderPO;
import net.mw.school.pojo.po.NotePO;
import net.mw.school.pojo.vo.DeliveryOrderVO;
import net.mw.school.pojo.vo.NoteVO;
import net.mw.school.service.DeliveryOrderService;
import net.mw.school.service.NoteService;
import net.mw.system.annotation.CurrentUser;
import net.mw.system.controller.BaseController;
import net.mw.system.pojo.po.UserPO;
import net.mw.system.result.ResultMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("deliveryOrder")
public class DeliveryOrderController extends BaseController<DeliveryOrderService, DeliveryOrderPO, DeliveryOrderVO> {
    /**
     * log4j实例对象.
     */
    private static Logger logger = LogManager.getLogger(DeliveryOrderController.class);

    @PostMapping(value = "/save")
    public ResultMessage save(@RequestBody DeliveryOrderVO vo, @CurrentUser UserPO currentUser){
        vo.setUserId(String.valueOf(currentUser.getId()));
        return this.save(vo);
    }
}
