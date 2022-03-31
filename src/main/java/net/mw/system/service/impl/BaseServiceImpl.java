package net.mw.system.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import io.jsonwebtoken.lang.Collections;
import net.mw.school.pojo.po.CanteenPO;
import net.mw.school.pojo.vo.CanteenVO;
import net.mw.school.service.CanteenService;
import net.mw.school.service.impl.CanteenServiceImpl;
import net.mw.system.pojo.po.UserPO;
import net.mw.system.result.ResultMessage;
import net.mw.system.service.BaseService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseServiceImpl <M extends BaseMapper<T>, T, V> extends ServiceImpl<M, T> implements BaseService<T> {

    /**
     * log4j实例对象.
     */
    private static Logger logger = LogManager.getLogger(BaseServiceImpl.class);

    @Autowired
    private BaseService<T> dao;

    public Class<?> getGenericClass(Integer index) {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<?>) parameterizedType.getActualTypeArguments()[index];
    }


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
            List<T> pos = dao.list();
            List<V> vos = new ArrayList<>();
            Class<?> cla =  getGenericClass(2);
            pos.forEach((item)->{
                V vo = null;
                Method method = null;
                try {
                    vo = (V)cla.getDeclaredConstructor().newInstance();
                   for(Method m: cla.getMethods()){
                       if(m.getName().equals("poToVo")){
                           method = m;
                           break;
                       }
                   }
                    method.invoke(vo,item);
                    vos.add(vo);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
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
            T resPo = (T) dao.getById(id);
            Class<?> cla =  getGenericClass(2);
            V resVo = (V)cla.getDeclaredConstructor().newInstance();
            if(ObjectUtils.allNotNull(resPo)){
                Method method = null;
                for(Method m: cla.getMethods()){
                    if(m.getName().equals("poToVo")){
                        method = m;
                        break;
                    }
                }
                method.invoke(resVo,resPo);
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
    public ResultMessage savePo(T po) {
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