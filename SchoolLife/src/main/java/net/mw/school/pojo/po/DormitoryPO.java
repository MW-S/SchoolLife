package net.mw.school.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.mw.system.pojo.base.BasePO;

/**
 * @Description 宿舍实体
 * @author W_Messi
 * @CreateTime 2022/3/27 22:23
 *
 */
@Data
@TableName("dormitory")
public class DormitoryPO extends BasePO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 宿舍名
     */
    private String name;

    /**
     * 宿舍所属栋
     */
    private String location;


}
