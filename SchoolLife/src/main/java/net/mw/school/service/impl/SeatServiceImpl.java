package net.mw.school.service.impl;

import lombok.RequiredArgsConstructor;
import net.mw.school.dao.NoteDao;
import net.mw.school.dao.SeatDao;
import net.mw.school.pojo.po.NotePO;
import net.mw.school.pojo.po.SeatPO;
import net.mw.school.pojo.vo.NoteVO;
import net.mw.school.pojo.vo.SeatVO;
import net.mw.school.service.NoteService;
import net.mw.school.service.SeatService;
import net.mw.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class SeatServiceImpl extends BaseServiceImpl<SeatDao, SeatPO, SeatVO> implements SeatService {

}
