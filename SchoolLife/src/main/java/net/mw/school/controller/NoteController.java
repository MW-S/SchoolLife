package net.mw.school.controller;

import net.mw.school.pojo.NoteViewVO;
import net.mw.school.pojo.po.NotePO;
import net.mw.school.pojo.vo.NoteVO;
import net.mw.school.service.NoteService;
import net.mw.system.annotation.CurrentUser;
import net.mw.system.controller.BaseController;
import net.mw.system.dao.UserDao;
import net.mw.system.pojo.po.UserPO;
import net.mw.system.result.ResultMessage;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("note")
public class NoteController extends BaseController<NoteService , NotePO, NoteVO> {
    /**
     * log4j实例对象.
     */
    private static Logger logger = LogManager.getLogger(NoteController.class);

    @Autowired
    UserDao userDao ;

    @PostMapping(value = "/save")
    public ResultMessage save(@RequestBody NoteVO vo, @CurrentUser UserPO currentUser){
        vo.setUserId(String.valueOf(currentUser.getId()));
        return this.save(vo);
    }

    @GetMapping("getNoteList")
    public ResultMessage getNoteList(@RequestParam("page") int page, @RequestParam("size") int size
            , @CurrentUser UserPO user){
        logger.trace("进入getList方法");
        PageRequest pageVo = null;
        if(ObjectUtils.allNotNull(page, size)){
            pageVo = PageRequest.of(page, size);
        }
        ResultMessage rs= service.getList(pageVo, user);
        Map<String, Object> data = rs.getData();
        List<NoteViewVO> vos = new ArrayList<>();
        ((ArrayList<NoteVO>) data.get("list")).forEach(item->{
            NoteViewVO vo = item.voToPo(NoteViewVO.class);
            vo.setUser(userDao.getUserById(Long.valueOf(item.getUserId())).getName());
            vos.add(vo);
        });
        data.put("list", vos);
        rs.setData(data);
        logger.trace("退出getList方法");
        return rs;
    }
}
