package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.digital_asset_distributor;

import com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.mocks.MockDigitalAssetMetadataForTesting;

import org.fermat.fermat_dap_api.layer.all_definition.contracts.exceptions.CantDefineContractPropertyException;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 24/10/15.
 */
public class MocksImplementationTest {

    @Test
    public void testImplementation() throws CantDefineContractPropertyException {
        MockDigitalAssetMetadataForTesting mockDigitalAssetMetadata = new MockDigitalAssetMetadataForTesting();
        System.out.println("--------------Digital Asset Metadata XML:\n" + mockDigitalAssetMetadata);
        System.out.println("\n--------------Digital Asset Metadata Hash:\n" + mockDigitalAssetMetadata.getDigitalAssetHash());
        System.out.println("\n--------------Digital Asset Metadata genesis transaction:\n" + mockDigitalAssetMetadata.getGenesisTransaction());
        DigitalAsset mockDigitalAsset = mockDigitalAssetMetadata.getDigitalAsset();
        System.out.println("\n--------------Digital Asset Metadata XML:\n" + mockDigitalAsset);
        System.out.println("\n--------------Digital Asset Metadata genesis address:\n" + mockDigitalAsset.getGenesisAddress().getAddress());
        System.out.println("\n--------------Digital Asset Metadata public key:\n" + mockDigitalAsset.getPublicKey());
        Timestamp expirationDate = (Timestamp) mockDigitalAsset.getContract().getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE).getValue();
        Date date = new Date(expirationDate.getTime());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        System.out.println("\n--------------Digital Asset Metadata expiration date:\n" + calendar);
    }
}
