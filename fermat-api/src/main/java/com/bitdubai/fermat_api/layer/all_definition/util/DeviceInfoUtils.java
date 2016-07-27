package com.bitdubai.fermat_api.layer.all_definition.util;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;

/**
 * Created by Matias Furszyfer on 2015.08.06..
 */
public class DeviceInfoUtils {

    public static ScreenSize toScreenSize(float height, float widht) {
        ScreenSize screenSize = null;
        if (height <= 960 && widht <= 720 && height > 640 && widht > 480) {
            screenSize = ScreenSize.XLARGE;
        } else if (height <= 640 && widht <= 720 && height > 640 && widht > 480) {
            screenSize = ScreenSize.LARGE;
        } else if (height <= 960 && widht <= 480 && height > 470 && widht > 320) {
            screenSize = ScreenSize.MEDIUM;
        } else if (height <= 470 && widht <= 320 && height > 240 && widht > 240) {
            screenSize = ScreenSize.SMALL;
        } else if (height == 240 && widht == 240) {
            screenSize = ScreenSize.XSMALL;
        }
        return screenSize;
    }

}
