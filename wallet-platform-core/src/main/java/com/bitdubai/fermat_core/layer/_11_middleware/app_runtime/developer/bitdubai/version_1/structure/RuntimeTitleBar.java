package com.bitdubai.fermat_core.layer._11_middleware.app_runtime.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer._11_middleware.app_runtime.TitleBar;

import java.awt.*;

/**
 * Created by ciencias on 2/14/15.
 */
public class RuntimeTitleBar implements TitleBar {

    String label;
    Color color; // Loui TODO: Implementar getter y setter y ponerlos en la interfaz
    Image backgroundImage; // Loui TODO: Implementar getter y setter y ponerlos en la interfaz
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
}
