package net.mw.school.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import io.jsonwebtoken.lang.Collections;
import net.mw.school.dao.CanteenDao;
import net.mw.school.dao.CarDao;
import net.mw.school.pojo.po.CanteenPO;
import net.mw.school.pojo.po.CarPO;
import net.mw.school.pojo.vo.CanteenVO;
import net.mw.school.service.CanteenService;
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
public class CanteenServiceImpl extends ServiceImpl<CanteenDao, CanteenPO> implements CanteenService {

	/**
	 * log4j实例对象.
	 */
	private static Logger logger = LogManager.getLogger(CanteenServiceImpl.class);
	
	@Autowired
	private CanteenService dao;

	
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
			if(ObjectUtils.allNotNull(page)){
				PageHelper.startPage(page.getPageNumber(), page.getPageSize());
			}
			List<CanteenPO> pos = dao.list();
			List<CanteenVO> vos = new ArrayList<>();
			pos.forEach((item)->{
				CanteenVO vo = new CanteenVO();
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
				logger.debug("Id must not null!");
				throw new IllegalArgumentException("Id must not null!");
			}
			Map<String,Object> data = new HashMap<String,Object>();
			CanteenPO resPo = dao.getById(id);
			CanteenVO resVo = new CanteenVO();
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
	public ResultMessage savePo(CanteenPO po) {
		logger.trace("进入add方法");
    	ResultMessage rs = new ResultMessage();
		try {
			if(!ObjectUtils.allNotNull(po)){
				logger.debug("canteen must not null!");
				throw new IllegalArgumentException("canteen must not null!");
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
