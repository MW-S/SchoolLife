package net.mw.school.controller;

import net.mw.school.dao.SeatDao;
import net.mw.school.dao.SeatOrderDao;
import net.mw.school.pojo.po.NotePO;
import net.mw.school.pojo.po.ParkingPO;
import net.mw.school.pojo.po.SeatPO;
import net.mw.school.pojo.vo.NoteVO;
import net.mw.school.pojo.vo.ParkingOrderVO;
import net.mw.school.pojo.vo.SeatOrderVO;
import net.mw.school.pojo.vo.SeatVO;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    public ResultMessage getOrderList(@RequestParam("page") int page, @RequestParam("size") int size
            , @CurrentUser UserPO currentUser){
        return orderController.getList(page, size, currentUser);
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
