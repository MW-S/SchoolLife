package net.mw.school.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.mw.system.pojo.base.BasePO;

/**
 * @Description 饭堂实体
 * @author W_Messi
 * @CreateTime 2022/3/27 22:23
 *
 */
//@Accessors(chain= true)
@Data
@TableName("canteen")
public class CanteenPO extends BasePO {

    /**
     * 主键
     */
    private Long id;

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
