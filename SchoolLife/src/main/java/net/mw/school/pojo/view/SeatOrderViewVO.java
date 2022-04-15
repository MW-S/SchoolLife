package net.mw.school.pojo.view;

import lombok.Data;
import net.mw.school.pojo.vo.SeatVO;

@Data
public class SeatOrderViewVO extends SeatVO {
    private String useTime;
    private String orderState;
    private String seatId;
}
