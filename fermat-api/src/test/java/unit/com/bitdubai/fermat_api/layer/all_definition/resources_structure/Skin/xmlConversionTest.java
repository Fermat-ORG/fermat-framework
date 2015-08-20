package unit.com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;


import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Layout;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceDensity;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceType;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.all_definition.util.VersionCompatibility;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;


import junit.framework.TestCase;
import junit.framework.TestResult;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
/*
=======
>>>>>>> de82977c2779fd194c0499e6f6d841387bf25cf5
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by rodrigo on 2015.07.24..
 */
public class xmlConversionTest extends TestCase {

    // TODO REVIEW THIS.
    @Test
    public void testPrueba() {
        String hello = "World";
    }

    /*
    public void testcreateResult() {
        Map<String,Resource> lstRecursosPortrait = new HashMap<String,Resource>();
        lstRecursosPortrait.put("person1", new Resource(UUID.randomUUID(), "person1", "person1.png", ResourceType.IMAGE, ResourceDensity.MDPI));

        Map<String,Resource> lstRecursosLandscape = new HashMap<String,Resource>();
        lstRecursosLandscape.put("person1", new Resource(UUID.randomUUID(), "person1", "person1.png", ResourceType.IMAGE, ResourceDensity.MDPI));

        Map<String,Layout> lstLayoutsPortrait = new HashMap<String,Layout>();
        lstLayoutsPortrait.put("wallets_bitcoin_fragment_balance", new Layout(UUID.randomUUID(), "wallets_bitcoin_fragment_balance.xml"));
        lstLayoutsPortrait.put("wallets_bitcoin_fragment_transactions", new Layout(UUID.randomUUID(), "wallets_bitcoin_fragment_transactions.xml"));

        Map<String,Layout> lstLayoutsLandscape = new HashMap<String,Layout>();
        lstLayoutsLandscape.put("wallets_bitcoin_fragment_balance", new Layout(UUID.randomUUID(), "wallets_bitcoin_fragment_balance.xml"));
        lstLayoutsLandscape.put("wallets_bitcoin_fragment_transactions", new Layout(UUID.randomUUID(), "wallets_bitcoin_fragment_transactions.xml"));

        Version minVersion = new Version(1,0,0);

        Version maxVersion = new Version(1,0,0);

        VersionCompatibility compatibility = null;
        try {

            compatibility = new VersionCompatibility(minVersion,maxVersion);

        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }


        Skin skin = new Skin(UUID.randomUUID(),"basic_wallet_default",new Version(1,1,1),compatibility, ScreenSize.MEDIUM,lstRecursosPortrait,lstRecursosLandscape,lstLayoutsPortrait,lstLayoutsLandscape);

        String xml = XMLParser.parseObject(skin);

        System.out.println(xml);
    }
*/
}