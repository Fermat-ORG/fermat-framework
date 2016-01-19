package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.MatchingEngineManager</code>
 * provide all the published methods of the matching engine middleware cbp plug-in.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/01/2016.
 */
public interface MatchingEngineManager {

    /**
     * Tiene que monitorear la existencia de nuevas crypto brokers wallets, o enterarse de alguna forma (capaz directamente que se le vayan asignando).
     * Tiene que poder notificar si algún par manejado por la wallet no tiene wallet definida para recibir ganancias (salvo que eso sea excluyente para arriba) igual debe ser validado.
     * Tiene que comparar por cada par de monedas, todas las transacciones hechas y como si fueran los inputs y outputs de blockchain ir uniendo unos con otros y dejando cantidades
     * adentro o afuera para ir calculando las ganancias.
     *
     * Para el cálculo de ganancias se tienen en cuenta los siguientes parámetros o valores por cada par de monedas:
     *
     * Par de Monedas: Example: (BTC-USD)
     * - Stocks manejados por cada par.
     *    . La idea de que sepa cuales son los stocks del par de monedas es para evitar que saque ganancias de una moneda que está por debajo del nivel de stock que tendría que tener
     *      debido a que esto haría que el broker se quede más rápidamente sin stock.
     *    . Capaz tendríamos que definir acá también un porcentaje modificable (aunque puede tener por defecto alo) que defina cuáles serían los márgenes que habría que dejar de stock.
     *      Por ejemplo si el stock es de 1000 USD que se pueda extraer sólo si hay más de un 90%, o sea si hay más de 900 USD que se pueda extraer ganancias.
     * - Moneda de la que se va a extraer la ganancia y wallet a la cual se va a dirigir.
     *
     * El proceso que va a correr revisando las transacciones tiene que irlas manejando como los inputs y outputs de blockchain.
     *
     *  - Por cada transacción nueva que reciba de un par la va a generar como un input.
     *  - Cuando exista una transacción del mismo par para contrarrestarla o sea de la contraparte si antes era USD-BTC sería BTC-USD. tiene que unir las transacciones y contrastarlas:
     *    . Para contrastar estas transacciones deberá tener en cuenta la moneda de la cual se van a sacar ganancias, o sea, si la ganancia se va a sacar de los BTC vamos a tener en
     *      cuenta la otra moneda USD para contrastar, o sea, cuando se vendan 100 USD y después se compren 100 USD la diferencia que haya en los valores de BTC sería pérdida o ganancia.
     *      Example: sale: 100 USD entra 1.1 BTC
     *               entra:100 USD sale  1.0 BTC
     *               ganancia            0.1 BTC
     *
     *  - Se podría decir entonces que tenemos inputs de m1 (moneda1) y m2 (moneda2), y los outputs que se van a ir generando podrían ser outputs "gastados" o outputs "sin gastar"
     *    que se tomarían como inputs a la hora de seguir haciendo los matchings.
     *
     *  - En caso de que las transacciones no se puedan matchear completas, habraá que generar un output, a matchearse en el próximo punto.
     *    . Para estos casos hay que tener en cuenta de que la moneda sobre la cual se van a sacar las ganancias puede ir cambiando a lo largo del tiempo.
     *
     * ??? - En el caso en el cual se determine que hubo pérdidas, el proceso debería registrarlas, y no sacar ganancias hasta que sean cubiertas?
     *
     * tener en cuenta que esto tendría que poder ser reutilizado por otros plug-ins y no solo los crypto brokers
     */

}
