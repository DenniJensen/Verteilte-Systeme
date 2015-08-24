package fucoin;

import java.util.Vector;


public interface Wallet {
  Vector<WalletPointer> join(WalletPointer walletPointer);

  Wallet searchWallet(String address);

  void storeOrUpdateWallet(Wallet w);

  void receiveTransaction(int amount);

  void initKnownNodes(Vector<WalletPointer> walletPointers);

  Vector<WalletPointer> getAllKnownNeighbors();

  String getAddress();

  void update(Wallet w);

  int getMoneyAmount();

  Vector<Wallet> getSynchronizedNeighbors();

  void updateWallets(int valueToTransform, String source, String target);

  void removeKnownNeighbor(String address);
}
