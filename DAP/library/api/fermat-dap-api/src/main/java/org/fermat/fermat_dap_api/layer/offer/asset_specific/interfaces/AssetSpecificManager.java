package org.fermat.fermat_dap_api.layer.offer.asset_specific.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;

import org.fermat.fermat_dap_api.layer.all_definition.offer.AssetSpecificOffer;
import org.fermat.fermat_dap_api.layer.offer.asset_specific.exceptions.CantGetAssetSpecificException;
import org.fermat.fermat_dap_api.layer.offer.asset_specific.exceptions.CantStoreAssetSpecificException;

import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 12/04/16.
 */
public interface AssetSpecificManager extends FermatManager {

    /**
     * Stores the passed asset offer
     *
     * @param offer
     * @throws CantStoreAssetSpecificException
     */
    void storeAssetSpecificOffer(AssetSpecificOffer offer) throws CantStoreAssetSpecificException;

    /**
     * Gets a previously stored asset offer
     *
     * @param id the id of the offer to get (must exists)
     * @return the stored offer
     * @throws CantGetAssetSpecificException if not found.
     */
    AssetSpecificOffer getAssetSpecificOffer(UUID id) throws CantGetAssetSpecificException;

}
