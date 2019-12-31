package com.savannah;

import com.savannah.entity.PromoItemDO;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stalern
 * @date 2019/12/31~21:23
 */
public class ListTest {

    @Test
    public void testList(){
        List<PromoItemDO>  list = new ArrayList<>();
        PromoItemDO promoItemDO = new PromoItemDO();

        promoItemDO.setPromoId(1);
        list.add(promoItemDO);
        promoItemDO.setPromoId(2);
        list.add(promoItemDO);
        promoItemDO.setPromoId(3);
        list.add(promoItemDO);
        promoItemDO.setPromoId(4);
        list.add(promoItemDO);
        list.forEach(System.out::println);
    }
}
