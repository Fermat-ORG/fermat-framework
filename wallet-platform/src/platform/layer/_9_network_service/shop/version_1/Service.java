package platform.layer._9_network_service.shop.version_1;

import platform.layer._9_network_service.review.version_1.Dislikeable;
import platform.layer._9_network_service.review.version_1.Likeable;
import platform.layer._9_network_service.review.version_1.Reviewable;

/**
 * Created by ciencias on 20.12.14.
 */
public class Service implements Sellable, Likeable, Dislikeable, Reviewable {
    private double mPrice;
    private String mName;
    private String mDescription;
    private String mPicture;

}
