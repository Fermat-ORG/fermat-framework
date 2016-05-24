package unit.com.bitdubai.fermat_dap_api.all_definition.digital_asset;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceDensity;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceType;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;

import org.fermat.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import org.fermat.fermat_dap_api.layer.all_definition.contracts.exceptions.CantDefineContractPropertyException;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.State;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/11/15.
 */
public class LocalStorageTest {

    @Test
    public void parseXMLTest() throws CantDefineContractPropertyException {
        DigitalAssetMetadata digitalAssetMetadata=getDigitalAssetMetadataForTest();
        String xmlDAM=digitalAssetMetadata.toString();
        System.out.println(xmlDAM);
        DigitalAssetMetadata recoveredDigitalAssetMetadata=new DigitalAssetMetadata();
        recoveredDigitalAssetMetadata=(DigitalAssetMetadata) XMLParser.parseXML(xmlDAM, recoveredDigitalAssetMetadata);
        if(recoveredDigitalAssetMetadata instanceof DigitalAssetMetadata){
            System.out.println("DAM is correct");
        }
        DigitalAsset digitalAsset=recoveredDigitalAssetMetadata.getDigitalAsset();
        if(digitalAsset instanceof DigitalAsset){
            System.out.println("DA is correct");
        }
    }

    private DigitalAssetMetadata getDigitalAssetMetadataForTest() throws CantDefineContractPropertyException {
        DigitalAsset mockedDigitalAsset=getDigitalAssetForTesting();
        DigitalAssetMetadata mockedDigitalAssetMetadata=new DigitalAssetMetadata();
        mockedDigitalAssetMetadata.setDigitalAsset(mockedDigitalAsset);
//        mockedDigitalAssetMetadata.setGenesisTransaction("d21633ba23f70118185227be58a63527675641ad37967e2aa461559f577aec43");
        String hash=mockedDigitalAssetMetadata.getDigitalAssetHash();
        System.out.println("DAM - HASH: " + hash);
        return mockedDigitalAssetMetadata;
    }

    private IdentityAssetIssuer getIdentityAssetIssuerForTesting(){
        IdentityAssetIssuer identityAssetIssuer=new IdentityAssetIssuer() {
            @Override
            public String getAlias() {
                return "Franklin Marcano";
            }

            @Override
            public byte[] getImage() {
                return new byte[0];
            }

            @Override
            public String getPublicKey() {
                return "ASDS-10087982";
            }

            @Override
            public Actors getActorType() {
                return null;
            }

            //@Override
            public byte[] getProfileImage() {
                return new byte[0];
            }

            @Override
            public void setNewProfileImage(byte[] newProfileImage) {

            }

            @Override
            public String createMessageSignature(String message) {
                return "signature";
            }
        };
        return identityAssetIssuer;

    }

    private DigitalAsset getDigitalAssetForTesting() throws CantDefineContractPropertyException {
        DigitalAsset mockedDigitalAsset=new DigitalAsset();
        //Genesis Address
        CryptoAddress testCryptoAddress=new CryptoAddress();
        testCryptoAddress.setAddress("mxJJSdXdKQLS4NeX6Y8tXFFoNASQnBShtv");
        testCryptoAddress.setCryptoCurrency(CryptoCurrency.BITCOIN);
        mockedDigitalAsset.setGenesisAddress(testCryptoAddress);
        //Identity
        IdentityAssetIssuer testIdentity=getIdentityAssetIssuerForTesting();
        mockedDigitalAsset.setIdentityAssetIssuer(testIdentity);
        //Contract
        DigitalAssetContract contract = new DigitalAssetContract();
        contract.setContractProperty(new ContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE, Boolean.TRUE));
        Expiration date - we choose 90 days from now, you can change for testing
        Timestamp expirationDateTimestamp=getExpirationDate(90);
        contract.setContractProperty(new ContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE, expirationDateTimestamp));
        mockedDigitalAsset.setContract(contract);
        //Description
        mockedDigitalAsset.setDescription("Skynet is working for you");
        //Public key
        mockedDigitalAsset.setPublicKey(new ECCKeyPair().getPublicKey());
        //Name
        mockedDigitalAsset.setName("Skynet Coupon");
        //Genesis Amount in satoshis
        mockedDigitalAsset.setGenesisAmount(100000);
        //State
        mockedDigitalAsset.setState(State.FINAL);
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

        mockedDigitalAsset.setResources(resources);

        return mockedDigitalAsset;
    }

    private ActorAssetUser getActorAssetUserForTest(){
        ActorAssetUser mockedActorAssetUser=new ActorAssetUser()
        {
            @Override
            public String getPublicLinkedIdentity() {
                return new ECCKeyPair().getPublicKey();
            }

            @Override
            public String getActorPublicKey() {
                return new ECCKeyPair().getPublicKey();
            }

            @Override
            public String getName() {
                return "Actor Asset User Patriotic Name";
            }

            @Override
            public String getAge() {
                return "90";
            }

            @Override
            public Genders getGenders() {
                return Genders.MALE;
            }

            //Comment by Luis Campo
            //Method no exist in interface ActorAssetUser
            /*@Override
            public ConnectionState getConnectionState() {
                return ConnectionState.CONNECTED;
            }*/

            /**
             * The method <code>getLocation</code> gives us the Location of the represented Asset user
             *
             * @return the Location of the Asset user
             */

            /*
            @Override
            public Location getLocation() {
                return null;
            }

            @Override
            public long getRegistrationDate() {
                return 0;
            }

            @Override
            public long getLastConnectionDate() {
                return 0;
            }

            @Override
            public DAPConnectionState getDapConnectionState() {
                return null;
            }

            @Override
            public Double getLocationLatitude() {
                return 24.846565;
            }

            @Override
            public Double getLocationLongitude() {
                return 1.054688;
            }

            @Override
            public byte[] getProfileImage() {
                return new byte[0];
            }

            @Override
            public CryptoAddress getCryptoAddress() {
                CryptoAddress actorUserCryptoAddress=new CryptoAddress("mqBuPbxaxKzni6uTQCyVxK2FRLbcmaDrsV", CryptoCurrency.BITCOIN);
                return actorUserCryptoAddress;
            }
        };
        return mockedActorAssetUser;
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
