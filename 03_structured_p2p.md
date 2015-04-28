##Structured P2P architectures
* use mostly DHT-schemes (DHT = Destributed Hash Tables)
* chord system arranges items in a ring
* data item k is assigned to node with smallest identifier id > k
  - i.e. item 1 is a node
  - item 2 belongs to node 4
* for each item k, such(k) = id returns the name of the node k is assigned to
* to find data item k the function LookUP(k) returns the address of succ(k)
* membership management:
  - join:
    ```
    create SHA1 identifier id
    Lookup(id) = succ(id)
    contact succ(id) and pred(id) to join ring
    ```
  - leave: node id information succ(id) and assigns its data items to succ(id)


###Content-Adressable-Network(CAN)
* d-dimensional cartesion coordinates
* every node draws coordinates
* space is dicided among nodes (random)
* join: slect closest point
  - half the rectangle in which it falls
  - assign two centres
  - assign data items to centres
* leace the network:
  - one node takes over the rectangle
  - joining rectangles gibes no rectagle(in general)

##Unstructured P2P architectures
* random graph
* each node maintains a list of c neighbours
* *partial new* is neighbours list with age of neighbour relation
  Jelasity(2004/2005)
* nodes exchange neighbor information
* gissip-based dissemination
* epidemic algorithms (SIR-models)
  - s - susceptibles
  - i - infected
  - r - recorered/removed

### anti-entropy
active thread vs  passive thread
####active thread
- select peer
- PUSH
  * select c/2 youngest entries into buffer + myself
  * send buffer to peer
- PULL
  * receive peer's buffer
  * construct new partial view
  * increment age of all items

####passive thread
- receive buffer from peer
- PULL
  * select c/2 youngest entries into buffer + myself
  * send buffer to peer
  * construct new partial
  * increment age

###Remarks
* only PUSH or only PULL leads to disconnected network easily
* items must be exchanged(gossip)
* removing olf nodes leads to forgotten nodes
* systematically removing old nodes increases indegree(popularity) of some node
  - leads to imbalance

**NOTES**
In PUSH approach updates are only distributed by infected nodes
* if many are infected, spread is slow
* PULL works better if few are infected
* spread is triggered by susceptibles
* high probability that susceptible will pull from infected
