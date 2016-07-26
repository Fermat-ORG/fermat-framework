package com.bitdubai.test_generics;

/**
 * The class <code>com.bitdubai.test_generics.TestGeneric1</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 10/12/2015.
 */
public abstract class TestGeneric1<P1> {

    private P1 p1;

    public TestGeneric1(P1 p1) {
        this.p1 = p1;
    }

    public P1 getP1() {
        return p1;
    }

}
