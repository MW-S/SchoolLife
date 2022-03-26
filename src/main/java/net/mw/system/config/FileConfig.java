package net.mw.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * SpringMVC文件配置类. 用于文件的上传下载.
 *
 * @author M&W
 * @since 6.0.0
 */
@Configuration
public class FileConfig {

    /**
     * 用于限制上传文件大小的常量,设为5MB.
     */
    //1MB
//	private static final Integer FILE_MAX_SIZE = 1048576;
    //5MB
    private static final Integer FILE_MAX_SIZE = 524288000;

    /**
     * 文件上传的默认编码.
     */
    private static final String FILE_DEFAULT_CODING = "UTF-8";


    /**
     * 文件上传解析器.
     *
     * @author M&W
     * @since 6.0.0
     */
    @Bean("multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        // 设置文件上传最大限制.
        commonsMultipartResolver.setMaxUploadSize(FILE_MAX_SIZE);
        // 设置上传文件的默认编码.
        commonsMultipartResolver.setDefaultEncoding(FILE_DEFAULT_CODING);
        return commonsMultipartResolver;
    }
}
