package net.mw.school.service.impl;

import lombok.RequiredArgsConstructor;
import net.mw.school.dao.DeliveryOrderDao;
import net.mw.school.dao.NoteDao;
import net.mw.school.pojo.po.DeliveryOrderPO;
import net.mw.school.pojo.po.NotePO;
import net.mw.school.pojo.vo.DeliveryOrderVO;
import net.mw.school.pojo.vo.NoteVO;
import net.mw.school.service.DeliveryOrderService;
import net.mw.school.service.NoteService;
import net.mw.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class DeliveryOrderServiceImpl extends BaseServiceImpl<DeliveryOrderDao, DeliveryOrderPO, DeliveryOrderVO> implements DeliveryOrderService {

}
