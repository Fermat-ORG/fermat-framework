package com.bitdubai.test_generics;

import org.junit.Test;

/**
 * The class <code>com.bitdubai.test_generics.TestGenericTests</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 10/12/2015.
 */
public class TestGenericTests {

    @Test
    public void testGeneric1() throws Exception {

        TestGenericString test1 = new TestGenericString("hola");

        String test = test1.getP1();

        TestGenericInteger test2 = new TestGenericInteger(3);

        Integer test3 = test2.getP1();


    }
}
