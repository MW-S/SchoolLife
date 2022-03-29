package net.mw.school.controller;

import net.mw.school.pojo.po.FoodPO;
import net.mw.school.pojo.po.NotePO;
import net.mw.school.pojo.vo.FoodVO;
import net.mw.school.pojo.vo.NoteVO;
import net.mw.school.service.FoodService;
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
@RequestMapping("food")
public class FoodController extends BaseController<FoodService, FoodPO, FoodVO> {
    /**
     * log4j实例对象.
     */
    private static Logger logger = LogManager.getLogger(FoodController.class);

    @PostMapping(value = "/save")
    public ResultMessage save(@RequestBody FoodVO vo, @CurrentUser UserPO currentUser){
        return this.save(vo);
    }
}
