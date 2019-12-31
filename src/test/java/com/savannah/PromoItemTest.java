package com.savannah;

import com.savannah.dao.PromoItemMapper;
import com.savannah.entity.PromoItemDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author stalern
 * @date 2019/12/31~22:45
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpikeApplication.class)
public class PromoItemTest {
    @Autowired
    private PromoItemMapper promoItemMapper;
    @Test
    public void test() {
        PromoItemDO promoItemDO = promoItemMapper.selectByItemId(2);
        System.out.println(promoItemDO);
    }
}
