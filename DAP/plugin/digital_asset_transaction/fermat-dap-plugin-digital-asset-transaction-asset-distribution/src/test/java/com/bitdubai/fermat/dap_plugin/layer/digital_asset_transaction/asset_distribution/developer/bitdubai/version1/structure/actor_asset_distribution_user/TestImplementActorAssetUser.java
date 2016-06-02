package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.actor_asset_distribution_user;

import com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.mocks.ActorAssetDistributionUser;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.junit.Test;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/10/15.
 */
public class TestImplementActorAssetUser {

    @Test
    public void implementationTest() {
        ActorAssetUser actorAssetUser = new MockActorAssetUser();
        System.out.println(actorAssetUser.getActorPublicKey());
        ActorAssetDistributionUser actorAssetDistributionUser = new ActorAssetDistributionUser();
        actorAssetDistributionUser.setActorAssetUser(actorAssetUser);
        System.out.println(actorAssetDistributionUser.getActorPublicKey());
        String actorXML = XMLParser.parseObject(actorAssetDistributionUser);
        System.out.println(actorXML);
        ActorAssetUser newActorAssetUser = actorAssetDistributionUser;
        actorXML = XMLParser.parseObject(newActorAssetUser);
        System.out.println(actorXML);
    }

    /*@Test
    public void recoveringFromXMLTest(){
        System.out.println("Recovering from XML Test");
        ActorAssetUser actorAssetUser=new MockActorAssetUser();
        ActorAssetDistributionUser actorAssetDistributionUser= new  ActorAssetDistributionUser();
        actorAssetDistributionUser.setActorAssetUser(actorAssetUser);
        String actorXML= XMLParser.parseObject(actorAssetDistributionUser);
        System.out.println(actorXML);
        ActorAssetDistributionUser xmlActorAssetDistributionUser=new  ActorAssetDistributionUser();
        xmlActorAssetDistributionUser= (ActorAssetDistributionUser) XMLParser.parseXML(actorXML, xmlActorAssetDistributionUser);
        System.out.println("Name from recovered actor: "+xmlActorAssetDistributionUser.getName());
    }*/

}
