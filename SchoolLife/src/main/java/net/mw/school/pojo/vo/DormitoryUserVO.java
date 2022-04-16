package net.mw.school.pojo.vo;

import lombok.Data;
import net.mw.system.pojo.base.BaseVO;

/**
 * @Description 宿舍人员表实体
 * @author W_Messi
 * @CreateTime 2022/3/27 22:23
 *
 */
@Data
public class DormitoryUserVO extends BaseVO {

    /**
     * 主键
     */
    private String id;


    /**
     * 用户ID
     */
    private String userId;


    /**
     * 宿舍ID
     */
    private String dormitoryId;

}
