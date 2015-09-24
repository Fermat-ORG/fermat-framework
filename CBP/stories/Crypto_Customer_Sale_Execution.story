Historia: Crypto Customer Sale Execution

Como deseo poder finalizar la compra establecida en la negociación con un Crypto Customer
 Como un Crypto Broker

Utilizando la Crypto Broker Wallet
  Y el plugin Receive Crypto Crypto Transaction
  Y el plugin Send Crypto Crypto Transaction
  Y el plugin Give Cash On Hand Fiat Cash Transaction
  Y el plugin Send Cash Delivery Fiat Cash Transaction
  Y el plugin Crypto Broker Market Crypto Wallet
  Y el plugin Crypto Broker Fiat Cash Wallet
  Y el plugin Crypto Customer Market Money Sale
  Y el plugin Crypto Customer Fiat Money Sale
  Y el plugin Receive Fiat Cash On Hand Fiat Cash Transaction
  Y el plugin Receive Fiat Cash Delivery Fiat Cash Transaction
  Y el plugin Receive Offline Fiat Bank Transfer Fiat Bank Transaction

Escenario: Negociacion ha pasado la Fecha del Vencimiento de Pago
  Dado que ya se cerro una negociacion con el Crypto Broker
    Y el estado de la Negociacion es PENDIENTE_PAGO
  Cuando la fecha ha pasado la Fecha de Vencimiento de Pago establecida en la Negociacion
  Entonces el "Sale Negotation Contract plugin" debe cambiar el estado a NEGOCIACION
    Y se registra esta actualizacion en el Log Transaccional del "Sale Negotation Contract plugin"
    Y se envia una notificacion de cambio de estado a NEGOCIACION a traves de la Crypto Customer Network Service
|!Sale Negotation Contract plugin           |
|Crypto Customer Market Money Sale Contract |
|Crypto Customer Fiat Money Sale Contract   |

Escenario: Crypto Broker recibe Market Crypto del Crypto Customer.
  Dado que ya se cerro una negociacion con el Crypto Customer
    Y se establecio que el modo de pago va a ser una transferencia de Market Crypto
    Y el Crypto Customer envio la Crypto Currency que se acepto como pago
  Cuando el Receive Crypto Crypto Transaction registre un pago a la direccion de la operacion
  Entonces Receive Crypto Crypto Transaction debe notificar a la Crypto Broker Market Crypto Wallet de la transaccion
    Y la Crypto Broker Market Crypto Wallet debe registrar la transaccion como un credito
    Y la Crypto Broker Market Crypto Wallet debe actualizar su balance
    Y el "Sale Negotiation Contract" debe recibir la notificacion de la transaccion
    Y el "Sale Negotiation Contract" verifica que el monto de la transaccion sea el monto acordado
    Y el "Sale Negotation Contract" actualiza su estado como PENDIENTE_ENTREGA
    Y se registra esta actualizacion en el Log Transaccional del "Sale Negotiation Contract"
Contratos:
|!Sale Negotation Contract          |
|Crypto Customer Market Money Sale  |
|Crypto Customer Fiat Money Sale    |

Escenario: Crypto Broker envia el Market Crypto al Crypto Customer.
  Dado que ya se cerro una negociacion con el Crypto Customer
    Y se establecio que la mercaderia a entregar va a ser Market Crypto
  Cuando el Crypto Broker envia la mercaderia a traves de la Crypto Broker Wallet
  Entonces la Crypto Broker Wallet debe registrar el envio usando el plugin Send Crypto Crypto Transaction
    Y la Crypto Broker Market Crypto Wallet debe debitar el monto del balance
    Y la Crypto Broker Wallet actualiza el "Sale Negotiation Contract" como ENTREGADO
    Y se registra esta actualizacion en el Log Transaccional del "Sale Negotiation Contract"

Escenario: Crypto Broker recibe pago utilizando Fiat Cash del Crypto Customer
  Dado que ya se cerro una negociacion con el Crypto Customer
    Y se establecio que el modo de pago va a ser Fiat Cash
    Y se especificaron las condiciones del "Tipo de Pago"
  Cuando el Crypto Broker procede a ingresar la informacion del pago en la Crypto Broker Wallet
  Entonces la Crypto Broker Wallet debe registrar el pago usando una "Fiat Cash Transaction"
    Y la "Fiat Cash Transaction" debe registrar un credito en la Crypto Broker Fiat Cash Wallet
    Y la Crypto Broker Wallet actualiza el estado  del "Sale Negotiation Contract" como PENDIENTE_ENTREGA
    Y se registra esta actualizacion en el Log Transaccional del "Sale Negotiation Contract"
