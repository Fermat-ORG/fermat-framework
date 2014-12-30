package com.bitdubai.smartwallet.core.platform.innersystem.layer.top.module.walletruntime.wallet.transaction;

import com.bitdubai.smartwallet.core.platform.innersystem.layer.lowlevel.service.request.MoneyRequest;

/**
 * Created by ciencias on 25.12.14.
 */
public class OutSystemMoneyOutFromMoneyRequest extends OutSystemMoneyOut implements TransactionFromMoneyRequest {
    private MoneyRequest mMoneyRequest;
}
