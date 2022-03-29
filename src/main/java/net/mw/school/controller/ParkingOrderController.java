package net.mw.school.controller;

import net.mw.school.pojo.po.ParkingOrderPO;
import net.mw.school.pojo.po.ParkingPO;
import net.mw.school.pojo.vo.ParkingOrderVO;
import net.mw.school.pojo.vo.ParkingVO;
import net.mw.school.service.ParkingOrderService;
import net.mw.school.service.ParkingService;
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
public class ParkingOrderController extends BaseController<ParkingOrderService, ParkingOrderPO, ParkingOrderVO> {
    /**
     * log4j实例对象.
     */
    private static Logger logger = LogManager.getLogger(ParkingOrderController.class);

    public ResultMessage save(@RequestBody ParkingOrderVO vo, @CurrentUser UserPO currentUser){
        return this.save(vo);
    }
}
