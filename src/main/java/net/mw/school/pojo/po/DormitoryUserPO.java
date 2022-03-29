package net.mw.school.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.mw.system.pojo.base.BasePO;

/**
 * @Description 宿舍人员表实体
 * @author W_Messi
 * @CreateTime 2022/3/27 22:23
 *
 */
@Data
@TableName("dormitory_user")
public class DormitoryUserPO extends BasePO {

    /**
     * 主键
     */
    private Long id;


    /**
     * 用户ID
     */
    private Long userId;


    /**
     * 宿舍ID
     */
    private Long dormitoryId;

}
