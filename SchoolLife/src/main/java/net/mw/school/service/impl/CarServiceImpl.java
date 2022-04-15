package net.mw.school.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import io.jsonwebtoken.lang.Collections;
import net.mw.school.dao.CarDao;
import net.mw.school.pojo.po.CarPO;
import net.mw.school.pojo.vo.CarVO;
import net.mw.school.service.CanteenService;
import net.mw.school.service.CarService;
import net.mw.system.pojo.po.UserPO;
import net.mw.system.result.ResultMessage;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TaskServiceImpl接口实现
 * @Author W_Messi
 * @CrateTime 2021-03-06 21:30:03
 */
@Service
public class CarServiceImpl extends ServiceImpl<CarDao, CarPO> implements CarService {

	/**
	 * log4j实例对象.
	 */
	private static Logger logger = LogManager.getLogger(CarServiceImpl.class);
	
	@Autowired
	private CarService dao;

	
	@Override
	public ResultMessage getList(PageRequest page, UserPO user) {
		logger.trace("进入getList方法");
    	ResultMessage rs = new ResultMessage();
		try {
			if(!ObjectUtils.allNotNull(user)){
				logger.debug("CurrentUser must not null!");
				throw new IllegalArgumentException("CurrentUser must not null!");
			}
			Map<String,Object> data = new HashMap<String,Object>();
			if(ObjectUtils.allNotNull(page) && ObjectUtils.allNotNull(page.getPageNumber(), page.getPageSize())){
				PageHelper.startPage(page.getPageNumber(), page.getPageSize());
			}
			List<CarPO> pos = dao.list();
			List<CarVO> vos = new ArrayList<>();
			pos.forEach((item)->{
				CarVO vo = new CarVO();
				vo.poToVo(item);
				vos.add(vo);
			});
			data.put("size", vos.size());
			data.put("data", vos);
			rs.setData(data);
			rs.setCode(1L);
			rs.setMsg("获取成功!");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			rs.setMsg("参数不正确");
			rs.setCode(2L);
		} catch (Exception e) {
			e.printStackTrace();
			rs.setMsg("获取失败");
			rs.setCode(0L);
		}
		logger.trace("退出getList方法");
		return rs;
	}

	@Override
	public ResultMessage getByPoId(Long id) {
		logger.trace("进入 getById 方法");
    	ResultMessage rs = new ResultMessage();
		try {
			if(!ObjectUtils.allNotNull(id)){
				logger.debug("id must not null!");
				throw new IllegalArgumentException("id must not null!");
			}
			Map<String,Object> data = new HashMap<String,Object>();
			CarPO resPo = dao.getById(id);
			CarVO resVo = new CarVO();
			if(ObjectUtils.allNotNull(resPo)){
				resVo.poToVo(resPo);
			}
			data.put("data",resVo);
			rs.setCode(1L);
			rs.setMsg("获取成功!");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			rs.setMsg("参数不正确");
			rs.setCode(2L);
		} catch (Exception e) {
			e.printStackTrace();
			rs.setMsg("获取失败");
			rs.setCode(0L);
		}
		logger.trace("退出 getById 方法");
		return rs;
	}

	@Override
	public ResultMessage savePo(CarPO po) {
		logger.trace("进入 save 方法");
    	ResultMessage rs = new ResultMessage();
		try {
			if(!ObjectUtils.allNotNull(po)){
				logger.debug("po must not null!");
				throw new IllegalArgumentException("po must not null!");
			}
			if(dao.saveOrUpdate(po)) {
				rs.setCode(1L);
				rs.setMsg("操作成功!");
			}else {
				rs.setCode(1L);
				rs.setMsg("添加失败!");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			rs.setMsg("参数不正确");
			rs.setCode(2L);
		} catch (Exception e) {
			e.printStackTrace();
			rs.setMsg("添加失败");
			rs.setCode(0L);
		}
		logger.trace("退出add方法");
		return rs;
	}

	@Override
	public ResultMessage delByIds(String[] ids) {
		logger.trace("进入 delByIds 方法");
    	ResultMessage rs = new ResultMessage();
		try {
			if(!ObjectUtils.allNotNull(ids)){
				logger.debug("ids must not null!");
				throw new IllegalArgumentException("ids must not null!");
			}
			if(dao.removeBatchByIds(Collections.arrayToList(ids))) {
				rs.setCode(1L);
				rs.setMsg("删除成功!");
			}else {
				rs.setCode(1L);
				rs.setMsg("删除失败!");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			rs.setMsg("参数不正确");
			rs.setCode(2L);
		} catch (Exception e) {
			e.printStackTrace();
			rs.setMsg("删除失败");
			rs.setCode(0L);
		}
		logger.trace("退出 delByIds 方法");
		return rs;
	}

}
