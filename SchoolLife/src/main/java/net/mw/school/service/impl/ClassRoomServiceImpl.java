package net.mw.school.service.impl;

import lombok.RequiredArgsConstructor;
import net.mw.school.dao.ClassRoomDao;
import net.mw.school.dao.SeatDao;
import net.mw.school.pojo.po.ClassRoomPO;
import net.mw.school.pojo.po.SeatPO;
import net.mw.school.pojo.vo.ClassRoomVO;
import net.mw.school.pojo.vo.SeatVO;
import net.mw.school.service.ClassRoomService;
import net.mw.school.service.SeatService;
import net.mw.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class ClassRoomServiceImpl extends BaseServiceImpl<ClassRoomDao, ClassRoomPO, ClassRoomVO> implements ClassRoomService {

}
