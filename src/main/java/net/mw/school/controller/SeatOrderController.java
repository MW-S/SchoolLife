package net.mw.school.controller;

import net.mw.school.pojo.po.SeatOrderPO;
import net.mw.school.pojo.po.SeatPO;
import net.mw.school.pojo.vo.SeatOrderVO;
import net.mw.school.pojo.vo.SeatVO;
import net.mw.school.service.SeatOrderService;
import net.mw.school.service.SeatService;
import net.mw.system.annotation.CurrentUser;
import net.mw.system.controller.BaseController;
import net.mw.system.pojo.po.UserPO;
import net.mw.system.result.ResultMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
public class SeatOrderController extends BaseController<SeatOrderService, SeatOrderPO, SeatOrderVO> {
    /**
     * log4j实例对象.
     */
    private static Logger logger = LogManager.getLogger(SeatOrderController.class);

    public ResultMessage save(@RequestBody SeatOrderVO vo, @CurrentUser UserPO currentUser){
        return this.save(vo);
    }
}