package com.webstore.service;

import com.webstore.dao.GoodsDao;
import com.webstore.domain.DiscountGoods;
import com.webstore.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {
    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoById(long id) {
        return goodsDao.getGoodsVoByGoodsId(id);
    }

    public void reduceStockByOne(GoodsVo goodsVo) {
        DiscountGoods g = new DiscountGoods();
        g.setGoodsId(goodsVo.getId());
        goodsDao.reduceStock(g);
    }
}
