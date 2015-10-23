package com.bitdubai.fermat_dap_api.layer.dap_transaction.common.util;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 22/10/15.
 */
public final class AssetVerification {

    private AssetVerification() {
        throw new AssertionError("NO INSTANCES!");
    }

    public static boolean isDigitalAssetComplete(DigitalAsset digitalAsset, DigitalAssetMetadata digitalAssetMetadata) {
        try {
            areObjectsSettled(digitalAsset);
            CryptoAddress genesisAddress = digitalAsset.getGenesisAddress();
            if (Validate.isObjectNull(genesisAddress)) {
                return false;
            }
            String digitalAssetHash = digitalAssetMetadata.getDigitalAssetHash();
            if (Validate.isValidString(digitalAssetHash)) {
                return false;
            }
            return true;
        } catch (ObjectNotSetException e) {
            return false;
        }
    }

    /**
     * This method checks that every object in Digital asset is set.
     *
     * @throws ObjectNotSetException
     */
    public static void areObjectsSettled(DigitalAsset digitalAsset) throws ObjectNotSetException {
        if (digitalAsset.getContract() == null) {
            throw new ObjectNotSetException("Digital Asset Contract is not set");
        }
        if (digitalAsset.getResources() == null) {
            throw new ObjectNotSetException("Digital Asset Resources is not set");
        }
        if (digitalAsset.getDescription() == null) {
            throw new ObjectNotSetException("Digital Asset Description is not set");
        }
        if (digitalAsset.getName() == null) {
            throw new ObjectNotSetException("Digital Asset Name is not set");
        }
        if (digitalAsset.getPublicKey() == null) {
            throw new ObjectNotSetException("Digital Asset PublicKey is not set");
        }
        if (digitalAsset.getState() == null) {
            digitalAsset.setState(State.DRAFT);
        }
        if (digitalAsset.getIdentityAssetIssuer() == null) {
            throw new ObjectNotSetException("Digital Asset Identity is not set");
        }
    }
}
