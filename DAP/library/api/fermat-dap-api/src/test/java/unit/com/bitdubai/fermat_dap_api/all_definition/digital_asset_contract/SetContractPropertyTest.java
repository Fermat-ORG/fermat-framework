package unit.com.bitdubai.fermat_dap_api.all_definition.digital_asset_contract;

import org.fermat.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import org.fermat.fermat_dap_api.layer.all_definition.contracts.exceptions.CantDefineContractPropertyException;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import org.junit.Test;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 06/10/15.
 */
public class SetContractPropertyTest {

    @Test
    public void setContractPropertyTestAssigningProperties() throws CantDefineContractPropertyException {
        DigitalAssetContract digitalAssetContract=new DigitalAssetContract();
        ContractProperty redeemable = new ContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE, null);
        redeemable.setValue("test name 0");
        ContractProperty expirationDate= new ContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE, null);
        expirationDate.setValue("test date 0");
        digitalAssetContract.setContractProperty(redeemable);
        digitalAssetContract.setContractProperty(expirationDate);
        System.out.println("0 Redeemable Name:" + digitalAssetContract.getContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE).getName());
        System.out.println("0 Redeemable Value:"+digitalAssetContract.getContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE).getValue());
        redeemable.setValue("test name 1");
        expirationDate= new ContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE, null);
        expirationDate.setValue("test date 1");
        digitalAssetContract.setContractProperty(redeemable);
        digitalAssetContract.setContractProperty(expirationDate);
        System.out.println("1 Redeemable Name:" + digitalAssetContract.getContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE).getName());
        System.out.println("1 Redeemable Value:"+digitalAssetContract.getContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE).getValue());
    }
}
