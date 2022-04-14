/**
 * 
 */
package net.mw.system.pojo.po;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.mw.system.pojo.base.BasePO;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @author W_Messi
 * @CrateTime 2020/3/2 12:22
 */
@Data
@TableName("role")
public class RolePO extends BasePO {
	/**
	 * 主键
	 * 
	 */
	private Long id;

	/**
	 * 角色名
	 * 
	 */
	private String name;

}
