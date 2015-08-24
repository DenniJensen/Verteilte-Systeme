package de.berlin.fu.vs.statisticserver.statisticevents;

import java.io.Serializable;

/**
 * Created by dennish on 20/06/15.
 */
public class TransactionEvent implements Serializable {

  private final String source;
  private final String target;
  private final int amount;

  public TransactionEvent(String source, String target, int amount) {
    this.source = source;
    this.target = target;
    this.amount = amount;
  }

  public String getSource() {
    return source;
  }

  public String getTarget() {
    return target;
  }

  public int getAmount() {
    return amount;
  }
}
