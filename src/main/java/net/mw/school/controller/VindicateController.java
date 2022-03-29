package net.mw.school.controller;

import net.mw.school.pojo.po.SeatPO;
import net.mw.school.pojo.po.VindicatePO;
import net.mw.school.pojo.vo.SeatVO;
import net.mw.school.pojo.vo.VindicateVO;
import net.mw.school.service.SeatService;
import net.mw.school.service.VindicateService;
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
@RequestMapping("vindicate")
public class VindicateController extends BaseController<VindicateService, VindicatePO, VindicateVO> {
    /**
     * log4j实例对象.
     */
    private static Logger logger = LogManager.getLogger(VindicateController.class);

    @PostMapping(value = "/save")
    public ResultMessage save(@RequestBody VindicateVO vo, @CurrentUser UserPO currentUser){
        return this.save(vo);
    }
}
