package net.mw.school.controller;

import net.mw.school.dao.ClassRoomDao;
import net.mw.school.dao.SeatDao;
import net.mw.school.dao.SeatOrderDao;
import net.mw.school.pojo.po.ClassRoomPO;
import net.mw.school.pojo.po.SeatPO;
import net.mw.school.pojo.vo.ClassRoomVO;
import net.mw.school.pojo.vo.SeatOrderVO;
import net.mw.school.pojo.vo.SeatVO;
import net.mw.school.service.ClassRoomService;
import net.mw.school.service.SeatService;
import net.mw.system.annotation.CurrentUser;
import net.mw.system.controller.BaseController;
import net.mw.system.pojo.po.UserPO;
import net.mw.system.result.ResultMessage;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("classRoom")
public class ClassRoomController extends BaseController<ClassRoomService, ClassRoomPO, ClassRoomVO> {
    /**
     * log4j实例对象.
     */
    private static Logger logger = LogManager.getLogger(ClassRoomController.class);

    @PostMapping(value = "/save")
    public ResultMessage save(@RequestBody ClassRoomVO vo, @CurrentUser UserPO currentUser){
        return this.save(vo);
    }
}
