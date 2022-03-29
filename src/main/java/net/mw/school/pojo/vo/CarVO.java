package net.mw.school.pojo.vo;

import lombok.Data;
import net.mw.system.pojo.base.BaseVO;

/**
 * @Description 车辆实体
 * @author W_Messi
 * @CreateTime 2022/3/27 22:23
 *
 */
@Data
public class CarVO extends BaseVO {

    /**
     * 主键
     */
    private String id;

    /**
     * 车牌号
     */
    private String number;

    /**
     * 用户ID
     */
    private String userId;
   
}
