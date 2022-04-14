package net.mw.school.controller;

import net.mw.school.pojo.po.GoodsPO;
import net.mw.school.pojo.po.NotePO;
import net.mw.school.pojo.view.GoodsViewVO;
import net.mw.school.pojo.view.NoteViewVO;
import net.mw.school.pojo.vo.GoodsVO;
import net.mw.school.pojo.vo.NoteVO;
import net.mw.school.service.GoodsService;
import net.mw.school.service.NoteService;
import net.mw.system.annotation.CurrentUser;
import net.mw.system.controller.BaseController;
import net.mw.system.dao.UserDao;
import net.mw.system.pojo.po.UserPO;
import net.mw.system.result.ResultMessage;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("goods")
public class GoodsController extends BaseController<GoodsService, GoodsPO, GoodsVO> {
    /**
     * log4j实例对象.
     */
    private static Logger logger = LogManager.getLogger(GoodsController.class);

    @Autowired
    UserDao userDao ;

    @PostMapping(value = "/save")
    public ResultMessage save(@RequestBody GoodsVO vo, @CurrentUser UserPO currentUser){
        vo.setUserId(String.valueOf(currentUser.getId()));
        return this.save(vo);
    }

    @GetMapping("getGoodsList")
    public ResultMessage getGoodsList(@RequestParam("page") int page, @RequestParam("size") int size
            , @CurrentUser UserPO user){
        logger.trace("进入 getGoodsList 方法");
        PageRequest pageVo = null;
        if(ObjectUtils.allNotNull(page, size)){
            pageVo = PageRequest.of(page, size);
        }
        ResultMessage rs= service.getList(pageVo, user);
        Map<String, Object> data = rs.getData();
        List<GoodsViewVO> vos = new ArrayList<>();
        ((ArrayList<GoodsVO>) data.get("list")).forEach(item->{
            GoodsViewVO vo = item.voToPo(GoodsViewVO.class);
            vo.setUser(userDao.getUserById(Long.valueOf(item.getUserId())).getName());
            vos.add(vo);
        });
        data.put("list", vos);
        rs.setData(data);
        logger.trace("退出 getGoodsList 方法");
        return rs;
    }

    @GetMapping("getGoodsById")
    public ResultMessage getGoodsById(@RequestParam("id") String id, @CurrentUser UserPO user){
        logger.trace("进入 getGoodsById 方法");
        ResultMessage rs= service.getByPoId(Long.valueOf(id));
        Map<String, Object> data = rs.getData();
        GoodsVO vo = (GoodsVO) rs.getData().get("data");
        GoodsViewVO viewVo = vo.voToPo(GoodsViewVO.class);
        viewVo.setUser(userDao.getUserById(Long.valueOf(vo.getUserId())).getName());
        data.put("data", viewVo);
        rs.setData(data);
        logger.trace("退出 getGoodsList 方法");
        return rs;
    }
}
