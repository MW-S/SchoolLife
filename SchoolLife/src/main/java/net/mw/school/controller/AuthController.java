package net.mw.school.controller;

import javax.servlet.http.HttpServletRequest;

import net.mw.system.annotation.CurrentUser;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.mw.system.dao.UserDao;
import net.mw.system.pojo.po.UserPO;
import net.mw.system.pojo.vo.UserVO;
import net.mw.system.result.ResultMessage;
import net.mw.system.utils.JwtTokenUtils;
import net.mw.school.service.UserService;

/**
 *
 */
@RestController
@RequestMapping("auth")
@Async
public class AuthController {
	
	/**
	 * log4j实例对象.
	 */
	private static Logger logger = LogManager.getLogger(AuthController.class);

	public static final String LOGIN_TOKEN_KEY = "Authorization";

    @Autowired
    private UserService service;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private UserDao userDao;

    @GetMapping(value = "/info")
    public ResultMessage info(HttpServletRequest request, @CurrentUser UserPO currentUser){
		logger.trace("进入info方法");
		ResultMessage rs;
		if(!ObjectUtils.allNotNull(currentUser)){
			rs = new ResultMessage();
			rs.setCode(403L);
			rs.setMsg("未登录！");
		}else{
			rs = service.getById(currentUser.getId());
		}
		logger.trace("退出info方法");
		return rs;
    }
    
    @PostMapping(value = "/login")
    public ResultMessage login(@RequestBody UserVO vo){
		logger.trace("进入login方法");
		UserPO po = vo.voToPo(UserPO.class);
		ResultMessage rs=service.login(po);
		logger.trace("退出login方法");
		return rs;
    }
    
    @PostMapping(value = "/register")
    public ResultMessage register(@RequestBody UserVO vo){
		logger.trace("进入register方法");
		UserPO po = vo.voToPo(UserPO.class);
		ResultMessage rs=service.register(po);
		logger.trace("退出register方法");
		return rs;
    }
    
    @GetMapping(value = "/logout")
    public ResultMessage logout(){
		logger.trace("进入login方法");
		ResultMessage rs=service.logout();
		logger.trace("退出login方法");
		return rs;
    }

	@GetMapping(value = "/isLogin")
	public ResultMessage isLogin(HttpServletRequest request){
		logger.trace("进入 isLogin 方法");
		ResultMessage rs= new ResultMessage();
		String authorization = request.getHeader(LOGIN_TOKEN_KEY);
		Boolean isContinue = true;
		String token = null ;
		isContinue = ObjectUtils.allNotNull(authorization);
		if(isContinue){
			token =  authorization.split("Bearer ")[1];
			isContinue = isContinue && ObjectUtils.allNotNull(token);
		}
		if(isContinue){
			isContinue = jwtTokenUtils.validateToken(token);
			isContinue =  isContinue && !jwtTokenUtils.isTokenExpired(token);
		}
		rs.setCode(isContinue?1L:403L);
		rs.setMsg(isContinue?"已登录":"未登录");
		logger.trace("退出 isLogin 方法");
		return rs;
	}

	@GetMapping(value = "/getCode")
	public ResultMessage getCode(){
		logger.trace("进入 getCode 方法");
		ResultMessage rs=service.logout();
		logger.trace("退出 getCode 方法");
		return rs;
	}
}
