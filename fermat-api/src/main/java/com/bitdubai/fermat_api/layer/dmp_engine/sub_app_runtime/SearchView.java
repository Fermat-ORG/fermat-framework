package com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime;

import java.awt.Image;

/**
 * Created by MATIAS FURSZYFER.
 */
public interface SearchView {

    public String getText();

    public Image getIcon() ;

    public void setIcon(Image icon);

    public void setLabel(String label);

    public String getLabel();

    
}
