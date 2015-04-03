package com.bitdubai.fermat_core.layer._3_platform_service.error_manager.developer.version_1.structure;

/**
 * Created by ciencias on 4/3/15.
 */


/**
 * Esta clase maneja un archivo con las excepciones recibidas x cada developer. Siempre agrega informacion al archivo.
 * No mantiene el arvhico abierto porque no sabe la frecuencia con la que recibira nuevas excepciones y ademas, porque
 * el Report Agent puede llegar a renombrarlo una vez que logre transmitirlo al developer.
 * 
 * Entonces en el fondo, lo que maneja son los archivos aun no transmitidos.
 * 
 * 
 * Tambien maneja un segundo archivo donde lista el plugin, la severidad declarada , para que el agente decida en base a 
 * eso cuando debe transmitir la informacion al Developer.
 * * *
 * * * * * 
 * * * * * * 
 */
public class ErrorManagerRegistry {
    
    

    
}
