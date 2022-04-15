package net.mw.school.service.impl;

import lombok.RequiredArgsConstructor;
import net.mw.school.dao.DormitoryDao;
import net.mw.school.dao.DormitoryUserDao;
import net.mw.school.pojo.po.DormitoryPO;
import net.mw.school.pojo.po.DormitoryUserPO;
import net.mw.school.pojo.vo.DormitoryUserVO;
import net.mw.school.pojo.vo.DormitoryVO;
import net.mw.school.service.DormitoryService;
import net.mw.school.service.DormitoryUserService;
import net.mw.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class DormitoryUserServiceImpl extends BaseServiceImpl<DormitoryUserDao, DormitoryUserPO, DormitoryUserVO> implements DormitoryUserService {

}
