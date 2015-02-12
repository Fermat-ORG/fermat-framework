package com.bitdubai.wallet_platform_draft.layer._12_agent.licesnse.developer.bitdubai;

import com.bitdubai.wallet_platform_api.layer._12_agent.AIAgent;
import com.bitdubai.wallet_platform_api.layer._12_agent.AgentLayerDeveloper;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.TimeFrequency;
import com.bitdubai.wallet_platform_api.layer._5_license.PluginLicensor;
import com.bitdubai.wallet_platform_draft.layer._12_agent.licesnse.developer.bitdubai.version_1.LicenseAgent;

/**
 * Created by ciencias on 21.01.15.
 */
public class DeveloperBitDubai implements AgentLayerDeveloper, PluginLicensor {

    AIAgent mAIAgent;

    @Override
    public AIAgent getAIAgent() {
        return mAIAgent;
    }

    public DeveloperBitDubai () {

        /**
         * I will choose from the different versions of my implementations which one to start. Now there is only one, so
         * it is easy to choose.
         */

        mAIAgent = new LicenseAgent();

    }

    @Override
    public int getAmountToPay() {
        return 100;
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return CryptoCurrency.BITCOIN;
    }

    @Override
    public String getAddress() {
        return "13gpMizSNvQCbJzAPyGCUnfUGqFD8ryzcv";
    }

    @Override
    public TimeFrequency getTimePeriod() {
        return TimeFrequency.MONTHLY;
    }
}
