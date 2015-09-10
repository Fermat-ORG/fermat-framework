#Fermat Messaging Protocol Specification v0.1.0

This protocol provides the standard communication structure of messages to be exchanged in the Communication Layer of the Fermat platform.

Every FMP compliant packet must contain the following three elements:

1. Sender : the unique identification of the sender
2. Action : this is the purpose identification of the packet
3. Message : the content of the fermatMessage to be interpreted according to the action

#The FMP interface

This interface defines the components of the FMP compliant methods and elements that are supported by the NIOServerChannel objects. It contains the following elements.

##Constants

+ **int** *PACKET_MAX_BYTE_SIZE* : the maximum size of a packet in bytes
+ **String** *PACKET_SEPARATOR* : this is the elements separator in the string conversion of the FMP packet (needed for serialization)
+ **int** *PACKET_SEPARATOR_PARTS* : the number of parts in which a String packet should convert once it's split using the *PACKET_SEPARATOR*

##Methods

+ **UUID** *getSender()* : returns the UUID associated to the Sender element
+ **FMPAction** *getAction()* : returns the Action element using the **FMPAction** enum
+ **FMPMessage** *getMessage()* : return the Message inside an implementation of the **FMPMessage** interface

##enum FMPAction

The different Actions that an FMP compliant packet and an FMP handler should support.

###List of FMPActions
+ **INTRAUSER_CONNECTION_REGISTER**
+ **INTRAUSER_CONNECTION_DEREGISTER**
+ **INTRAUSER_CONNECTION_ACCEPT**
+ **INTRAUSER_CONNECTION_DENY**
+ **INTRAUSER_CONNECTION_END**
+ **INTRAUSER_CALL_REQUEST**
+ **INTRAUSER_CALL_RECEIVE**
+ **INTRAUSER_CALL_ACCEPT**
+ **INTRAUSER_CALL_DENY**
+ **INTRAUSER_CALL_PROVIDE**
+ **INTRAUSER_CALL_REJECT**
+ **INTRAUSER_CALL_TRANSMIT**

##interface FMPMessage

The interface that specifies the content of an FMP compliant fermatMessage, each fermatMessage should vary according to the Action registered.

#interface FMPIntraUserServerHandler

The interface that defines the methods associated with the Actions associated with IntraUser calls that a FMP server can receive from a FMP client.

#interface FMPIntraUserClientHandler 

The interface that defines the methods associated with the Actions associated with IntraUser calls that a FMP client can receive from a FMP server.

#Road to Version 1

1. Specify all the possible actions
2. Implementation of an FMP compliant packet class
3. Define the different types of messages according to actions
4. Create the FMPHandler interface

---
Authors
=======
- Jorge Gonzalez - *github: jorgeejgonzalez - email: jorgeejgonzalez@gmail.com - skype: lolosky29@hotmail.com* 
