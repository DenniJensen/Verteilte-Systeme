package de.berlin.fu.vs.statisticserver.statisticevents;

import de.berlin.fu.vs.Wallet;

import java.io.Serializable;

/**
 * Created by dennish on 20/06/15.
 */
public class LeaveEvent implements Serializable {

  private final Wallet wallet;

  public LeaveEvent(Wallet wallet) {
    this.wallet = wallet;
  }

  public Wallet getWallet() {
    return wallet;
  }
}
