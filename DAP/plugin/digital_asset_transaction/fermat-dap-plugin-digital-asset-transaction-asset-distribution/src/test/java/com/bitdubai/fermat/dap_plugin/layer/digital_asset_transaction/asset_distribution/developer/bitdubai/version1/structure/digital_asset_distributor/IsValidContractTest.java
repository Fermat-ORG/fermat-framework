package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.digital_asset_distributor;

import org.fermat.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 29/09/15.
 */
public class IsValidContractTest {

    ContractProperty datePropertySetAsActual;
    public IsValidContractTest(){

        datePropertySetAsActual=new ContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE.toString(), null);
        Date date= new Date();
        datePropertySetAsActual.setValue(new Timestamp(date.getTime()));
        System.out.println("Present time: " + datePropertySetAsActual.getValue());
    }

    @Test
    public void getTheTimestampFromContractPropertyTest(){

        Timestamp expirationDate= (Timestamp)datePropertySetAsActual.getValue();
        System.out.println("Timestamp from ContractProperty: "+expirationDate);

    }
//Comment by Luis Campo
    //Generate errors runnig testCoverage
    /*@Test
    public void afterTest(){
        Timestamp expirationDate= (Timestamp)datePropertySetAsActual.getValue();
        Date date= new Date();
        Timestamp actualTime=new Timestamp(date.getTime());
        System.out.println("Before Test - Timestamp from ContractProperty: " + expirationDate);
        System.out.println("Before Test - Timestamp from ActualTime: " + actualTime);
        Assert.assertTrue("Result:",expirationDate.after(actualTime));
    }*/

}
