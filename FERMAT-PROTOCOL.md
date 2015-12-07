![alt text](https://github.com/bitDubai/media-kit/blob/master/Readme%20Image/Fermat%20Logotype/Fermat_Logo_3D.png "Fermat Logo")

#--draft--

# Fermat Protocol

The Fermat Protocol is an open standard meaning that can be freely adopted, implemented and extended. The protocol defines how both Fermat nodes and clients should behave.  

We expect the protocol to evolve through time to address miss behaviours and mitigate attacks. The protocol may be adapted in response to proposed improvements and market feedback but all changes must be decided by consensus of its users.

<br>
## Fermat Protocol Version 1.0

The Fermat Protocol spans through Fermat Nodes and Fermat Clients. It defines the internal rules on each of them and also the rules of engagement between each other.

### Fermat P2P Protocol

#### Network Discovery

##### New Fermat Node

When a new node boots up for the first time, it must discover other Fermat nodes on the network in order to participate. To start this process, a new node must discover at least one existing node on the network and connect to it. The geographic location of other nodes is relevant; the Fermat network topology is geographically defined. But for the purpose of bootstraping, any existing Fermat node can be selected at random.

A bootstrapping node that knows nothing of the network must be given the IP address of at least one Fermat node, after which it can establish connections through further introductions. The Fermat Node reference implementation contains the IPs and port numbers of several different stable Fermat Node seeds.

Any of these IPs can be used to connect to one node just for introductions, using it as a seed. After the initial seed node is used to form introductions, the bootstrapping node will disconnect from it and use the newly discovered peers.

##### Old Fermat Node

To connect to a known peer, nodes establish a TCP connection, usually to port 9444 (the port generally known as the one used by Fermat), or an alternative port if one is provided. Upon establishing a connection, the node will start a "handshake".

##### Node to Node Dialogue 1: Node to Node Connection

| Node A        | Node B         | Notes  |
|:-------------:|:-------------:|:-----|
| Version       |               | Node A sends _Version_  message to Node B.|
|               | Version ACK   | Node B answers with _Version ACK_  message to Node A.|



##### Message Definitions

###### Message: Version

| Field        | Size          | Example  | Notes  |
|:-------------:|-------------:|:---------|:-------|
| Version       |               | FP2P v.1.0.0 | A constant that defines the Fermat P2P Protocol version the client node "speaks".|
| Implementation   |               | bitDubai v.1.0.0 | A constant that defines the name and version of the implementation the client node is running.|
| Your Address  |               | 123.32.45.31 | The IP address of the remote node as seen from this node.|
| My Address    |               | 111.44.15.1  | The IP address of the local node, as discovered by the local node.|


