package net.mw.school.service.impl;

import lombok.RequiredArgsConstructor;
import net.mw.school.dao.NoteDao;
import net.mw.school.dao.ParkingDao;
import net.mw.school.pojo.po.NotePO;
import net.mw.school.pojo.po.ParkingPO;
import net.mw.school.pojo.vo.NoteVO;
import net.mw.school.pojo.vo.ParkingVO;
import net.mw.school.service.NoteService;
import net.mw.school.service.ParkingService;
import net.mw.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class ParkingServiceImpl extends BaseServiceImpl<ParkingDao, ParkingPO, ParkingVO> implements ParkingService {

}
