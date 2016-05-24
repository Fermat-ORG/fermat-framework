package unit.com.bitdubai.fermat_dap_api.all_definition.digital_asset;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
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
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 9/25/15.
 */
public class XMLParserOnDigitalAssetTest {
    @Test
    public void parseToXMLTest() throws CantDefineContractPropertyException {
        DigitalAsset digitalAsset = new DigitalAsset();
        digitalAsset.setPublicKey(new ECCKeyPair().getPublicKey());
        digitalAsset.setDescription("Descripcion de prueba");
        digitalAsset.setGenesisAddress(new CryptoAddress("n1zVgphtAoxgDMUzKV5ATeggwvUwnssb7m", CryptoCurrency.BITCOIN));
        digitalAsset.setGenesisAmount(1000);

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
        digitalAsset.setResources(resources);
        IdentityAssetIssuer identityAssetIssuer = new IdentityAssetIssuer() {
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
            public String createMessageSignature(String mensage) /*throws CantSingMessageException*/ {
                return "signature";
            }
        };
        digitalAsset.setName("Asset de prueba");
        digitalAsset.setIdentityAssetIssuer(identityAssetIssuer);
        DigitalAssetContract contract = new DigitalAssetContract();
        contract.setContractProperty(new ContractProperty(DigitalAssetContractPropertiesConstants.REDEEMABLE, Boolean.TRUE));
        digitalAsset.setContract(contract);

        System.out.println(digitalAsset.toString());

    }




}
