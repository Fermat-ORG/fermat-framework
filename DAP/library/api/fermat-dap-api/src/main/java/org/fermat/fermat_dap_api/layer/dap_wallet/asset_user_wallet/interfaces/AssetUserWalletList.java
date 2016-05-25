package org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by franklin on 05/10/15.
 */
public interface AssetUserWalletList extends Serializable {

    DigitalAsset getDigitalAsset();

    List<CryptoAddress> getAddresses();

    void setAddresses(Set<CryptoAddress> addresses);

    long getAvailableBalance();

    void setAvailableBalance(long availableBalance);

    long getBookBalance();

    void setBookBalance(long bookBalance);

    void setDigitalAsset(DigitalAsset digitalAsset);

    long getQuantityBookBalance();

    void setQuantityBookBalance(long quantityBookBalance);

    long getQuantityAvailableBalance();

    void setQuantityAvailableBalance(long quantityAvailableBalance);

    int getLockedAssets();

    void setLockedAssets(int lockedAssets);
}
