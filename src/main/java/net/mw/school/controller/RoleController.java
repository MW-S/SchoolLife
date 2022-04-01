package net.mw.school.controller;

import net.mw.school.dao.CanteenDao;
import net.mw.school.pojo.po.FoodPO;
import net.mw.school.pojo.vo.FoodVO;
import net.mw.school.service.FoodService;
import net.mw.school.service.RoleService;
import net.mw.system.annotation.CurrentUser;
import net.mw.system.controller.BaseController;
import net.mw.system.pojo.po.RolePO;
import net.mw.system.pojo.po.UserPO;
import net.mw.system.pojo.vo.RoleVO;
import net.mw.system.result.ResultMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("role")
public class RoleController extends BaseController<RoleService, RolePO, RoleVO> {
    /**
     * log4j实例对象.
     */
    private static Logger logger = LogManager.getLogger(RoleController.class);

    @Autowired
    RoleService service;

    @GetMapping(value = "/getAllRole")
    public ResultMessage getAllRole(){
        return service.getList();
    }

    @PostMapping(value = "/save")
    public ResultMessage save(@RequestBody RoleVO vo, @CurrentUser UserPO currentUser){
        return this.save(vo);
    }
}
