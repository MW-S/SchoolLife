package net.mw.school.service.impl;

import lombok.RequiredArgsConstructor;
import net.mw.school.dao.FoodDao;
import net.mw.school.dao.GoodsDao;
import net.mw.school.pojo.po.FoodPO;
import net.mw.school.pojo.po.GoodsPO;
import net.mw.school.pojo.vo.FoodVO;
import net.mw.school.pojo.vo.GoodsVO;
import net.mw.school.service.FoodService;
import net.mw.school.service.GoodsService;
import net.mw.system.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsServiceImpl extends BaseServiceImpl<GoodsDao, GoodsPO, GoodsVO> implements GoodsService {

}
