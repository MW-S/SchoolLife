package net.mw.school.pojo.vo;

import lombok.Data;
import net.mw.system.pojo.base.BaseVO;

/**
 * @Description 跑腿配送订单实体
 * @author W_Messi
 * @CreateTime 2022/3/27 22:23
 *
 */
@Data
public class DeliveryOrderVO extends BaseVO {

    /**
     * 主键
     */
    private String id;

    /**
     * 商品名
     */
    private String name;

    /**
     * 所在校区
     */
    private String school;

    /**
     * 价格
     */
    private String price;

    /**
     * 佣金
     */
    private String commission;

    /**
     * 备注
     */
    private String tag;

    /**
     * 特定购买地点
     */
    private String address;

    /**
     * 用户电话
     */
    private String userPhone;

    /**
     * 骑手电话
     */
    private String serverPhone;

    /**
     * 订单状态
     */
    private String state;

    /**
     * 创建人ID
     */
    private String userId;

    /**
     * 订单骑手ID
     */
    private String serverId;

}
