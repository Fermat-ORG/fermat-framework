package com.bitdubai.fermat_api.layer.dmp_identity.designer;

import com.bitdubai.fermat_api.layer.dmp_identity.designer.interfaces.DesignerManager;

/**
 * The Class <code>DealsWithDesigner</code>
 * indicates that the plugin needs the functionality of a DesignerManager
 * <p/>
 *
 * Created by natalia on 03/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface DealsWithDesigner {

    void setDesignerIdentityManager(DesignerManager designerIdentityManager);
}
