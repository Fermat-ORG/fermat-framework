/*
package unit.com.bitdubai.fermat_api.logictest;

import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatPluginsEnum;
import PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Developers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

*/
/**
 * Created by lnacosta (laion.cj91@gmail.com) on 21/10/2015.
 *//*

public class LogicTest {

    public enum CCPPlugins implements FermatPluginsEnum {

        */
/**
         * For doing the code more readable, please keep the elements in the Enum sorted alphabetically.
         *//*


        BITDUBAI_BITCOIN_WALLET_BASIC_WALLET            ("BBWBW" , Developers.BITDUBAI, Layers.BASIC_WALLET   ),
        BITDUBAI_CRYPTO_ADDRESSES_NETWORK_SERVICE       ("BCANS" , Developers.BITDUBAI, Layers.NETWORK_SERVICE),
        BITDUBAI_CRYPTO_PAYMENT_REQUEST                 ("BCPR"  , Developers.BITDUBAI, Layers.REQUEST        ),
        BITDUBAI_CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE ("BCPRNS", Developers.BITDUBAI, Layers.NETWORK_SERVICE),
        BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE    ("BCTNS" , Developers.BITDUBAI, Layers.NETWORK_SERVICE),
        BITDUBAI_CRYPTO_WALLET_MODULE                   ("BCWM"  , Developers.BITDUBAI, Layers.WALLET_MODULE  ),
        BITDUBAI_EXTRA_WALLET_USER_ACTOR                ("BEWUA" , Developers.BITDUBAI, Layers.ACTOR          ),
        BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION        ("BIEUT" , Developers.BITDUBAI, Layers.TRANSACTION    ),
        BITDUBAI_INCOMING_INTRA_USER_TRANSACTION        ("BIIUT" , Developers.BITDUBAI, Layers.TRANSACTION    ),
        BITDUBAI_INTRA_USER_NETWORK_SERVICE             ("BIUNS" , Developers.BITDUBAI, Layers.NETWORK_SERVICE),
        BITDUBAI_INTRA_WALLET_USER_ACTOR                ("BIWUA" , Developers.BITDUBAI, Layers.ACTOR          ),
        BITDUBAI_INTRA_WALLET_USER_IDENTITY             ("BIUI"  , Developers.BITDUBAI, Layers.IDENTITY       ),
        BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION        ("BOEUT" , Developers.BITDUBAI, Layers.TRANSACTION    ),
        BITDUBAI_OUTGOING_INTRA_ACTOR_TRANSACTION       ("BOIAT" , Developers.BITDUBAI, Layers.TRANSACTION    ),
        BITDUBAI_WALLET_CONTACTS_MIDDLEWARE             ("BWCM"  , Developers.BITDUBAI, Layers.MIDDLEWARE     ),

        ;

        private final String     code     ;
        private final Developers developer;
        private final Layers     layer    ;

        CCPPlugins(final String     code     ,
                   final Developers developer,
                   final Layers     layer    ) {

            this.code      = code     ;
            this.developer = developer;
            this.layer = layer;
        }

        public static CCPPlugins getByCode(String code) throws InvalidParameterException {

            switch (code) {

                case "BBWBW" : return BITDUBAI_BITCOIN_WALLET_BASIC_WALLET           ;
                case "BCANS" : return BITDUBAI_CRYPTO_ADDRESSES_NETWORK_SERVICE      ;
                case "BCPR"  : return BITDUBAI_CRYPTO_PAYMENT_REQUEST                ;
                case "BCPRNS": return BITDUBAI_CRYPTO_PAYMENT_REQUEST_NETWORK_SERVICE;
                case "BCTNS" : return BITDUBAI_CRYPTO_TRANSMISSION_NETWORK_SERVICE   ;
                case "BCWM"  : return BITDUBAI_CRYPTO_WALLET_MODULE                  ;
                case "BEWUA" : return BITDUBAI_EXTRA_WALLET_USER_ACTOR               ;
                case "BIEUT" : return BITDUBAI_INCOMING_EXTRA_USER_TRANSACTION       ;
                case "BIIUT" : return BITDUBAI_INCOMING_INTRA_USER_TRANSACTION       ;
                case "BIUNS" : return BITDUBAI_INTRA_USER_NETWORK_SERVICE            ;
                case "BIWUA" : return BITDUBAI_INTRA_WALLET_USER_ACTOR               ;
                case "BIUI"  : return BITDUBAI_INTRA_WALLET_USER_IDENTITY            ;
                case "BOEUT" : return BITDUBAI_OUTGOING_EXTRA_USER_TRANSACTION       ;
                case "BOIAT" : return BITDUBAI_OUTGOING_INTRA_ACTOR_TRANSACTION      ;
                case "BWCM"  : return BITDUBAI_WALLET_CONTACTS_MIDDLEWARE            ;

                default:
                    throw new InvalidParameterException(
                            "Code Received: " + code,
                            "The received code is not valid for the CCPPlugins enum"
                    );
            }
        }

        @Override
        public Platforms getPlatform() {
            return Platforms.CRYPTO_CURRENCY_PLATFORM;
        }

        @Override
        public Layers getLayer() {
            return this.layer;
        }

        @Override
        public Developers getDeveloper() {
            return this.developer;
        }

        @Override
        public String getCode() {
            return this.code;
        }

    }


    private Map<PluginReference, Integer> pluginLevels = new HashMap<>();

    private Map<PluginReference, List<PluginReference>> pluginReferenceListMap = new HashMap<>();

    private PluginReference buildPR(FermatPluginsEnum e){
        return new PluginReference(e, new Version("1.0.0"));
    }

    private List<PluginReference> buildRL(PluginReference...refs) {
        return Arrays.asList(refs);
    }

    private void chargeTestingDataWithCyclicReferences() {
        // testing data
        pluginReferenceListMap = new HashMap<>();

        PluginReference basicWallet = buildPR(CCPPlugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET);
        PluginReference cryptoWallet = buildPR(CCPPlugins.BITDUBAI_CRYPTO_WALLET_MODULE);
        PluginReference intraActor  = buildPR(CCPPlugins.BITDUBAI_INTRA_WALLET_USER_ACTOR);
        PluginReference intraIdentity = buildPR(CCPPlugins.BITDUBAI_INTRA_WALLET_USER_IDENTITY);
        PluginReference extraWallet = buildPR(CCPPlugins.BITDUBAI_EXTRA_WALLET_USER_ACTOR);
        PluginReference walletContacts = buildPR(CCPPlugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE);

        List<PluginReference> basicWalletRefs = buildRL(
                buildPR(CCPPlugins.BITDUBAI_CRYPTO_WALLET_MODULE)
        );

        pluginReferenceListMap.put(
                basicWallet,
                basicWalletRefs
        );

        List<PluginReference> cryptoWalletRefs = buildRL(
                buildPR(CCPPlugins.BITDUBAI_INTRA_WALLET_USER_ACTOR),
                buildPR(CCPPlugins.BITDUBAI_INTRA_WALLET_USER_IDENTITY),
                buildPR(CCPPlugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE)
        );

        pluginReferenceListMap.put(
                cryptoWallet,
                cryptoWalletRefs
        );

        List<PluginReference> intraActorRefs = buildRL(
                buildPR(CCPPlugins.BITDUBAI_INTRA_WALLET_USER_IDENTITY)
        );

        pluginReferenceListMap.put(
                intraActor,
                intraActorRefs
        );

        List<PluginReference> intraIdentityRefs = buildRL(
                buildPR(CCPPlugins.BITDUBAI_EXTRA_WALLET_USER_ACTOR)
        );

        pluginReferenceListMap.put(
                intraIdentity,
                intraIdentityRefs
        );

        pluginReferenceListMap.put(
                walletContacts,
                new ArrayList<PluginReference>()
        );

        List<PluginReference> extraWalletRefs = buildRL(
                buildPR(CCPPlugins.BITDUBAI_INTRA_WALLET_USER_IDENTITY)
        );
        pluginReferenceListMap.put(
                extraWallet,
                extraWalletRefs
        );

        // end testing data
    }

    private void chargeGoodTestingData() {
        // testing data
        pluginReferenceListMap = new HashMap<>();

        PluginReference basicWallet = buildPR(CCPPlugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET);
        PluginReference cryptoWallet = buildPR(CCPPlugins.BITDUBAI_CRYPTO_WALLET_MODULE);
        PluginReference intraActor  = buildPR(CCPPlugins.BITDUBAI_INTRA_WALLET_USER_ACTOR);
        PluginReference intraIdentity = buildPR(CCPPlugins.BITDUBAI_INTRA_WALLET_USER_IDENTITY);
        PluginReference extraWallet = buildPR(CCPPlugins.BITDUBAI_EXTRA_WALLET_USER_ACTOR);
        PluginReference walletContacts = buildPR(CCPPlugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE);

        List<PluginReference> basicWalletRefs = buildRL(
                buildPR(CCPPlugins.BITDUBAI_CRYPTO_WALLET_MODULE)
        );

        pluginReferenceListMap.put(
                basicWallet,
                basicWalletRefs
        );

        List<PluginReference> cryptoWalletRefs = buildRL(
                buildPR(CCPPlugins.BITDUBAI_INTRA_WALLET_USER_ACTOR),
                buildPR(CCPPlugins.BITDUBAI_INTRA_WALLET_USER_IDENTITY),
                buildPR(CCPPlugins.BITDUBAI_WALLET_CONTACTS_MIDDLEWARE)
        );

        pluginReferenceListMap.put(
                cryptoWallet,
                cryptoWalletRefs
        );

        List<PluginReference> intraActorRefs = buildRL(
                buildPR(CCPPlugins.BITDUBAI_INTRA_WALLET_USER_IDENTITY)
        );

        pluginReferenceListMap.put(
                intraActor,
                intraActorRefs
        );

        List<PluginReference> intraIdentityRefs = buildRL(
                buildPR(CCPPlugins.BITDUBAI_EXTRA_WALLET_USER_ACTOR)
        );

        pluginReferenceListMap.put(
                intraIdentity,
                intraIdentityRefs
        );

        pluginReferenceListMap.put(
                walletContacts,
                new ArrayList<PluginReference>()
        );


        pluginReferenceListMap.put(
                extraWallet,
                new ArrayList<PluginReference>()
        );

        // end testing data
    }

    @Test
    public void SimpleConstruction_ValidParameters_NotNull(){

        PluginReference pluginNeeded = buildPR(CCPPlugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET);

        chargeGoodTestingData();

        setLevels(pluginNeeded, 1);

        showInstantiationOrder();

        chargeTestingDataWithCyclicReferences();

        setLevels(pluginNeeded, 1);

        showInstantiationOrder();
    }

    private void showInstantiationOrder() {
        List<Map.Entry<PluginReference, Integer>> list = new LinkedList<>(pluginLevels.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<PluginReference,Integer>>() {
            public int compare(Map.Entry<PluginReference, Integer> o1, Map.Entry<PluginReference, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        System.out.println("************************************************************************");
        System.out.println("---- INIT Lista de Plugins a instanciar por orden. ----");
        System.out.println("************************************************************************");
        for (Map.Entry<PluginReference, Integer> entry : list) {
            System.out.println(entry.getKey().toString() + " - Level: "+entry.getValue());
        }

        System.out.println("************************************************************************");
        System.out.println("---- END  Lista de Plugins a instanciar por orden. ----");
        System.out.println("************************************************************************");
    }

    private void setLevels(PluginReference prToCalc, Integer lvlToAssign) {

        Integer lvlToAssignToReferences = lvlToAssign + 1;

        if(pluginLevels.containsKey(prToCalc)) {

            Integer actualLvl = pluginLevels.get(prToCalc);

            if (actualLvl < lvlToAssign) {
                pluginLevels.put(prToCalc, lvlToAssign);
                assignToReferences(prToCalc, lvlToAssignToReferences);
            } else if (actualLvl.equals(lvlToAssign)) {
                assignToReferences(prToCalc, lvlToAssignToReferences);
            } else {
                lvlToAssignToReferences = actualLvl + 1;
                assignToReferences(prToCalc, lvlToAssignToReferences);
            }

        } else {
            pluginLevels.put(prToCalc, lvlToAssign);
            assignToReferences(prToCalc, lvlToAssignToReferences);
        }

    }

    private void setLevels(PluginReference prToCalc, List<PluginReference> pluginReferenceList, Integer lvlToAssign) {

        Integer lvlToAssignToReferences = lvlToAssign + 1;

        if(pluginLevels.containsKey(prToCalc)) {

            Integer actualLvl = pluginLevels.get(prToCalc);

            if (actualLvl < lvlToAssign) {
                pluginLevels.put(prToCalc, lvlToAssign);
                assignToReferences(prToCalc, pluginReferenceList, lvlToAssignToReferences);
            } else if (actualLvl.equals(lvlToAssign)) {
                assignToReferences(prToCalc, pluginReferenceList, lvlToAssignToReferences);
            } else {
                lvlToAssignToReferences = actualLvl + 1;
                assignToReferences(prToCalc, pluginReferenceList, lvlToAssignToReferences);
            }

        } else {
            pluginLevels.put(prToCalc, lvlToAssign);
            assignToReferences(prToCalc, pluginReferenceList, lvlToAssignToReferences);
        }

    }

    private void assignToReferences(PluginReference prToCalc, Integer lvlToAssign) {
        List<PluginReference> refNeededList = pluginReferenceListMap.get(prToCalc);
        for (PluginReference refNeeded : refNeededList) {
            List<PluginReference> refNeededReferenceList = pluginReferenceListMap.get(refNeeded);
            setLevels(refNeeded, refNeededReferenceList, lvlToAssign);
        }
    }
    private void assignToReferences(PluginReference prToCalc, List<PluginReference> pluginReferenceList, Integer lvlToAssign) {
        for (PluginReference refNeeded : pluginReferenceList) {
            List<PluginReference> refNeededReferenceList = pluginReferenceListMap.get(refNeeded);
            if (compareReferences(prToCalc, refNeeded, refNeededReferenceList))
                setLevels(refNeeded, refNeededReferenceList, lvlToAssign);
            else
                System.out.println("Cyclic reference found.");
        }
    }

    // check looking for cyclic references
    private boolean compareReferences(PluginReference pr1, PluginReference pr2, List<PluginReference> pluginReferenceList) {
            for (PluginReference ref2 : pluginReferenceList) {
                if(pr1.equals(ref2)) {
                    System.out.println("Cyclic reference found between.- "+pr1 + " and " + pr2);
                    return false;
                }
            }
        return true;
    }
}
*/
