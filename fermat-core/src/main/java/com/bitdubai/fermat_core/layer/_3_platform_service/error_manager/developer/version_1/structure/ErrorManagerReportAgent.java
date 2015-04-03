package com.bitdubai.fermat_core.layer._3_platform_service.error_manager.developer.version_1.structure;

import com.bitdubai.fermat_core.layer._3_platform_service.error_manager.developer.version_1.exceptions.CantStartAgentException;
import com.bitdubai.fermat_core.layer._3_platform_service.error_manager.developer.version_1.interfaces.ErrorAgent;

/**
 * Created by ciencias on 4/3/15.
 */


/**
 * Esta clase lo que hace es leer el arvhivo con las ultimas excepciones de cada Developer, y luego determina si en algun 
 * caso hay necesidad de transmitir la informacion al Developer.
 * 
 * Si la hay intenta la transmision y si lo logra elimina la informacion transmitida.
 * 
 * 
 * 
 * * * * * * * * .
 */
public class ErrorManagerReportAgent implements ErrorAgent{

    @Override
    public void start() throws CantStartAgentException {
        
    }

    @Override
    public void stop() {

    }
}
