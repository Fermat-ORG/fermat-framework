package com.bitdubai.fermat_draft.layer._13_agent.licesnse.developer.bitdubai;

import com.bitdubai.fermat_api.layer._15_agent.AIAgent;
import com.bitdubai.fermat_api.layer._15_agent.AgentLayerDeveloper;
import com.bitdubai.fermat_api.layer._1_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer._1_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer._6_license.PluginLicensor;
import com.bitdubai.fermat_draft.layer._13_agent.licesnse.developer.bitdubai.version_1.LicenseAgent;

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
