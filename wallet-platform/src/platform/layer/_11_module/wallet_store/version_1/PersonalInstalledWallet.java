package platform.layer._11_module.wallet_store.version_1;

import platform.layer._9_network_service.review.version_1.Dislikeable;
import platform.layer._9_network_service.review.version_1.Likeable;
import platform.layer._9_network_service.review.version_1.Reviewable;
import platform.layer._10_middleware.wallet.developer.bitdubai.version_1.engine.license.License;

import java.util.List;

/**
 * Created by ciencias on 28.12.14.
 */
public class PersonalInstalledWallet implements  PersonalWallet, Reviewable, Likeable, Dislikeable {


    private List<License> mLicense;
}
