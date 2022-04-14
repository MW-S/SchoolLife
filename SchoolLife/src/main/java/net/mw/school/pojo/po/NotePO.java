package net.mw.school.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.mw.system.pojo.base.BasePO;

/**
 * @Description 个人动态实体
 * @author W_Messi
 * @CreateTime 2022/3/27 22:23
 *
 */
@Data
@TableName("note")
public class NotePO extends BasePO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 内容
     */
    private String content;

    /**
     * 动态状态
     */
    private Boolean state;


    /**
     * 创建人ID
     */
    private Long userId;

}
