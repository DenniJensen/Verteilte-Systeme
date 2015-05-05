##Fundametal Models(Conlouris)
* interactions model
* failure model
* security model

###Models of a Computation
Define dsitributed system is a loosly-coupledset of N processes{p1...,pn} and a
set of loss-free uni-directional channels. No shared memory. Assume
message-passing.

1. Interleaving model
  - Run of a distributet program is a globale sqquence of events. next(G,e) is
    the next state obtained after executing


####Def (Interleaving model) A sequence of events seq={e; 0<=i<=m}
is a computation of the systems in the inteerleaving odel if there exists a
sequence of global stats(G; 0 <= i <= m+1) such the G_0 is a initial state and
G_i+1 = next(G_i, e_i) for 0<=<=m
=> total (global) order

