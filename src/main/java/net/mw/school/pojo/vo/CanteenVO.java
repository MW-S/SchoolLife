package net.mw.school.pojo.vo;

import lombok.Data;
import net.mw.system.pojo.base.BaseVO;

/**
 * @Description 饭堂实体
 * @author W_Messi
 * @CreateTime 2022/3/27 22:23
 *
 */
@Data
public class CanteenVO extends BaseVO {

    /**
     * 主键
     */
    private String id;

    /**
     * 饭堂名
     */
    private String name;

    /**
     * 饭堂图片
     */
    private String pictures;

    /**
     * 饭堂地点
     */
    private String location;

    /**
     * 饭堂营业时间
     */
    private String openTime;
   
}
