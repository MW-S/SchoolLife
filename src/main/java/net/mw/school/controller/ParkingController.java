package net.mw.school.controller;

import net.mw.school.dao.ParkingDao;
import net.mw.school.dao.ParkingOrderDao;
import net.mw.school.pojo.po.ParkingPO;
import net.mw.school.pojo.vo.ParkingOrderVO;
import net.mw.school.pojo.vo.ParkingVO;
import net.mw.school.service.ParkingService;
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
@RequestMapping("parking")
public class ParkingController extends BaseController<ParkingService, ParkingPO, ParkingVO> {
    /**
     * log4j实例对象.
     */
    private static Logger logger = LogManager.getLogger(ParkingController.class);

    @Autowired
    private ParkingOrderController orderController;

    @Autowired
    private ParkingDao dao;

    @Autowired
    private ParkingOrderDao orderDao;

    @PostMapping(value = "/save")
    public ResultMessage save(@RequestBody ParkingVO vo, @CurrentUser UserPO currentUser){
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
    public ResultMessage saveParkingOrder(@RequestBody ParkingOrderVO vo, @CurrentUser UserPO currentUser){
        ResultMessage rs =  orderController.save(vo);
        if(rs.getCode().equals(1L)){
            if(!ObjectUtils.allNotNull(vo.getId())){
                ParkingPO parking = new ParkingPO();
                parking.setId(Long.valueOf(vo.getParkingId()));
                parking.setState(true);
                dao.updateById(parking);
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
                    ParkingPO parking = new ParkingPO();
                    parking.setId(orderDao.selectById(id).getParkingId());
                    parking.setState(false);
                    dao.updateById(parking);
                }
            }
        }
        return rs;
    }
}
