package com.savannah;

import org.junit.Test;

/**
 * @author stalern
 * @date 2020/01/02~07:40
 */
public class OrderTest {

    @Test
    public void postOrder() {

        OrderThread orderThread = new OrderThread();
        orderThread.start();

    }

}
