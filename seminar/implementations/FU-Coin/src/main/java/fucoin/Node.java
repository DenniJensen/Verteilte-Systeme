package fucoin;

import akka.actor.UntypedActor;
import fucoin.events.AcceptJoin;
import fucoin.events.FoundWallet;
import fucoin.events.SyncWallet;
import fucoin.events.Transaction;

import java.util.Vector;

public class Node extends UntypedActor {

  private Wallet wallet;
  private int numberOfSearchRequest;

  public Node() {

  }

  public Node(String address) {
    wallet = new WalletImpl(address);
  }

  @Override
  public void preStart() {
  }

  @Override
  public void onReceive(Object message) {
    if (message.equals("join")) {
      handleJoinEvent();
    }
    if (message instanceof AcceptJoin) {
      handleAcceptJoinEvent((AcceptJoin) message);
    }
    if (message.equals("searchMe")) {
      Wallet foundWallet = wallet.searchWallet(getSender().path().toString());
      FoundWallet fw = new FoundWallet(foundWallet);
      getSender().tell(fw, getSelf());
    }
    if (message instanceof FoundWallet) {
      handleFoundWalletEvent((FoundWallet) message);
    }
    if (message instanceof SyncWallet) {
      Wallet toSyncWallet = ((SyncWallet) message).getWallet();
      wallet.storeOrUpdateWallet(toSyncWallet);
    }
    if (message instanceof Transaction) {
      handleTransactionEvent((Transaction) message);
    }
    if (message.equals("leave")) {
      wallet.removeKnownNeighbor(getSender().path().toString());
    }
  }

  private void handleFoundWalletEvent(FoundWallet foundWallet) {
    if (numberOfSearchRequest > 0) {
      if (foundWallet.getWallet() == null) {
        numberOfSearchRequest--;
        if (numberOfSearchRequest == 0) {
          synchronizeOwnWalletToAllNodes();
        }
      } else {
        numberOfSearchRequest = 0;
        wallet = foundWallet.getWallet();
        synchronizeOwnWalletToAllNodes();
      }
    }
  }

  private void handleTransactionEvent(Transaction transaction) {
    int valueToTransform = transaction.getValue();
    String source = transaction.getSource();
    String target = transaction.getTarget();
    wallet.updateWallets(valueToTransform, source, target);
  }

  private void synchronizeOwnWalletToAllNodes() {
    notifyAll(new SyncWallet(this.wallet));
  }

  private void handleJoinEvent() {
    String acceptedAddress = getSender().path().toString();
    WalletPointer walletPointer = new WalletPointer(acceptedAddress);
    AcceptJoin acceptJoin = new AcceptJoin(wallet.join(walletPointer), acceptedAddress);
    getSender().tell(acceptJoin, getSelf());
  }

  private void handleAcceptJoinEvent(AcceptJoin acceptJoin) {
    initKnownNodes(acceptJoin);
    searchOwnWallInNetwork();
  }

  private void initKnownNodes(AcceptJoin acceptJoin) {
    String ownAddressInNetwork = acceptJoin.getAcceptedAddress();
    wallet = new WalletImpl(ownAddressInNetwork);
    System.out.println("Join accept");
    Vector<WalletPointer> walletPointers = acceptJoin.getWalletPointers();
    wallet.initKnownNodes(walletPointers);
  }

  private void searchOwnWallInNetwork() {
    this.numberOfSearchRequest = wallet.getAllKnownNeighbors().size();
    notifyAll("searchMe");
  }

  private void notifyAll(Object event) {
    String address;
    for (WalletPointer walletPointer : wallet.getAllKnownNeighbors()) {
      address = walletPointer.getAddress();
      getContext().actorFor(address).tell(event, getSelf());
    }
  }
}
