package net.mw.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import net.mw.school.controller.CanteenController;
import net.mw.school.pojo.po.CanteenPO;
import net.mw.school.pojo.vo.CanteenVO;
import net.mw.school.service.CanteenService;
import net.mw.system.annotation.CurrentUser;
import net.mw.system.pojo.base.BaseVO;
import net.mw.system.pojo.po.UserPO;
import net.mw.system.result.ResultMessage;
import net.mw.system.service.BaseService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class BaseController <S extends BaseService<E>, E, V> {

    /**
     * log4j实例对象.
     */
    public static Logger logger = LogManager.getLogger(BaseController.class);


    @Autowired
    protected S service;

    @Autowired
    protected Gson gson;

    private Method method;

    public Class<?> getGenericClass(Integer index) {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<?>) parameterizedType.getActualTypeArguments()[index];
    }

    public void setVoToPo(Class<?> cla){
        for(Method m:cla.getMethods()){
            if(m.getName().equals("voToPo")){
                method = m;
                break;
            }
        }
    }


    @GetMapping(value = "/getList")
    public ResultMessage getList( @RequestParam(value = "page", required = false) Integer page
            , @RequestParam(value = "size", required = false) Integer size
            , @CurrentUser UserPO user){
        logger.trace("进入getList方法");
        PageRequest pageVo = null;
        if(ObjectUtils.allNotNull(page, size)){
            pageVo = PageRequest.of(page, size);
        }
        ResultMessage rs=service.getList(pageVo, user);
        logger.trace("退出getList方法");
        return rs;
    }

    @PostMapping(value = "/getListByVo")
    public ResultMessage getListByVo( @RequestParam(value = "page", required = false) Integer page
            , @RequestParam(value = "size", required = false) Integer size
            , @RequestParam(value = "aimVo", required = false) String aimVoJson
            , @CurrentUser UserPO user) throws InvocationTargetException, IllegalAccessException {
        logger.trace("进入 getListByVo 方法");
        Class<?> cla = getGenericClass(2),
                e_class = getGenericClass(1);
        PageRequest pageVo = null;
        E po = null;
        if(ObjectUtils.allNotNull(page, size)){
            pageVo = PageRequest.of(page, size);
        }
        if(ObjectUtils.allNotNull(aimVoJson)){
            V vo = (V) gson.fromJson(aimVoJson, cla);
            this.setVoToPo(cla);
            po = (E) method.invoke(vo, e_class);
        }
        ResultMessage rs=service.getList(pageVo, new QueryWrapper<E>(po), user);
        logger.trace("退出 getListByVo 方法");
        return rs;
    }

    @GetMapping(value = "/getById")
    public ResultMessage getById(@RequestParam("id") String id){
        logger.trace("进入getById方法");
        ResultMessage rs=service.getByPoId(Long.valueOf(id));
        logger.trace("退出getById方法");
        return rs;
    }



    public ResultMessage save(@RequestBody V vo){
        logger.trace("进入 save 方法");
        E po = null;
        Class<?> cla = getGenericClass(2),
                e_class = getGenericClass(1);
        if(ObjectUtils.allNotNull(vo)){
            try {
                for(Method m:cla.getMethods()){
                    if(m.getName().equals("voToPo")){
                        method = m;
                        break;
                    }
                }
                po = (E) method.invoke(vo, e_class);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
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
