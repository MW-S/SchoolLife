package net.mw.school.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.mw.school.dao.SeatDao;
import net.mw.school.dao.SeatOrderDao;
import net.mw.school.pojo.po.FoodPO;
import net.mw.school.pojo.po.NotePO;
import net.mw.school.pojo.po.ParkingPO;
import net.mw.school.pojo.po.SeatPO;
import net.mw.school.pojo.view.FoodViewVO;
import net.mw.school.pojo.view.SeatOrderViewVO;
import net.mw.school.pojo.vo.*;
import net.mw.school.service.NoteService;
import net.mw.school.service.SeatService;
import net.mw.system.annotation.CurrentUser;
import net.mw.system.controller.BaseController;
import net.mw.system.pojo.po.UserPO;
import net.mw.system.result.ResultMessage;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("seat")
public class SeatController extends BaseController<SeatService, SeatPO, SeatVO> {
    /**
     * log4j实例对象.
     */
    private static Logger logger = LogManager.getLogger(SeatController.class);

    @Autowired
    private SeatOrderController orderController;

    @Autowired
    private SeatDao dao;

    @Autowired
    private SeatOrderDao orderDao;

    @PostMapping(value = "/save")
    public ResultMessage save(@RequestBody SeatVO vo, @CurrentUser UserPO currentUser){
        return this.save(vo);
    }

    @GetMapping(value = "/getOrderList")
    public ResultMessage getOrderList(@RequestParam("page") Integer page, @RequestParam("size") Integer size
            , @CurrentUser UserPO currentUser){
        return orderController.getList(page, size, currentUser);
    }

    @PostMapping(value = "/getOrderListByVo")
    public ResultMessage getOrderListByVo(@RequestParam(value = "page", required = false) Integer page
            , @RequestParam(value = "size", required = false) Integer size
            , @RequestParam(value = "aimVo", required = false) String aimVoJson
            , @CurrentUser UserPO user) throws InvocationTargetException, IllegalAccessException {
        logger.trace("进入 getOrderListByVo 方法");
        ResultMessage rs =  orderController.getListByVo(page, size, aimVoJson, user);
        Map<String, Object> data = rs.getData();
        Map<String, SeatVO> seatMap = new HashMap<>();
        List<SeatOrderViewVO> vos = new ArrayList<>();
        ((ArrayList<SeatOrderVO>) data.get("list")).forEach(item->{
            SeatVO seatVo = null;
            if(seatMap.containsKey(item.getSeatId())){
                seatVo = seatMap.get(item.getSeatId());
            }else{
                seatVo = new SeatVO();
                seatVo.poToVo(dao.selectById(item.getSeatId()));
                seatMap.put(item.getSeatId(), seatVo);
            }
            SeatOrderViewVO vo = seatVo.voToPo(SeatOrderViewVO.class);
            vo.setId(item.getId());
            vo.setSeatId(item.getSeatId());
            vo.setUseTime(item.getUseTime());
            vo.setOrderState(item.getState());
            vo.setGmtCreate(item.getGmtCreate());
            vos.add(vo);
        });
        data.put("list", vos);
        rs.setData(data);
        logger.trace("退出 getOrderListByVo 方法");
        return rs;
    }

    @GetMapping(value = "/getOrderById")
    public ResultMessage getOrderList(@RequestParam("id") String id, @CurrentUser UserPO currentUser){
        return orderController.getById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping(value = "/saveOrder")
    public ResultMessage saveOrder(@RequestBody SeatOrderVO vo, @CurrentUser UserPO currentUser){
        ResultMessage rs =  orderController.save(vo);
        if(rs.getCode().equals(1L)){
            if(!ObjectUtils.allNotNull(vo.getId())){
                SeatPO seat = new SeatPO();
                seat.setId(Long.valueOf(vo.getSeatId()));
                seat.setState(true);
                dao.updateById(seat);
            }
        }
        return rs;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping(value = "/delOrderByIds")
    public ResultMessage getOrderList(@RequestParam("ids") String[] ids){
        ResultMessage rs = orderController.delByIds(ids);
        if(rs.getCode().equals(1L)){
            if(!ObjectUtils.allNotNull(ids)){
                for(String id: ids){
                    SeatPO seat = new SeatPO();
                    seat.setId(orderDao.selectById(id).getSeatId());
                    seat.setState(false);
                    dao.updateById(seat);
                }
            }
        }
        return rs;
    }
}
