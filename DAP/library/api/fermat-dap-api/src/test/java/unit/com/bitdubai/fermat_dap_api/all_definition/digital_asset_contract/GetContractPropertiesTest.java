package unit.com.bitdubai.fermat_dap_api.all_definition.digital_asset_contract;

import org.fermat.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import org.fermat.fermat_dap_api.layer.all_definition.contracts.exceptions.CantDefineContractPropertyException;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import org.junit.Test;

import java.util.List;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 06/10/15.
 */
public class GetContractPropertiesTest {

    @Test
    public void getContractPropertiesListTest() {
        DigitalAssetContract digitalAssetContract = new DigitalAssetContract();
        List<ContractProperty> contractPropertyList = digitalAssetContract.getContractProperties();
        for (ContractProperty contractProperty : contractPropertyList) {
            System.out.println(contractProperty.getName());
            System.out.println(contractProperty.getValue());
        }

    }

    @Test
    public void getContractPropertiesListWithValueTest() throws CantDefineContractPropertyException {
        DigitalAssetContract digitalAssetContract = new DigitalAssetContract();
        ContractProperty redeemable = new ContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE, "Test redeemable");
        ContractProperty expirationDate = new ContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE, "test expiration date");
        digitalAssetContract.setContractProperty(redeemable);
        digitalAssetContract.setContractProperty(expirationDate);
        List<ContractProperty> contractPropertyList = digitalAssetContract.getContractProperties();
        for (ContractProperty contractProperty : contractPropertyList) {
            System.out.println(contractProperty.getName());
            System.out.println(contractProperty.getValue());
        }

    }

}
