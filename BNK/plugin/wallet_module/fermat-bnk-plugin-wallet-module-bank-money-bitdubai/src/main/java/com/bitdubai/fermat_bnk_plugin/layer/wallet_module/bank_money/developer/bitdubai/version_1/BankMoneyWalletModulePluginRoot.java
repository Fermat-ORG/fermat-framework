package com.bitdubai.fermat_bnk_plugin.layer.wallet_module.bank_money.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by memo on 04/12/15.
 */
public class BankMoneyWalletModulePluginRoot extends AbstractPlugin implements LogManagerForDevelopers {

    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    public BankMoneyWalletModulePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public List<String> getClassesFullPath() {
        return null;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {

        // I will check the current values and update the LogLevel in those which is different
        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {

            // if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
            if (BankMoneyWalletModulePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                BankMoneyWalletModulePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                BankMoneyWalletModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                BankMoneyWalletModulePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }
}
