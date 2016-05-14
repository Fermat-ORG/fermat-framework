package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.mocks;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceDensity;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceType;

import org.fermat.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import org.fermat.fermat_dap_api.layer.all_definition.contracts.exceptions.CantDefineContractPropertyException;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import org.fermat.fermat_dap_api.layer.all_definition.enums.State;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
        testCryptoAddress.setCryptoCurrency(CryptoCurrency.BITCOIN);
        setGenesisAddress(testCryptoAddress);
        //Identity
        MockIdentityAssetIssuerForTest testIdentity=new MockIdentityAssetIssuerForTest();
        setIdentityAssetIssuer(testIdentity);
        //Contract
        DigitalAssetContract contract = new DigitalAssetContract();
        contract.setContractProperty(new ContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE, Boolean.TRUE));
        //Expiration date - we choose 90 days from now, you can change for testing
        Timestamp expirationDateTimestamp=getExpirationDate(90);
        contract.setContractProperty(new ContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE, expirationDateTimestamp));
        setContract(contract);
        //Description
        setDescription("Patriapon - el cupón patriótico");
        //Public key
        setPublicKey(new ECCKeyPair().getPublicKey());
        //Name
        setName("Patriapon");
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

    /**
     * This method returns a fake date, days after the present time, according the daysFromNow Argument.
     * We will add daysFromNow to actual date and returns this new date.
     * Please, don't use negative numbers in daysFromNow, this method will use absolute value.
     * Don't use 0, because this method will set the value to 90.
     * @param daysFromNow days to add to present date.
     * @return
     */
    private Timestamp getExpirationDate(int daysFromNow){
        if(daysFromNow<0){
            daysFromNow=Math.abs(daysFromNow);
        }
        if(daysFromNow==0){
            daysFromNow=90;
        }
        Date date=new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(date.getTime());
        calendar.add(calendar.DATE, daysFromNow);
        date=new Date(calendar.getTimeInMillis());
        return new Timestamp(date.getTime());
    }

}
