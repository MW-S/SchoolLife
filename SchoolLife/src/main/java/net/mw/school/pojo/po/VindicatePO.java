package net.mw.school.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.mw.system.pojo.base.BasePO;

/**
 * @Description 表白条目实体
 * @author W_Messi
 * @CreateTime 2022/3/27 22:23
 *
 */
@Data
@TableName("vindicate")
public class VindicatePO extends BasePO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 内容
     */
    private String content;

    /**
     * 收信者
     */
    private String receiver;

    /**
     * 写信者
     */
    private String writer;

    /**
     * 内容状态
     */
    private Boolean state;

    /**
     * 创建人ID
     */
    private Long userId;

}
