package com.bitdubai.fermat_core.layer._12_middleware.app_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._12_middleware.app_runtime.TitleBar;

import java.awt.*;

/**
 * Created by ciencias on 2/14/15.
 */
public class RuntimeTitleBar implements TitleBar {

    String label;
    Color color; 
    Image backgroundImage; 
    
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}
