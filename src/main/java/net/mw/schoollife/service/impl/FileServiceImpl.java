/**
 * Copyright © 2019 广州市麒骏网络科技有限公司版权所有.
 * 请勿修改或删除版权声明及文件头部.
 */
package net.mw.schoollife.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.mw.schoollife.service.FileService;
import net.mw.system.result.ResultMessage;
import net.mw.system.result.UnifiedResultCode;
import net.mw.system.utils.MinioHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 * 文件上传下载服务层接口实现类.
 * 
 * @author 张剑民
 * @since 6.0.0
 */
@Service
public class FileServiceImpl implements FileService {

	/**
	 * log4j实例对象.
	 */
	private static Logger logger = LogManager.getLogger(FileServiceImpl.class);
	

	@Autowired
	private MinioHelper minioHelper;

	/**
	 * 删除图片.
	 * 
	 * @param file 文件，不得为null.
	 * @return 结果集.
	 * 
	 * @author M&W
	 * @since 1.0.0
	 */
	@Override
	public ResultMessage uploadOSS(MultipartFile file) {
		logger.trace("进入deleteUploadFile方法");
		ResultMessage rs = new ResultMessage();
		Map<String, Object> data =  new HashMap<String, Object>();
		try {
			data.put("path", this.minioHelper.putObject(file));
			rs.setData(data);
			rs.setMsg("上传成功");
			rs.setCode(UnifiedResultCode.getCode("success"));
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			rs.setMsg("参数不正确");
			rs.setCode(UnifiedResultCode.getCode("illegal argument"));
		} catch (Exception e) {
			e.printStackTrace();
			rs.setMsg("删除失败");
			rs.setCode(UnifiedResultCode.getCode("fail"));
		}
		logger.trace("退出deleteUploadFile方法");
		return rs;
	}

	/**
	 * 数据验证是否为空
	 * 
	 * @author 孙宇辉
	 * @param object   验证对象
	 * @param parmName 验证的参数名
	 */
	private void customValidate(Object object, String parmName) {
		logger.trace("退出customValidate方法");
		if (object == null) {
			logger.error("The " + parmName + " is null");
			throw new IllegalArgumentException("The " + parmName + " is null");
		}
		logger.trace("退出customValidate方法");
	}

}
