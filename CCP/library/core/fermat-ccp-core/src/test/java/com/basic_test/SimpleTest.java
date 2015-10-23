package com.basic_test;

import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatPluginsEnum;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * The class <code>com.basic_test.SimpleTest</code>
 * is used for basic message conversion testing.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/10/2015.
 */
public class SimpleTest {

    private Map<FermatPluginsEnum, String> objectList;

    public enum BalanceType implements FermatPluginsEnum {
        ESTIMATED,
        AVAILABLE
    }

    public enum Romeo implements FermatPluginsEnum {
        SURIJON,
        ZAPARRANCHO
    }

    @Test
    public void TestingOk(){
        objectList = new HashMap<>();

        objectList.put(BalanceType.AVAILABLE, "balance available");
        objectList.put(Romeo.ZAPARRANCHO, "ZAPAPAPALATA");

        System.out.println(objectList.get(Romeo.ZAPARRANCHO));
        System.out.println(objectList.get(BalanceType.AVAILABLE));
        System.out.println(objectList.get(Romeo.SURIJON));
    }
}
