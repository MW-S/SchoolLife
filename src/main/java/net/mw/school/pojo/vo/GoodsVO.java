package net.mw.school.pojo.vo;

import lombok.Data;
import net.mw.system.pojo.base.BaseVO;

/**
 * @Description 闲置物品实体
 * @author W_Messi
 * @CreateTime 2022/3/27 22:23
 *
 */
@Data
public class GoodsVO extends BaseVO {

    /**
     * 主键
     */
    private String id;

    /**
     * 物品名
     */
    private String name;

    /**
     * 物品种类
     */
    private String type;

    /**
     * 物品信息
     */
    private String info;

    /**
     * 物品状态 1-已出售  0-未出售
     */
    private String state;

    /**
     * 物品审核状态 2-未过审 1-已过审  0-待审核
     */
    private String auditState;


    /**
     * 创建人ID
     */
    private String userId;

    /**
     * 购买人ID
     */
    private String buyerId;
}
