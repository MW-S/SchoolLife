package net.mw.school.service.impl;

import lombok.RequiredArgsConstructor;
import net.mw.school.dao.NoteDao;
import net.mw.school.dao.ParkingOrderDao;
import net.mw.school.pojo.po.NotePO;
import net.mw.school.pojo.po.ParkingOrderPO;
import net.mw.school.pojo.vo.NoteVO;
import net.mw.school.pojo.vo.ParkingOrderVO;
import net.mw.school.service.NoteService;
import net.mw.school.service.ParkingOrderService;
import net.mw.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class ParkingOrderServiceImpl extends BaseServiceImpl<ParkingOrderDao, ParkingOrderPO, ParkingOrderVO> implements ParkingOrderService {

}
