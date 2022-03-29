package net.mw.school.service.impl;

import lombok.RequiredArgsConstructor;
import net.mw.school.dao.SeatDao;
import net.mw.school.dao.SeatOrderDao;
import net.mw.school.pojo.po.SeatOrderPO;
import net.mw.school.pojo.po.SeatPO;
import net.mw.school.pojo.vo.SeatOrderVO;
import net.mw.school.pojo.vo.SeatVO;
import net.mw.school.service.SeatOrderService;
import net.mw.school.service.SeatService;
import net.mw.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class SeatOrderServiceImpl extends BaseServiceImpl<SeatOrderDao, SeatOrderPO, SeatOrderVO> implements SeatOrderService {

}
