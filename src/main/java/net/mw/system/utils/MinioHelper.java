package net.mw.system.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
//import org.bytedeco.javacv.FFmpegFrameGrabber;
//import org.bytedeco.javacv.Frame;
//import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidExpiresRangeException;
import io.minio.errors.InvalidPortException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.RegionConflictException;
import io.minio.errors.XmlParserException;

@Component
public class MinioHelper {

    @Value("${minio.bucket:image}")
    private String bucket;

    @Value("${minio.host:localhost}")
    private String host;

    @Value("${minio.port:9001}")
    private String port;

    @Value("${minio.url:localhost:9001}")
    private String url;

    @Value("${minio.access-key:minioadmin}")
    private String accessKey;

    @Value("${minio.secret-key:minioadmin}")
    private String secretKey;

    public String putObject(MultipartFile multipartFile) throws InvalidEndpointException, InvalidPortException,
            IOException, InvalidKeyException, ErrorResponseException, IllegalArgumentException,
            InsufficientDataException, InternalException, InvalidBucketNameException, InvalidResponseException,
            NoSuchAlgorithmException, XmlParserException, RegionConflictException, InvalidExpiresRangeException {
        MinioClient minioClient = new MinioClient(this.host, this.accessKey, this.secretKey, false);
        // bucket 不存在，创建
        if (!minioClient.bucketExists(this.bucket)) {
            minioClient.makeBucket(this.bucket);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {

            // 上传文件的名称
            String fileName = multipartFile.getOriginalFilename();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmss"); // add S if you need milliseconds

            fileName = df.format(new Date()) + fileName;

            // PutObjectOptions，上传配置(文件大小，内存中文件分片大小)
            PutObjectOptions putObjectOptions = new PutObjectOptions(multipartFile.getSize(), PutObjectOptions.MIN_MULTIPART_SIZE);
            // 文件的ContentType
            putObjectOptions.setContentType(multipartFile.getContentType());
            minioClient.putObject(this.bucket, fileName, inputStream, putObjectOptions);
            if(multipartFile.getContentType().contains("video/")) {
//            	putVideo(fileName, minioClient);
            }
            // 返回访问路径
            return this.url + UriUtils.encode(fileName, StandardCharsets.UTF_8);
        }
    }

    //网络图片转存
    public String putObject(String fileName, String imageurl) throws InvalidEndpointException, InvalidPortException,
	    IOException, InvalidKeyException, ErrorResponseException, IllegalArgumentException,
	    InsufficientDataException, InternalException, InvalidBucketNameException, InvalidResponseException,
	    NoSuchAlgorithmException, XmlParserException, RegionConflictException, InvalidExpiresRangeException {
				MinioClient minioClient = new MinioClient(this.host, this.accessKey, this.secretKey, false);
				// bucket 不存在，创建
				if (!minioClient.bucketExists(this.bucket)) {
				    minioClient.makeBucket(this.bucket);
				}
				  // 构造URL
	            URL url = new URL(imageurl);
	            // 打开连接
	            URLConnection con = url.openConnection();
	            // 输入流
	            InputStream inputStream = con.getInputStream();
	            byte[] bytes = IOUtils.toByteArray(inputStream);
	            inputStream = new ByteArrayInputStream(bytes); 
			    // PutObjectOptions，上传配置(文件大小，内存中文件分片大小)
			    PutObjectOptions putObjectOptions = new PutObjectOptions(bytes.length, PutObjectOptions.MIN_MULTIPART_SIZE);
			    // 文件的ContentType
			    putObjectOptions.setContentType("image/pjpeg");
			    minioClient.putObject(this.bucket, fileName, inputStream, putObjectOptions);
			    // 返回访问路径
			    return this.url + UriUtils.encode(fileName, StandardCharsets.UTF_8);
	   }    


  /*  public String putVideo(String fileName,MinioClient client) throws InvalidEndpointException, InvalidPortException,
    IOException, InvalidKeyException, ErrorResponseException, IllegalArgumentException,
    InsufficientDataException, InternalException, InvalidBucketNameException, InvalidResponseException,
    NoSuchAlgorithmException, XmlParserException, RegionConflictException, InvalidExpiresRangeException {
	    // 上传文件的名称
//	    String fileName = multipartFile.getOriginalFilename();
	    //从服务器获取图片
	    InputStream inputStream = client.getObject(this.bucket, fileName);

        FFmpegFrameGrabber grabber;
        InputStream img = null;
        try {

            grabber = new FFmpegFrameGrabber(inputStream,0);

            grabber.start();
            // 视频总帧数
            int videoLength = grabber.getLengthInFrames();

            Frame frame = null;
            int i = 0;

            while (i < videoLength) {

                // 过滤前5帧,因为前5帧可能是全黑的
                frame = grabber.grabFrame();
                if (i>5&&frame.image != null) {

                    break;
                }
                i++;
            }

            Java2DFrameConverter converter = new Java2DFrameConverter();
            // 绘制图片
            BufferedImage bi = converter.getBufferedImage(frame);
            img = bufferedImageToInputStream(bi);
            byte[] bytes = IOUtils.toByteArray(img);
            img = new ByteArrayInputStream(bytes); 
            PutObjectOptions putObjectOptions = new PutObjectOptions(bytes.length, PutObjectOptions.MIN_MULTIPART_SIZE);
            // 文件的ContentType
            putObjectOptions.setContentType("image/jpeg");
            grabber.stop();
            grabber.close();
            client.putObject(this.bucket, fileName + ".jpg", img, putObjectOptions);
        } catch (IOException e) {

            e.printStackTrace();
        }
        
        return  this.url + UriUtils.encode(fileName, StandardCharsets.UTF_8);
    }	*/
    
    /**
     * 将BufferedImage转换为InputStream
     *
     * @param image
     * @return
     */
    public  InputStream bufferedImageToInputStream(BufferedImage image) {
    
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
    
            ImageIO.write(image, "jpg", os);
            InputStream input = new ByteArrayInputStream(os.toByteArray());
            return input;
        } catch (IOException e) {
        	 e.printStackTrace();
        }
        return null;
    }
}