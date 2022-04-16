package net.mw.school.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.mw.school.dao.DormitoryUserDao;
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
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    DormitoryUserDao dao;

    @PostMapping(value = "/save")
    public ResultMessage save(@RequestBody DormitoryUserVO vo, @CurrentUser UserPO currentUser){
        QueryWrapper<DormitoryUserPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", vo.getUserId());
        if(dao.selectCount(queryWrapper) > 0){
            vo.setId(dao.selectOne(queryWrapper).getId().toString());
        }
        return this.save(vo);
    }
}
