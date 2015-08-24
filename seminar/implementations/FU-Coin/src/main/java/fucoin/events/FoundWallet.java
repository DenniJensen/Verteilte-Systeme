package fucoin.events;

import fucoin.Wallet;

import java.io.Serializable;

public class FoundWallet implements Serializable {
  private final Wallet wallet;

  public FoundWallet(Wallet wallet) {
    this.wallet = wallet;
  }

  public Wallet getWallet() {
    return wallet;
  }
}
