package unit.com.bitdubai.fermat_core.reference_injection_logic_test;

import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatPluginsEnum;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_ccp_api.all_definition.enums.CCPPlugins;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lnacosta (laion.cj91@gmail.com) on 21/10/2015.
 */
public class LogicTest {

    private Map<PluginReference, Integer> pluginLevels = new HashMap<>();

    private Map<PluginReference, List<PluginReference>> pluginReferenceListMap;

    private PluginReference buildPR(FermatPluginsEnum e){
        return new PluginReference(e, new Version("1.0.0"));
    }

    private List<PluginReference> buildRL(PluginReference pr, PluginReference...refs) {
        return Arrays.asList(refs);
    }

    private void chargeGoodTestingData() {
        pluginReferenceListMap = new HashMap<>();

        PluginReference basicWallet = buildPR(CCPPlugins.BITCOIN_WALLET_BASIC_WALLET);
        PluginReference cryptoWallet = buildPR(CCPPlugins.CRYPTO_WALLET_MODULE);
        PluginReference intraActor = buildPR(CCPPlugins.INTRA_WALLET_USER_ACTOR);
        PluginReference intraIdentity = buildPR(CCPPlugins.INTRA_WALLET_USER_IDENTITY);

        List<PluginReference> basicWalletRefs = buildRL(
                basicWallet,
                buildPR(CCPPlugins.CRYPTO_WALLET_MODULE),
                buildPR(CCPPlugins.INTRA_WALLET_USER_ACTOR),
                buildPR(CCPPlugins.INTRA_WALLET_USER_IDENTITY)
        );

        pluginReferenceListMap.put(
                basicWallet,
                basicWalletRefs
        );

        List<PluginReference> cryptoWalletRefs = buildRL(
                cryptoWallet,
                buildPR(CCPPlugins.INTRA_WALLET_USER_ACTOR),
                buildPR(CCPPlugins.INTRA_WALLET_USER_IDENTITY)
        );

        pluginReferenceListMap.put(
                cryptoWallet,
                cryptoWalletRefs
        );

        List<PluginReference> intraActorRefs = buildRL(
                intraActor,
                buildPR(CCPPlugins.INTRA_WALLET_USER_IDENTITY)
        );

        pluginReferenceListMap.put(
                intraActor,
                intraActorRefs
        );

        List<PluginReference> intraIdentityRefs = buildRL(
                intraIdentity,
                buildPR(CCPPlugins.EXTRA_WALLET_USER_ACTOR),
                buildPR(CCPPlugins.WALLET_CONTACTS_MIDDLEWARE)
        );

        pluginReferenceListMap.put(
                intraIdentity,
                intraIdentityRefs
        );

    }

    private void chargeBadTestingData() {
        pluginReferenceListMap = new HashMap<>();
        PluginReference basicWallet = buildPR(CCPPlugins.BITCOIN_WALLET_BASIC_WALLET);
        PluginReference cryptoWallet = buildPR(CCPPlugins.CRYPTO_WALLET_MODULE);
        PluginReference intraActor = buildPR(CCPPlugins.INTRA_WALLET_USER_ACTOR);
        PluginReference intraIdentity = buildPR(CCPPlugins.INTRA_WALLET_USER_IDENTITY);

        List<PluginReference> basicWalletRefs = buildRL(
                basicWallet,
                buildPR(CCPPlugins.CRYPTO_WALLET_MODULE),
                buildPR(CCPPlugins.INTRA_WALLET_USER_ACTOR),
                buildPR(CCPPlugins.INTRA_WALLET_USER_IDENTITY)
        );

        pluginReferenceListMap.put(
                basicWallet,
                basicWalletRefs
        );

        List<PluginReference> cryptoWalletRefs = buildRL(
                cryptoWallet,
                buildPR(CCPPlugins.INTRA_WALLET_USER_ACTOR),
                buildPR(CCPPlugins.INTRA_WALLET_USER_IDENTITY)
        );

        pluginReferenceListMap.put(
                cryptoWallet,
                cryptoWalletRefs
        );

        List<PluginReference> intraActorRefs = buildRL(
                intraActor,
                buildPR(CCPPlugins.INTRA_WALLET_USER_IDENTITY)
        );

        pluginReferenceListMap.put(
                intraActor,
                intraActorRefs
        );

        List<PluginReference> intraIdentityRefs = buildRL(
                intraIdentity,
                buildPR(CCPPlugins.EXTRA_WALLET_USER_ACTOR),
                buildPR(CCPPlugins.WALLET_CONTACTS_MIDDLEWARE)
        );

        pluginReferenceListMap.put(
                intraIdentity,
                intraIdentityRefs
        );

    }

    @Test
    public void SimpleConstruction_ValidParameters_NotNull(){

        chargeGoodTestingData();

        PluginReference pluginNeeded = buildPR(CCPPlugins.BITCOIN_WALLET_BASIC_WALLET);

        setLevels(pluginNeeded, 1);

        for( Map.Entry<PluginReference, Integer> entry : pluginLevels.entrySet() ) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }

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

    private void assignToReferences(PluginReference prToCalc, Integer lvlToAssign) {
        List<PluginReference> refNeededList = pluginReferenceListMap.get(prToCalc);
        for (PluginReference refNeeded : refNeededList) {
            setLevels(refNeeded, lvlToAssign);
        }
    }
}
