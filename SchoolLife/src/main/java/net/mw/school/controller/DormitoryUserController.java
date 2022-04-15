package net.mw.school.controller;

import net.mw.school.pojo.po.DormitoryPO;
import net.mw.school.pojo.po.DormitoryUserPO;
import net.mw.school.pojo.vo.DormitoryUserVO;
import net.mw.school.pojo.vo.DormitoryVO;
import net.mw.school.service.DormitoryService;
import net.mw.school.service.DormitoryUserService;
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
@RequestMapping("dormitoryUser")
public class DormitoryUserController extends BaseController<DormitoryUserService, DormitoryUserPO, DormitoryUserVO> {
    /**
     * log4j实例对象.
     */
    private static Logger logger = LogManager.getLogger(DormitoryUserController.class);

    @PostMapping(value = "/save")
    public ResultMessage save(@RequestBody DormitoryUserVO vo, @CurrentUser UserPO currentUser){

        return this.save(vo);
    }
}
