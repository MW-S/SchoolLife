package net.mw.school.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.mw.system.pojo.base.BasePO;

/**
 * @Description 跑腿配送订单实体
 * @author W_Messi
 * @CreateTime 2022/3/27 22:23
 *
 */
@Data
@TableName("delivery_order")
public class DeliveryOrderPO extends BasePO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 商品名
     */
    private String name;

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
     * 所在校区
     */
    private Integer school;

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
    private Integer state;

    /**
     * 创建人ID
     */
    private Long userId;

    /**
     * 订单骑手ID
     */
    private Long serverId;

}
