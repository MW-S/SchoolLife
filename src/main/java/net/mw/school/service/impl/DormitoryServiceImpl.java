package net.mw.school.service.impl;

import lombok.RequiredArgsConstructor;
import net.mw.school.dao.DormitoryDao;
import net.mw.school.dao.NoteDao;
import net.mw.school.pojo.po.DormitoryPO;
import net.mw.school.pojo.po.NotePO;
import net.mw.school.pojo.vo.DormitoryVO;
import net.mw.school.pojo.vo.NoteVO;
import net.mw.school.service.DormitoryService;
import net.mw.school.service.NoteService;
import net.mw.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class DormitoryServiceImpl extends BaseServiceImpl<DormitoryDao, DormitoryPO, DormitoryVO> implements DormitoryService {

}
