package net.mw.school.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.mw.system.pojo.base.BasePO;

/**
 * @Description 闲置物品实体
 * @author W_Messi
 * @CreateTime 2022/3/27 22:23
 *
 */
@Data
@TableName("goods")
public class GoodsPO extends BasePO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 物品名
     */
    private String name;

    /**
     * 物品种类
     */
    private String type;

    /**
     * 价格
     */
    private String price;

    /**
     * 图片
     */
    private String pictures;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 微信号
     */
    private String wechatId;

    /**
     * 物品信息
     */
    private String info;

    /**
     * 物品状态 1-已出售  0-未出售
     */
    private Boolean state;

    /**
     * 物品审核状态 2-未过审 1-已过审  0-待审核
     */
    private Integer auditState;


    /**
     * 创建人ID
     */
    private Long userId;

    /**
     * 购买人ID
     */
    private Long buyerId;
}
