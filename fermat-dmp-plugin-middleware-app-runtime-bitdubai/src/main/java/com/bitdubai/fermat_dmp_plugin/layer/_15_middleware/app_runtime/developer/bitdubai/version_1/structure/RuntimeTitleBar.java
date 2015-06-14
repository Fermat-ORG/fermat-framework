package com.bitdubai.fermat_dmp_plugin.layer._15_middleware.app_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.SearchView;
import com.bitdubai.fermat_api.layer._15_middleware.app_runtime.TitleBar;

import java.awt.*;

/**
 * Created by Matias
 */
public class RuntimeTitleBar implements TitleBar {

    String label;
    Color color;
    Image backgroundImage;



    SearchView runtimeSearchView;

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

    public void setRuntimeSearchView(SearchView runtimeSearchView){this.runtimeSearchView=runtimeSearchView;};
    public SearchView getRuntimeSearchView() {
        if(runtimeSearchView!=null){
            return runtimeSearchView;
        }
        return null;
    }
}
