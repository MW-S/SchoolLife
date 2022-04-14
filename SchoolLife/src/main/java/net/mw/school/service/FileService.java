/**
 * Copyright © 2019 广州市麒骏网络科技有限公司版权所有.
 * 请勿修改或删除版权声明及文件头部.
 */
package net.mw.school.service;

import net.mw.system.result.ResultMessage;
import org.springframework.web.multipart.MultipartFile;

/**
 *	 文件上传下载服务层接口.
 * 
 * @author M&W
 * @since 6.0.0
 */
public interface FileService {


	ResultMessage uploadOSS(MultipartFile file);

}
