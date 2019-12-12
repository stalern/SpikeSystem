package com.savannah;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试shiro
 */
public class ShiroTest
{

    @Test
    public void testShiro() {
        Logger log = LoggerFactory.getLogger(ShiroTest.class);
        log.info("shiro");
        System.exit(0);
    }

}
