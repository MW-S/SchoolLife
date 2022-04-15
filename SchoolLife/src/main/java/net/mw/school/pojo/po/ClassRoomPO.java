package net.mw.school.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.mw.system.pojo.base.BasePO;

/**
 * @Description 图书馆座位实体
 * @author W_Messi
 * @CreateTime 2022/3/27 22:23
 *
 */
@Data
@TableName("class_room")
public class ClassRoomPO extends BasePO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 教室号
     */
    private String code;

    /**
     * 所在楼
     */
    private String location;

    /**
     * 所在校区
     */
    private Integer school;

    /**
     * 教室状态 1-已占用  0-空闲
     */
    private Boolean state;

   
}
