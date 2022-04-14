package net.mw.school.pojo.vo;

import lombok.Data;
import net.mw.system.pojo.base.BaseVO;

/**
 * @Description 个人动态实体
 * @author W_Messi
 * @CreateTime 2022/3/27 22:23
 *
 */
@Data
public class NoteVO extends BaseVO {

    /**
     * 主键
     */
    private String id;

    /**
     * 内容
     */
    private String content;

    /**
     * 动态状态
     */
    private String state;


    /**
     * 创建人ID
     */
    private String userId;

}
