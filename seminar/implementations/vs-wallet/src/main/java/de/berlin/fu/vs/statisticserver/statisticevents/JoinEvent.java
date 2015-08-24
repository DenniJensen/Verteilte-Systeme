package de.berlin.fu.vs.statisticserver.statisticevents;

import de.berlin.fu.vs.Wallet;

import java.io.Serializable;

/**
 * Created by dennish on 20/06/15.
 */
public class JoinEvent implements Serializable {

  private final de.berlin.fu.vs.Wallet wallet;

  public JoinEvent(de.berlin.fu.vs.Wallet wallet) {
    this.wallet = wallet;
  }

  public de.berlin.fu.vs.Wallet getWallet() {
    return wallet;
  }
}
