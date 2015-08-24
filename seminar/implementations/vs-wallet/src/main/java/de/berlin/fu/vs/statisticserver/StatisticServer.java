package de.berlin.fu.vs.statisticserver;

import akka.actor.UntypedActor;
import de.berlin.fu.vs.Wallet;
import de.berlin.fu.vs.statisticserver.statisticevents.JoinEvent;
import de.berlin.fu.vs.statisticserver.statisticevents.LeaveEvent;
import de.berlin.fu.vs.statisticserver.statisticevents.TransactionEvent;

import java.util.ArrayList;

/**
 * Created by dennish on 20/06/15.
 */
public class StatisticServer extends UntypedActor {

  private ArrayList<Wallet> walletList;
  private ArrayList<Wallet> activWallets;
  private ArrayList<TransactionEvent> transactionEvents;

  public void preStart() {
    walletList = new ArrayList<>(100);
    activWallets = new ArrayList<>(100);
    transactionEvents = new ArrayList<>(1000);
  }

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof JoinEvent) {
      Wallet wallet = ((JoinEvent) message).getWallet();
      handleJoinEvent(wallet);
    }
    if (message instanceof LeaveEvent) {
      Wallet wallet = ((LeaveEvent) message).getWallet();
      handleLeaveEvent(wallet);
    }
    if (message instanceof TransactionEvent) {
      handleTransactionEvent((TransactionEvent) message);
    } else {
      unhandled(message);
    }
  }

  public int computeTransactionCount() {
    return transactionEvents.size();
  }

  public int computeTransactionTotalAmount() {
    int totalAmount = 0;
    for (TransactionEvent transactionEvent : transactionEvents) {
      totalAmount += Math.abs(transactionEvent.getAmount());
    }
    return totalAmount;
  }

  public int computeTotalAmount() {
    int totalAmount = 0;
    for (Wallet wallet : walletList) {
      totalAmount += Math.abs(wallet.amount);
    }
    return totalAmount;
  }

  public int totalActivWallets() {
    return activWallets.size();
  }

  public int totalWallets() {
    return walletList.size();
  }

  private void handleTransactionEvent(TransactionEvent transactionEvent) {
    transactionEvents.add(transactionEvent);
    String source = transactionEvent.getSource();
    String target = transactionEvent.getTarget();
    int amount = transactionEvent.getAmount();
    for (Wallet wallet : walletList) {
      if (wallet.getName().equals(source)) {
        wallet.amount -= amount;
      } else if (wallet.getName().equals(target)) {
        wallet.amount += amount;
      }
    }
  }

  private void handleLeaveEvent(Wallet wallet) {
    if (activWallets.contains(wallet)) {
      activWallets.remove(wallet);
    }
  }

  private void handleJoinEvent(Wallet wallet) {
    if (!activWallets.contains(wallet)) {
      activWallets.add(wallet);
    }
    if (!walletList.contains(wallet)) {
      walletList.add(wallet);
    }
  }
}
