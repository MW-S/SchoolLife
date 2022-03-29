package net.mw.school.service.impl;

import lombok.RequiredArgsConstructor;
import net.mw.school.dao.NoteDao;
import net.mw.school.dao.VindicateDao;
import net.mw.school.pojo.po.NotePO;
import net.mw.school.pojo.po.VindicatePO;
import net.mw.school.pojo.vo.NoteVO;
import net.mw.school.pojo.vo.VindicateVO;
import net.mw.school.service.NoteService;
import net.mw.school.service.VindicateService;
import net.mw.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class VindicateServiceImpl extends BaseServiceImpl<VindicateDao, VindicatePO, VindicateVO> implements VindicateService {

}
