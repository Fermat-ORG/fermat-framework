package unit.com.bitdubai.fermat_dap_api.all_definition.digital_asset;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import org.fermat.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.State;
import org.junit.Assert;
import org.junit.Test;


/**
 * Created by rodrigo on 9/4/15.
 */
public class DigitalAssetTests {
    DigitalAsset digitalAsset;

    @Test
    public void createNewDigitalAsset() {
        digitalAsset = new DigitalAsset();
        // I validate is in draft state.
        Assert.assertEquals(State.DRAFT, digitalAsset.getState());

        digitalAsset.setPublicKey("testPublicKey");
        digitalAsset.setName("Prueba de Asset");
        digitalAsset.setDescription("description");
        digitalAsset.setGenesisAddress(new CryptoAddress("Fake Address", CryptoCurrency.BITCOIN));
        DigitalAssetContract contract = new DigitalAssetContract();
        digitalAsset.setContract(contract);
        ContractProperty contractProperty = contract.getContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE);
        contractProperty.setValue(Boolean.TRUE);

        // I validate that the Redeemable property of the contract was set to true
        Assert.assertTrue((Boolean) digitalAsset.getContract().getContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE).getValue());

        // I will assume the digital asset is complete, and hash it.

        DigitalAssetMetadata metadata = new DigitalAssetMetadata(digitalAsset);
        String hash1 = metadata.getDigitalAssetHash();

        // I will validate the digital Asset is in Final state
        Assert.assertEquals(digitalAsset.getState(), State.FINAL);

        // I will change the digital Asset and re generate the hash
        digitalAsset.setGenesisAmount(1000);
        String hash2 = metadata.getDigitalAssetHash();

        Assert.assertNotEquals(hash1, hash2);
    }
}
