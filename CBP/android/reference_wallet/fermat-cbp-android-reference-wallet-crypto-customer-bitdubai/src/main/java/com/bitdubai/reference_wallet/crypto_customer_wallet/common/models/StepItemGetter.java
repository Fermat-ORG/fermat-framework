package com.bitdubai.reference_wallet.crypto_customer_wallet.common.models;

import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.NegotiationStep;

/**
 * Created by nelson on 08/01/16.
 */
public interface StepItemGetter {
    NegotiationStep getItem(final int position);

    NegotiationStep getDataSetItem(int position);
}
