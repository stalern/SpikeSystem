package com.savannah.service;

import com.savannah.controller.vo.MyPage;
import com.savannah.service.model.PromoDTO;

import java.util.List;

/**
 * @author stalern
 * @date 2019/12/23~22:50
 */
public interface PromoService {

    /**
     * 通过id拿到所有活动信息
     * 不检查活动在或者不在当前时间
     * @param id promoId
     * @return 活动信息
     */
    PromoDTO getPromoById(Integer id);

    /**
     * 列出所有活动
     * @return promoList
     */
    List<PromoDTO> listPromo();

    /**
     * 列出当前所有活动
     * @return promoList
     */
    List<PromoDTO> listPromoNow();
}
