package com.savannah.service.impl;

import com.savannah.dao.PromoInfoMapper;
import com.savannah.dao.PromoItemMapper;
import com.savannah.entity.PromoInfoDO;
import com.savannah.entity.PromoItemDO;
import com.savannah.error.EmReturnError;
import com.savannah.error.ReturnException;
import com.savannah.service.PromoService;
import com.savannah.service.model.PromoDTO;
import com.savannah.util.collection.EqualCollection;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(rollbackFor = ReturnException.class)
    public PromoDTO createPromo(PromoDTO promoDTO) throws ReturnException {
        PromoInfoDO promoInfoDO = convertInfoDoFromDTO(promoDTO);
        promoInfoMapper.insertSelective(promoInfoDO);
        promoDTO.setId(promoInfoDO.getId());
        if (promoDTO.getPromoPrice() != null) {
            List<PromoItemDO> promoItemDOList = convertItemDoFromDTO(promoDTO);
            try {
                promoItemDOList.forEach(promoItemMapper::insertSelective);
            } catch (DataAccessException ex) {
                throw new ReturnException(EmReturnError.PROMO_EXIST_ERROR,"一个商品只能参加一个活动");
            }
        }
        return getPromoById(promoDTO.getId());
    }

    @Override
    @Transactional(rollbackFor = ReturnException.class)
    public PromoDTO updatePromo(PromoDTO promoDTO) throws ReturnException {
        PromoDTO oldPromoDTO = getPromoById(promoDTO.getId());
        if (oldPromoDTO == null) {
            throw new ReturnException(EmReturnError.PROMO_NOT_EXIT);
        }
        // 如果InfoDO不同
        if (!convertInfoDoFromDTO(promoDTO).equals(convertInfoDoFromDTO(oldPromoDTO))) {
            PromoInfoDO promoInfoDO = convertInfoDoFromDTO(promoDTO);
            promoInfoMapper.updateByPrimaryKeySelective(promoInfoDO);
        }
        // 如果增加了商品活动关系不同
        if (!EqualCollection.equalMap(promoDTO.getPromoPrice(), oldPromoDTO.getPromoPrice())) {
            List<PromoItemDO> promoItemDOList = convertItemDoFromDTO(promoDTO);
            promoItemMapper.deleteByPromoId(promoDTO.getId());
            try {
                promoItemDOList.forEach(promoItemMapper::insertSelective);
            } catch (DataAccessException ex) {
                throw new ReturnException(EmReturnError.PROMO_EXIST_ERROR,"一个商品只能参加一个活动");
            }
        }
        return getPromoById(promoDTO.getId());
    }

    @Override
    public void deletePromo(Integer promoId) throws ReturnException {
        if (promoInfoMapper.deleteByPrimaryKey(promoId) < 0) {
            throw new ReturnException(EmReturnError.PROMO_EXIST_ERROR,"删除活动时出错");
        }
        promoItemMapper.deleteByPromoId(promoId);
    }

    private List<PromoItemDO> convertItemDoFromDTO(PromoDTO promoDTO) {
        List<PromoItemDO> promoItemDOList = new ArrayList<>();
        promoDTO.getPromoPrice().forEach((k,v) -> {
            PromoItemDO promoItemDO = new PromoItemDO();
            promoItemDO.setPromoId(promoDTO.getId());
            promoItemDO.setItemId(k);
            promoItemDO.setPromoItemPrice(v);
            promoItemDOList.add(promoItemDO);
        });
        return promoItemDOList;
    }

    private PromoInfoDO convertInfoDoFromDTO(PromoDTO promoDTO) {
        PromoInfoDO promoInfoDO = new PromoInfoDO();
        BeanUtils.copyProperties(promoDTO,promoInfoDO);
        promoInfoDO.setStartDate(promoDTO.getStartDate().toDate());
        promoInfoDO.setEndDate(promoDTO.getEndDate().toDate());
        return promoInfoDO;
    }

    private PromoDTO convertDtoFromDo(PromoInfoDO promoInfoDO, List<PromoItemDO> promoItemDO) {
        PromoDTO promoDTO = new PromoDTO();
        if (promoInfoDO == null) {
            return null;
        }
        BeanUtils.copyProperties(promoInfoDO, promoDTO);
        promoDTO.setStartDate(new DateTime(promoInfoDO.getStartDate()));
        promoDTO.setEndDate(new DateTime(promoInfoDO.getEndDate()));
        if (promoItemDO != null) {
            promoDTO.setPromoPrice(
                    promoItemDO.stream().collect(Collectors.toMap(PromoItemDO::getItemId, PromoItemDO::getPromoItemPrice)));
        }
        return promoDTO;
    }
}
