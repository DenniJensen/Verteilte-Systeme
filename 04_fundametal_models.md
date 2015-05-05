##Fundametal Models(Conlouris)
* interactions model
* failure model
* security model

###Models of a Computation
Define dsitributed system is a loosly-coupledset of N processes{p1...,pn} and a
set of loss-free uni-directional channels. No shared memory. Assume
message-passing.

### 1. Interleaving model
  - Run of a distributet program is a globale sqquence of events. next(G,e) is
    the next state obtained after executing


####Def (Interleaving model) A sequence of events seq={e; 0<=i<=m}
is a computation of the systems in the inteerleaving odel if there exists a
sequence of global stats(G; 0 <= i <= m+1) such the G_0 is a initial state and
G_i+1 = next(G_i, e_i) for 0<=<=m
=> total (global) order

### 2. Happended before model

Interleaving model needs total order (there is a order between each pair of
events). Destributed ststem has only partial order(Laslie Laport)
=> happened before!

Each process P: generates a sequence of stated s_i0 e_i0, s_i1.......e_ie
Initial state of P: is s_i0: s_ij -e_ij---> s_ij+1

####Def Relation immediately precedes
e immediately precedes f in the sequence of events at P;
Also: next(e) = x of prev(f) = whenever

####Def.: relation locally precedes <
  < for irreflexive, transitive closure of <_im nd <= for reflexive, transitive closure of <_im
  
  Bsp.: P_i timeline e_1 e_2 e_3 e_4
        <_im = {(e_1, e_2), (e_2, e_3), (e_3, e_4)}
        <    = <_im vereint mit {(e_1, e_3), (e_1, e_4), (e_2, e_4)}
        <=   = < vereint mit {(e_1, e_1), (e_2, e_2), (e_3, e_3), (e_4, e_4)}
  
####Def.: e.p 
 * event e occurs in process p
  
####Def.: relation remotly precedes ~>
  e ~> f if e is the send event at process P_i and f the receive event of the same message t P_j similary state s and t are related by ~> iff a message is send after state s that is received by the receiver, resulting state t
  
####Def.: Happend before
  The happend before relation -> is the smallest relation that satisfies
  
  1. (e <_im f) v (e ~>f) => (e -> f)
  2. it exists g (e -> g) and (g -> f) => e -> f

  A run or computation is a tuple (E, ->) of events E and -> is a patial order such that all events in  pairs are totally ordered
  
  e and f are concurrent if not(e -> f) and not(f -> e)
  
###3. Potential causality model
Happed before introduces total order in a process (Potential causality does not)
  
More interesting is cause and effect relation
  
Causality relation is partial order within a process
  - if event e cause f, then e potential causes f

Note that happend before is a potential causality model
  - if e causes f then e happend before f

####Def.: (potential causlity relation)
is the smalltest causality relation on the event set that satisfies
  - if a event e potentially causes another event f on the same process then e p-> f
  - if e is the send event of a message and f is the corresponding receive eventt then e p-> f
  - if e p-> f and f p-> g then e p-> g
   
Events e,f are independent if not(e p->f) and not(f p-> e)
Given (E, p->) a happend before diagram is consistant with if if p-> Teilmenge von ->