Permutacion:
|!Tipo Pago             |!Fiat Cash Transaction                             |
|Cash On Hand           |Receive Fiat Cash On Hand Fiat Cash Transaction    |
|Cash Delivery          |Receive Fiat Cash Delivery Fiat Cash Transaction   |

Escenario: Crypto Broker recibe pago utilizando Fiat Bank del Crypto Customer
  Dado que ya se cerro una negociacion con el Crypto Customer
    Y se establecio que el modo de pago va a ser Fiat Bank
  Cuando el Crypto Broker procede a ingresar la informacion del pago en la Crypto Broker Wallet
  Entonces la Crypto Broker Wallet debe registrar el pago a traves del plugin Receive Offline Fiat Bank Transfer Fiat Bank Transaction
    Y la Receive Offline Fiat Bank Transfer Fiat Bank Transaction debe registrar un credito en la Crypto Broker Fiat Cash Wallet
    Y el "Sale Negotiation Contract" actualiza su estado como PENDIENTE_ENTREGA
    Y se registra esta actualizacion en el Log Transaccional del "Sale Negotiation Contract"

Escenario: Crypto Broker envio de mercancia Fiat Cash                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 currency del Crypto Customer.
  Dado que ya se cerro una negociacion con el Crypto Customer
    Y se establecio que la mercaderia se va a entregar por Cash on Hand o Cash Delivery
    Y hago entrega de la mercaderia
  Cuando procedo a registrar el envio de la mercaderia en la Crypto Broker Wallet
  Entonces la Crypto Broker Wallet debe registrar el envio usando el "Plugin de Envio Cash"
    Y el "Plugin de Envio Cash" debe registrar un debito en la Crypto Broker Fiat Cash Wallet
    Y el "Sale Negotiation Contract" actualiza su estado como ENTREGADO
    Y se registra esta actualizacion en el Log Transaccional del "Sale Negotiation Contract"
    Y el "Sale Negotiation Contract" envia la notificacion de cambio de estado a ENTREGADO a traves del plugin Crypto Customer Network Service
|!Plugin de Envio Cash                    |
|Give Cash On Hand Fiat Cash Transaction  |
|Send Cash Delivery Fiat Cash Transaction |

Escenario: Crypto Broker envio mercaderia a Fiat Bank                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          currency del Crypto Customer.
  Dado que ya se cerro una negociacion con el Crypto Customer
    Y se establecio que la mercaderia se va a entregar por Bank Deposit
  Cuando procedo a registrar el Bank Transfer en la Crypto Broker Wallet
  Entonces la Crypto Broker Wallet debe registrar el envio usando el plugin Make Offline Bank Transfer Fiat Bank Transaction
    Y la Make Offline Bank Transfer Fiat Bank Transaction debe realizar un debito en la cuenta registrada en el Crypto Broker Fiat Bank Wallet
    Y el "Sale Negotiation Contract" actualiza su estado como ENTREGADO
    Y se registra esta actualizacion en el Log Transaccional del "Sale Negotiation Contract"
    Y el "Sale Negotiation Contract" envia la notificacion de cambio de estado a ENTREGADO a traves del plugin Crypto Customer Network Service

Escenario: Negociacion con Mercaderia Entregada no es confirmada por el Crypto Customer
  Dado que ya se cerro una negociacion con el Crypto Broker
    Y el estado de la Negociacion es ENTREGADO
  Cuando la fecha ha pasado la Fecha de Entrega de Mercaderia establecida en la Negociacion
  Entonces el "Sale Negotation Contract plugin" debe cambiar el estado a COMPLETADO_SIN_CONFIRMACION
    Y se registra esta actualizacion en el Log Transaccional del "Sale Negotation Contract plugin"
    Y se envia una notificacion de estado COMPLETADO_SIN_CONFIRMACION a traves de la Crypto Customer Network Service
|!Sale Negotation Contract plugin           |
|Crypto Customer Market Money Sale Contract |
|Crypto Customer Fiat Money Sale Contract   |
