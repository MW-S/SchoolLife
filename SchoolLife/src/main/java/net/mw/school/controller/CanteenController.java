package net.mw.school.controller;

import net.mw.school.pojo.po.CanteenPO;
import net.mw.school.pojo.vo.CanteenVO;
import net.mw.school.service.CanteenService;
import net.mw.system.annotation.CurrentUser;
import net.mw.system.pojo.po.UserPO;
import net.mw.system.result.ResultMessage;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;


/**
 * @Description TaskController接口实现
 * @Author W_Messi
 * @CrateTime 2021-03-06 23:51:25
 */
@RestController
@RequestMapping("canteen")
public class CanteenController {
	/**
	 * log4j实例对象.
	 */
	private static Logger logger = LogManager.getLogger(CanteenController.class);
	

    @Autowired
    private CanteenService service;
    
    
    
    @GetMapping(value = "/getList")
    public ResultMessage getList(@RequestParam("page") int page, @RequestParam("size") int size
			, @CurrentUser UserPO user){
		logger.trace("进入getList方法");
		PageRequest pageVo = new PageRequest(page, size);
		ResultMessage rs=service.getList(pageVo, user);
		logger.trace("退出getList方法");
		return rs;
    }
    
    @GetMapping(value = "/getById")
    public ResultMessage getById(@RequestParam("id") String id){
		logger.trace("进入getById方法");
		ResultMessage rs=service.getByPoId(Long.valueOf(id));
		logger.trace("退出getById方法");
		return rs;
    }

    
    @PostMapping(value = "/save")
    public ResultMessage save(@RequestBody CanteenVO vo){
		logger.trace("进入 save 方法");
		CanteenPO po = null;
		if(ObjectUtils.allNotNull(vo)){
			po =  vo.voToPo(CanteenPO.class);
		}
		ResultMessage rs=service.savePo(po);
		logger.trace("退出 save 方法");
		return rs;
    }

    
    @PostMapping(value = "/delByIds")
    public ResultMessage delByIds(@RequestParam("ids") String[] ids){
		logger.trace("进入 delByIds 方法");
		ResultMessage rs=service.delByIds(ids);
		logger.trace("退出 delByIds 方法");
		return rs;
    }
}
