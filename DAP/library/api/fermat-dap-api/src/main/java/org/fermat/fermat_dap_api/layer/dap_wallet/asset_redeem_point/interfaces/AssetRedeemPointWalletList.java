package org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;

import java.io.Serializable;

/**
 * Created by franklin on 14/10/15.
 */
public interface AssetRedeemPointWalletList extends Serializable {

    DigitalAsset getDigitalAsset();

    void setDigitalAsset(DigitalAsset digitalAsset);

    long getAvailableBalance();

    void setAvailableBalance(long availableBalance);

    long getBookBalance();

    void setBookBalance(long bookBalance);

    long getQuantityBookBalance();

    void setQuantityBookBalance(long quantityBookBalance);

    long getQuantityAvailableBalance();

    void setQuantityAvailableBalance(long quantityAvailableBalance);
}
