package com.bitdubai.sub_app.wallet_factory.fragment.version_3.utils;

import com.squareup.otto.Bus;

/**
 * Created by Nicolas on 05/05/2015.
 */
public final class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {}
}