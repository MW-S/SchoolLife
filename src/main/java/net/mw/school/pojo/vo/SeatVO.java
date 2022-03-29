package net.mw.school.pojo.vo;

import lombok.Data;
import net.mw.system.pojo.base.BaseVO;

/**
 * @Description 图书馆座位实体
 * @author W_Messi
 * @CreateTime 2022/3/27 22:23
 *
 */
@Data
public class SeatVO extends BaseVO {

    /**
     * 主键
     */
    private String id;

    /**
     * 座位号
     */
    private String code;

    /**
     * 所在教室
     */
    private String location;

   
}
