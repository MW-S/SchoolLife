package net.mw.school.pojo.vo;

import lombok.Data;
import net.mw.system.pojo.base.BaseVO;

/**
 * @Description 图书馆座位预约实体
 * @author W_Messi
 * @CreateTime 2022/3/27 22:23
 *
 */
@Data
public class SeatOrderVO extends BaseVO {

    /**
     * 主键
     */
    private String id;

    /**
     * 使用人ID
     */
    private String userId;

    /**
     * 座位ID
     */
    private String seatId;

    /**
     * 使用时长
     */
    private String useTime;

   
}
