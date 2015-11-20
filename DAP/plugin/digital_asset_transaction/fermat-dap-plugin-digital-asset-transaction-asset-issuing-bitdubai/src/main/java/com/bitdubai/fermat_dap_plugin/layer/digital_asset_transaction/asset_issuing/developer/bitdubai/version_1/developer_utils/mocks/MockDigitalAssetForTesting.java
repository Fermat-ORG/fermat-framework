package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.developer_utils.mocks;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceDensity;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceType;
import com.bitdubai.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import com.bitdubai.fermat_dap_api.layer.all_definition.contracts.exceptions.CantDefineContractPropertyException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 16/10/15.
 */
public class MockDigitalAssetForTesting extends DigitalAsset {

    public MockDigitalAssetForTesting() throws CantDefineContractPropertyException {
        //Genesis Address
        CryptoAddress testCryptoAddress=new CryptoAddress();
        testCryptoAddress.setAddress("mxJJSdXdKQLS4NeX6Y8tXFFoNASQnBShtv");
        testCryptoAddress.setCryptoCurrency(CryptoCurrency.CHAVEZCOIN);
        setGenesisAddress(testCryptoAddress);
        //Identity
        MockIdentityAssetIssuerForTest testIdentity=new MockIdentityAssetIssuerForTest();
        setIdentityAssetIssuer(testIdentity);
        //Contract
        DigitalAssetContract contract = new DigitalAssetContract();
        contract.setContractProperty(new ContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE, Boolean.TRUE));
        setContract(contract);
        //Description
        setDescription("MockDigitalAsset");
        //Public key
        setPublicKey("testPublicKey");
        //Name
        setName("Digital Asset for testing");
        //Genesis Amount in satoshis
        setGenesisAmount(100000);
        //State
        setState(State.FINAL);
        //Resources
        List<Resource> resources = new ArrayList<>();
        Resource resource = new Resource();
        resource.setId(UUID.randomUUID());
        resource.setName("Foto 1");
        resource.setFileName("imagen2.png");
        resource.setResourceType(ResourceType.IMAGE);
        resource.setResourceDensity(ResourceDensity.HDPI);
        resource.setResourceBinayData(new byte[]{0xa, 0x2, 0xf, (byte) 0xff, (byte) 0xff, (byte) 0xff});

        Resource resource2 = new Resource();
        resource2.setId(UUID.randomUUID());
        resource2.setName("Foto 1");
        resource2.setFileName("imagen2.png");
        resource2.setResourceType(ResourceType.IMAGE);
        resource2.setResourceDensity(ResourceDensity.HDPI);
        resource2.setResourceBinayData(new byte[]{0xa, 0x2, 0xf, (byte) 0xff, (byte) 0xff, (byte) 0xff});

        Resource resource3 = new Resource();
        resource3.setId(UUID.randomUUID());
        resource3.setName("Foto 1");
        resource3.setFileName("imagen2.png");
        resource3.setResourceType(ResourceType.IMAGE);
        resource3.setResourceDensity(ResourceDensity.HDPI);
        resource3.setResourceBinayData(new byte[]{0xa, 0x2, 0xf, (byte) 0xff, (byte) 0xff, (byte) 0xff});

        resources.add(resource);
        resources.add(resource2);
        resources.add(resource3);

        setResources(resources);

    }

}
