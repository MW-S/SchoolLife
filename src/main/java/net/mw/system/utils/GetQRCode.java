package net.mw.system.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import java.io.FileOutputStream;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

public class GetQRCode {

    private static void getURCodePicyure(String c ,String path){
        int width = 400; //定义图片宽度
        int height = 400; // 定义图片高度
        String type = "png"; //定义图片格式
        String content = c; //图片扫描二维码的内容

        //使用HahsMap进行二维码的配置
        HashMap hashMap = new HashMap();
        //定义字符集设计为utf-8
        hashMap.put(EncodeHintType.CHARACTER_SET,"UTF-8");
        // 设计容错等级
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 设置边距
        hashMap.put(EncodeHintType.MARGIN,6);

        try {
            //生成二维码对象，传递各种属性
            BitMatrix bitMatrix = new MultiFormatWriter()
                    .encode(content, BarcodeFormat.QR_CODE, width, height, hashMap);
            // 定义路径
            Path file = new File(path).toPath();
            try {
                MatrixToImageWriter.writeToPath(bitMatrix,type,file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }


    }

    /**
     * @param srcImgPath 源图片路径
     * @param tarImgPath 保存的图片路径
     * @param waterMarkContent 水印内容
     * @param markContentColor 水印颜色
     * @param font 水印字体
     */
    public static void addWaterMark(String srcImgPath, String tarImgPath, String waterMarkContent,Color markContentColor,Font font) {

        try {
            // 读取原图片信息
            File srcImgFile = new File(srcImgPath);//得到文件
            Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高
            // 加水印



            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setColor(markContentColor); //根据图片的背景设置水印颜色
            g.setFont(font);              //设置字体

            //设置水印的坐标
            int x = (srcImgWidth-(waterMarkContent.length()*35) ) /2;
            int y = (15*srcImgHeight)/16;
            g.drawString(waterMarkContent, x, y);  //画出水印
            g.dispose();



            // 输出图片
            FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
            ImageIO.write(bufImg, "jpg", outImgStream);
            System.out.println("添加水印完成");
            outImgStream.flush();
            outImgStream.close();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }



    public static void jiang(String srcImgPath) throws IOException {
        File file = new File(srcImgPath);
        BufferedImage img = ImageIO.read(new FileInputStream(file));

        BufferedImage newImg= img.getSubimage(50, 50, 300, 350);
        File f = new File(srcImgPath);
        ImageIO.write(newImg, "png", f);

    }

    public static String getCode(String url, String finalPath,String id,String text) throws IOException {
        getURCodePicyure(url+id,
                finalPath+"/code"+id+".png");
//        createStringMark(finalPath+"/code"+id+".png",
//                text,
//                finalPath+"/code"+id+".png");


        Font font = new Font("微软雅黑", Font.PLAIN, 35);
        Color color=new Color(0,0,0);
        addWaterMark(finalPath+"/code"+id+".png", finalPath+"/code"+id+".png", text,color,font);
        jiang(finalPath+"/code"+id+".png");
        return "code/code"+id+".png";
    }

//    public static void main(String[] args) throws IOException {
//        getCode("https://ship.gzcskj.cn/permanent/code/","src/main/resources/static/code","4","");
//    }




}
