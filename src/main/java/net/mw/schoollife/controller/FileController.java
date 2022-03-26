package net.mw.schoollife.controller;


import net.mw.schoollife.service.FileService;
import net.mw.system.result.ResultMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传下载控制层.
 *
 * @author M&W
 * @since 6.0.0
 */
@RestController
public class FileController {
    /**
     * log4j实例对象.
     */
    private static Logger logger = LogManager.getLogger(FileController.class);

    /**
     * 用于对文件CRUD的service.
     */
    @Autowired
    private FileService fileService;

    /**
     * 删除文章图片.
     *
     * @author 洪铎权
     * @since 1.0.1
     */
    @RequestMapping(value = "uploadOss")
//    @ActionResource(name = "uploadOss", permissions = {})
    public ResultMessage upload (@RequestParam("file") MultipartFile multipartFile) {
    ResultMessage resultMessage = fileService.uploadOSS(multipartFile);
        return resultMessage;
    }
}
