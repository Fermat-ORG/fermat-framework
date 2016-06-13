package org.fermat.fermat_dap_api.layer.metadata.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.metadata.exceptions.CantGetDigitalAssetMetadataException;
import org.fermat.fermat_dap_api.layer.metadata.exceptions.CantStoreDigitalAssetMetadataException;

import java.util.UUID;

/**
 * Created by rodrigo on 4/10/16.
 */
public interface DigitalAssetMetadataManager extends FermatManager {
    /**
     * Stores the passed digital asset Metadata
     *
     * @param digitalAssetMetadata
     * @throws CantStoreDigitalAssetMetadataException
     */
    void storeDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata) throws CantStoreDigitalAssetMetadataException;

    /**
     * Gets a previously stored asset metadata
     *
     * @param id the id of the metadata to get (must exists)
     * @return the stored metadata
     * @throws CantGetDigitalAssetMetadataException if not found.
     */
    DigitalAssetMetadata getDigitalAssetMetadata(UUID id) throws CantGetDigitalAssetMetadataException;

}
