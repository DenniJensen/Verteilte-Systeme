package fucoin.events;

import fucoin.Wallet;

import java.io.Serializable;

public class SyncWallet implements Serializable {
  private final Wallet wallet;

  public SyncWallet(Wallet wallet) {
    this.wallet = wallet;
  }

  public Wallet getWallet() {
    return this.wallet;
  }
}
