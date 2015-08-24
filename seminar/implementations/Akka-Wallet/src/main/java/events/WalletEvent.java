package events;

import java.io.Serializable;

/**
 * Created by dennish on 15/05/15.
 */
public class WalletEvent implements Serializable {

  private int euro;
  private int cent;

  public WalletEvent(int euro, int cent) {
    this.euro = euro;
    this.cent = cent;
  }

  public int getEuro() {
    return euro;
  }

  public void setEuro(int euro) {
    this.euro = euro;
  }

  public int getCent() {
    return cent;
  }

  public void setCent(int cent) {
    this.cent = cent;
  }
}
