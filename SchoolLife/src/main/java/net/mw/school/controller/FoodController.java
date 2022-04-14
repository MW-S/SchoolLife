package net.mw.school.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.mw.school.dao.CanteenDao;
import net.mw.school.pojo.po.FoodPO;
import net.mw.school.pojo.po.NotePO;
import net.mw.school.pojo.view.FoodViewVO;
import net.mw.school.pojo.view.GoodsViewVO;
import net.mw.school.pojo.vo.FoodVO;
import net.mw.school.pojo.vo.GoodsVO;
import net.mw.school.pojo.vo.NoteVO;
import net.mw.school.service.FoodService;
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

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("food")
public class FoodController extends BaseController<FoodService, FoodPO, FoodVO> {
    /**
     * log4j实例对象.
     */
    private static Logger logger = LogManager.getLogger(FoodController.class);

    @Autowired
    CanteenDao canteenDao;

    @PostMapping(value = "/save")
    public ResultMessage save(@RequestBody FoodVO vo, @CurrentUser UserPO currentUser){
        return this.save(vo);
    }

    @GetMapping("getFoodList")
    public ResultMessage getFoodList(@RequestParam("page") Integer page, @RequestParam("size") Integer size
            , @CurrentUser UserPO user){
        logger.trace("进入 getFoodList 方法");
        PageRequest pageVo = null;
        if(ObjectUtils.allNotNull(page, size)){
            pageVo = PageRequest.of(page, size);
        }
        ResultMessage rs= service.getList(pageVo
                , new QueryWrapper<FoodPO> ().eq("offer_date", new java.sql.Date(new java.util.Date().getTime()))
                , user);
        Map<String, Object> data = rs.getData();
        List<FoodViewVO> vos = new ArrayList<>();
        ((ArrayList<FoodVO>) data.get("list")).forEach(item->{
            FoodViewVO vo = item.voToPo(FoodViewVO.class);
            vo.setCanteen(canteenDao.getById(Long.valueOf(item.getCanteenId())).getName());
            vos.add(vo);
        });
        data.put("list", vos);
        rs.setData(data);
        logger.trace("退出 getFoodList 方法");
        return rs;
    }
}
