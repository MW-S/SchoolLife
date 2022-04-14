package net.mw.system.pojo.base;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BasePO implements Serializable {

    /**
     *创建时间
     */
    @TableField("create_gtm")
    private Date gmtCreate;
    /**
     *修改时间
     */
    @TableField("update_gtm")
    private Date gmtUpdate;
    /**
     *是否删除
     */
    private Boolean isDeleted;
    /**
     *是否冻结
     */
    private Boolean isLocked;
}
