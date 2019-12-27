package com.savannah.service.impl;

import com.savannah.dao.PromoInfoMapper;
import com.savannah.dao.PromoItemMapper;
import com.savannah.entity.PromoInfoDO;
import com.savannah.entity.PromoItemDO;
import com.savannah.service.PromoService;
import com.savannah.service.model.PromoDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author stalern
 * @date 2019/12/24~08:27
 */
@Service
public class PromoServiceImpl implements PromoService {

    private final PromoInfoMapper promoInfoMapper;
    private final PromoItemMapper promoItemMapper;

    public PromoServiceImpl(PromoInfoMapper promoInfoMapper, PromoItemMapper promoItemMapper) {
        this.promoInfoMapper = promoInfoMapper;
        this.promoItemMapper = promoItemMapper;
    }

    @Override
    public PromoDTO getPromoById(Integer promoId) {
        PromoInfoDO promoInfoDO = promoInfoMapper.selectByPrimaryKey(promoId);
        if (promoInfoDO == null) {
            return null;
        }
        List<PromoItemDO> promoItemDO = promoItemMapper.listPromoItemByPromoId(promoId);
        return convertDtoFromDo(promoInfoDO, promoItemDO);

    }

    @Override
    public List<PromoDTO> listPromo() {
        List<PromoDTO> promoDTOList = new ArrayList<>();
        List<PromoInfoDO> promoInfoDOList = promoInfoMapper.listPromo();
        promoInfoDOList.forEach(e-> promoDTOList.add(
                convertDtoFromDo(e,promoItemMapper.listPromoItemByPromoId(e.getId()))));
        return promoDTOList;
    }

    @Override
    public List<PromoDTO> listPromoNow() {
        List<PromoDTO> promoDTOList = new ArrayList<>();
        List<PromoInfoDO> promoInfoDOList = promoInfoMapper.listPromo().stream().
                filter(PromoInfoDO::isNow).collect(Collectors.toList());
        promoInfoDOList.forEach(e-> promoDTOList.add(
                convertDtoFromDo(e,promoItemMapper.listPromoItemByPromoId(e.getId()))));
        return promoDTOList;
    }

    private PromoDTO convertDtoFromDo(PromoInfoDO promoInfoDO, List<PromoItemDO> promoItemDO) {
        PromoDTO promoDTO = new PromoDTO();
        if (promoInfoDO == null) {
            return null;
        }
        BeanUtils.copyProperties(promoInfoDO, promoDTO);
        if (promoItemDO != null) {
            promoDTO.setPromoPrice(
                    promoItemDO.stream().collect(Collectors.toMap(PromoItemDO::getId, PromoItemDO::getPromoItemPrice)));
        }
        return promoDTO;
    }
}
