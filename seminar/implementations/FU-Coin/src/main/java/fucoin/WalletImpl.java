package fucoin;

import java.util.Vector;

public class WalletImpl implements Wallet, Comparable<Wallet> {
  private final String address;
  private int moneyAmount;
  private Vector<WalletPointer> allKnownNeighbors;
  private Vector<Wallet> synchronizedNeighbors;

  public WalletImpl(String address) {
    this.address = address;
    this.allKnownNeighbors = new Vector<WalletPointer>();
    this.synchronizedNeighbors = new Vector<Wallet>();
  }

  @Override
  public Vector<WalletPointer> join(WalletPointer walletPointer) {
    allKnownNeighbors.add(walletPointer);
    return allKnownNeighbors;
  }

  @Override
  public Wallet searchWallet(String address) {
    for (Wallet wallet : synchronizedNeighbors) {
      if (wallet.getAddress().equals(address)) {
        return wallet;
      }
    }
    return null;
  }

  @Override
  public void storeOrUpdateWallet(Wallet w) {
    if (!synchronizedNeighbors.contains(w)) {
      this.synchronizedNeighbors.add(w);
    } else {
      for (Wallet wallet : synchronizedNeighbors) {
        if (wallet.equals(w)) {
          wallet.update(w);
          return;
        }
      }
    }
  }

  @Override
  public String getAddress() {
    return this.address;
  }

  @Override
  public void update(Wallet w) {
    this.moneyAmount = w.getMoneyAmount();
    this.synchronizedNeighbors = w.getSynchronizedNeighbors();
    this.allKnownNeighbors = w.getAllKnownNeighbors();
  }

  @Override
  public void removeKnownNeighbor(String address) {
    for (WalletPointer walletPointer : allKnownNeighbors) {
      if (walletPointer.getAddress().equals(address)) {
        allKnownNeighbors.remove(walletPointer);
        return;
      }
    }
  }

  @Override
  public void receiveTransaction(int amount) {
    this.moneyAmount = +amount;
  }

  public Vector<WalletPointer> getAllKnownNeighbors() {
    return allKnownNeighbors;
  }

  public int getMoneyAmount() {
    return moneyAmount;
  }

  public Vector<Wallet> getSynchronizedNeighbors() {
    return synchronizedNeighbors;
  }

  @Override
  public void updateWallets(int valueToTransform, String source, String target) {
    for (Wallet wallet : synchronizedNeighbors) {
      if (wallet.getAddress().equals(source)) {
        wallet.receiveTransaction(-valueToTransform);
      }
      if (wallet.getAddress().equals(target)) {
        wallet.receiveTransaction(valueToTransform);
      }
    }
  }

  public void initKnownNodes(Vector<WalletPointer> walletPointers) {
    allKnownNeighbors = walletPointers;
    for (WalletPointer walletPointer : allKnownNeighbors) {
      if (walletPointer.getAddress().equals(address)) {
        walletPointers.remove(walletPointer);
        return;
      }
    }
  }

  @Override
  public int compareTo(Wallet o) {
    if (o == null) {
      return 1;
    }
    if (o.getAddress().equals(this.address)) {
      return 0;
    }
    return 1;
  }
}
