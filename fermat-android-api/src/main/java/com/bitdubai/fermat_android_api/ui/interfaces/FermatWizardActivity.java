package com.bitdubai.fermat_android_api.ui.interfaces;

import java.util.Map;

/**
 * Fermat Wizard Activity
 */
public interface FermatWizardActivity {

    /**
     * Get Parent Data
     *
     * @return HasMap<String, Object>
     */
    Map<String, Object> getData();

    /**
     * Put Object data in parent Reference
     *
     * @param key  String key name
     * @param data data to save
     */
    void putData(String key, Object data);

    /**
     * put all HasMap to the existing object data
     *
     * @param data HasMap<String, Object>
     */
    void putData(Map<String, Object> data);

    /**
     * Set Complete Data
     *
     * @param data HasMap<String, Object>
     */
    void setData(Map<String, Object> data);

    /**
     * Set Title to this activity
     *
     * @param title CharSequence ActionBar Title
     */
    void setWizardActivity(CharSequence title);


}
