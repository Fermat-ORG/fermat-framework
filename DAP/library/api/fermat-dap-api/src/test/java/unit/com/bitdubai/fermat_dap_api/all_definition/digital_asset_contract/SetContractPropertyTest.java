package unit.com.bitdubai.fermat_dap_api.all_definition.digital_asset_contract;

import com.bitdubai.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import com.bitdubai.fermat_dap_api.layer.all_definition.contracts.exceptions.CantDefineContractPropertyException;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;

import org.junit.Test;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 06/10/15.
 */
public class SetContractPropertyTest {

    @Test
    public void setContractPropertyTestAssigningProperties() throws CantDefineContractPropertyException {
        DigitalAssetContract digitalAssetContract=new DigitalAssetContract();
        ContractProperty redeemable = new ContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE, null);
        redeemable.setValue("test name");
        ContractProperty expirationDate= new ContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE, null);
        expirationDate.setValue("test date");
        digitalAssetContract.setContractProperty(redeemable);
        digitalAssetContract.setContractProperty(expirationDate);
        System.out.println("Redeemable Name:" + digitalAssetContract.getContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE).getName());
        System.out.println("Redeemable Value:"+digitalAssetContract.getContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE).getValue());
    }
}
