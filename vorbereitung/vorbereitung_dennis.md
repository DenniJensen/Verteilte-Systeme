Why distributed systems?
* performance
* distributed inherent (share data)
* reliability (data duplication)
* incremental growth (scalability)
* sharing of resources
* communication

##Architecture
Dividable in:
* Interaction model (synchronous, async, messages,...)
* Failure model (node failure, link failure, message loss)
* Secrurity model

###System Architectures
####Centralized
* Client / Server
* most TCP/IP

####Decentralized
* vertical distribution(multi-tier systems)
  * different logic on different machines
* horizontal distribution
  * client or server on different Data
*unstructured p2p
*structured p2p
  * distributed hash tables


##Models of a computation
###Interleaving model
* run distributed computation is a global even on one universal timeline
* can define chronoligical order

###Happened-before model
* run or computation is a tuple
* partial order (two event may not be ordered)

###Potential causality model
* cause-effect relation (must be chronological)
* causality is a partial order within one process
  * if event e causes event f then e potentially causes f (e happened before f)

* potential causality relation is the smallest causality relation on the event
  set

##Logical Clocks
* uses in general happened-before relation
  * state based models are possible too
  * events cause state transaction
  * state includes program counter

###Vector clock


##Process and Thread
###Process
* execution of a program
* processor creates virtual processor for each program and stores it in a
  process table
* each virtual process has its own independent address space
* transparent sharing of resource(processor, memory)-separation
* process switch is expensive(save CPU context, registers, pointers)

###Thread
* several threads sharing CPU
* thread context has little memory information
* threads avoid blocking application
* thread switch is fast
* user level threads allows parallel computation
* I/O or other blocking systems calls block in all threads


###Virtualization

