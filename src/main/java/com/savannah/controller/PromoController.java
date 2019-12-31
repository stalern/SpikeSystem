package com.savannah.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.savannah.controller.vo.MyPage;
import com.savannah.controller.vo.PromoVO;
import com.savannah.error.EmReturnError;
import com.savannah.error.ReturnException;
import com.savannah.response.ReturnType;
import com.savannah.service.PromoService;
import com.savannah.service.model.PromoDTO;
import com.savannah.util.auth.Auth;
import com.savannah.util.auth.Group;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author stalern
 * @date 2019/12/18~00:43
 */
@RestController
@RequestMapping("/promo")
@CrossOrigin(allowCredentials="true", allowedHeaders = "*")
public class PromoController {

    private final PromoService promoService;

    public PromoController(PromoService promoService) {
        this.promoService = promoService;
    }

    /**
     * 得到单个活动
     * @param id 活动id
     * @return promoVO
     * @throws ReturnException 活动不存在异常
     */
    @GetMapping("/getPromo/{id}")
    public ReturnType getPromo(@PathVariable Integer id) throws ReturnException {
        PromoVO promoVO = convertFromDTO(promoService.getPromoById(id));
        return ReturnType.create(promoVO);
    }

    /**
     * 列出所有活动
     * @param myPage 活动页
     * @return promoList
     */
    @Auth(Group.SELLER)
    @GetMapping("/listPromo")
    public ReturnType listPromo(MyPage myPage) {
        List<PromoVO> promoVOList = new ArrayList<>();
        PageHelper.startPage(myPage.getPage(), myPage.getSize());
        promoService.listPromo().forEach(e->{
            try {
                promoVOList.add(convertFromDTO(e));
            } catch (ReturnException ex) {
                ex.printStackTrace();
            }
        });
        return ReturnType.create(new PageInfo<>(promoVOList));
    }

    /**
     * 列出当前活动
     * @param myPage 活动展示页数
     * @return promoList
     */
    @GetMapping("/listPromoNow")
    public ReturnType listPromoNow(MyPage myPage) {
        List<PromoVO> promoVOList = new ArrayList<>();
        PageHelper.startPage(myPage.getPage(), myPage.getSize());
        promoService.listPromoNow().forEach(e->{
            try {
                promoVOList.add(convertFromDTO(e));
            } catch (ReturnException ex) {
                ex.printStackTrace();
            }
        });
        return ReturnType.create(new PageInfo<>(promoVOList));
    }

    /**
     * 创建一个秒杀活动
     * @param promoDTO 可以加入参与活动的商品和价格
     * @return promoVO
     */
    @PostMapping("/postPromo")
    @Auth(Group.SELLER)
    public ReturnType postPromo(@RequestBody PromoDTO promoDTO) throws ReturnException {
        if (promoDTO.beforeNow()) {
            throw new ReturnException(EmReturnError.PROMO_TIME_ERROR,"不能创建比现在更早的活动");
        }
        PromoVO promoVO = convertFromDTO(promoService.createPromo(promoDTO));
        return ReturnType.create(promoVO);
    }

    /**
     * 更新一个活动
     * @param promoDTO 增加商品活动在这里
     * @return promoVO
     * @throws ReturnException 活动时间修改错误/活动不存在错误
     */
    @PutMapping("/updatePromo")
    @Auth(Group.SELLER)
    public ReturnType updatePromo(@RequestBody PromoDTO promoDTO) throws ReturnException {
        if (promoDTO.beforeNow()) {
            throw new ReturnException(EmReturnError.PROMO_TIME_ERROR,"不能修改为比现在更早的活动");
        }
        PromoVO promoVO = convertFromDTO(promoService.updatePromo(promoDTO));
        return ReturnType.create(promoVO);
    }

    /**
     * 删除一个活动
     * @param id promoId
     * @return ok
     */
    @DeleteMapping("/deletePromo/{id}")
    @Auth(Group.SELLER)
    public ReturnType deletePromo(@PathVariable Integer id) throws ReturnException {
        promoService.deletePromo(id);
        return ReturnType.create();
    }

    private PromoVO convertFromDTO(PromoDTO promoDTO) throws ReturnException {
        if (promoDTO == null) {
            throw new ReturnException(EmReturnError.PROMO_NOT_EXIT);
        }
        PromoVO promoVO = new PromoVO();
        BeanUtils.copyProperties(promoDTO, promoVO);
        promoVO.setStartDate(promoDTO.getStartDate());
        promoVO.setEndDate(promoDTO.getEndDate());
        promoVO.setItemIds(new ArrayList<>(promoDTO.getPromoPrice().keySet()));
        return promoVO;
    }

}
