package com.bitdubai.smartwallet.core.platform.innersystem.layer.top.module.wallet_runtime.version_1.transaction;

import com.bitdubai.smartwallet.core.platform.innersystem.layer.lowlevel.service.request.version_1.MoneyRequest;

/**
 * Created by ciencias on 25.12.14.
 */
public class OutSystemMoneyInFromMoneyRequest extends OutSystemMoneyIn implements TransactionFromMoneyRequest{
    private MoneyRequest mMoneyRequest;
}
