package net.mw.school.pojo.vo;

import lombok.Data;
import net.mw.system.pojo.base.BaseVO;

/**
 * @Description 宿舍实体
 * @author W_Messi
 * @CreateTime 2022/3/27 22:23
 *
 */
@Data
public class DormitoryVO extends BaseVO {

    /**
     * 主键
     */
    private String id;

    /**
     * 宿舍名
     */
    private String name;

    /**
     * 宿舍所属栋
     */
    private String location;


}
