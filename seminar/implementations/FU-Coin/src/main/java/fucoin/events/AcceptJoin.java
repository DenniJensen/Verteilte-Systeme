package fucoin.events;

import fucoin.WalletPointer;

import java.io.Serializable;
import java.util.Vector;

public class AcceptJoin implements Serializable {

  private final Vector<WalletPointer> walletPointers;
  private final String acceptedAddress;

  public AcceptJoin(Vector<WalletPointer> walletPointers, String acceptedAddress) {
    this.walletPointers = walletPointers;
    this.acceptedAddress = acceptedAddress;
  }

  public Vector<WalletPointer> getWalletPointers() {
    return walletPointers;
  }

  public String getAcceptedAddress() {
    return acceptedAddress;
  }
}
