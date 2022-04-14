/**
 * 
 */
package net.mw.system.pojo.vo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.mw.system.pojo.base.BasePO;
import net.mw.system.pojo.base.BaseVO;

/**
 * @Description
 * @author W_Messi
 * @CrateTime 2020/3/2 12:22
 */
@Data
public class RoleVO extends BaseVO {
	/**
	 * 主键
	 * 
	 */
	private String id;

	/**
	 * 角色名
	 * 
	 */
	private String name;

}
