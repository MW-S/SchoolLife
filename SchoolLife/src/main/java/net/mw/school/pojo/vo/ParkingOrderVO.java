package net.mw.school.pojo.vo;

import lombok.Data;
import net.mw.system.pojo.base.BaseVO;

/**
 * @Description 停车订单实体
 * @author W_Messi
 * @CreateTime 2022/3/27 22:23
 *
 */
@Data
public class ParkingOrderVO extends BaseVO {

    /**
     * 主键
     */
    private String id;


    /**
     * 车位ID
     */
    private String parkingId;


    /**
     * 车辆ID
     */
    private String carId;

}
