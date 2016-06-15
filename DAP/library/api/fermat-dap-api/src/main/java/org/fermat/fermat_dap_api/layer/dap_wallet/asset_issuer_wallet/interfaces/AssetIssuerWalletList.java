package org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces;

import java.io.Serializable;

/**
 * Created by franklin on 29/09/15.
 */
public interface AssetIssuerWalletList extends Serializable {

    org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset getDigitalAsset();

    void setDigitalAsset(org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset digitalAsset);

    long getBookBalance();

    void setBookBalance(long bookBalance);

    long getAvailableBalance();

    void setAvailableBalance(long availableBalance);

    long getQuantityBookBalance();

    void setQuantityBookBalance(long quantityBookBalance);

    long getQuantityAvailableBalance();

    void setQuantityAvailableBalance(long quantityAvailableBalance);
}
