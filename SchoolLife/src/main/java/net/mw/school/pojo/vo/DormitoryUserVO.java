package net.mw.school.pojo.vo;

import lombok.Data;
import net.mw.system.pojo.base.BasePO;

/**
 * @Description 宿舍人员表实体
 * @author W_Messi
 * @CreateTime 2022/3/27 22:23
 *
 */
@Data
public class DormitoryUserVO extends BasePO{

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
