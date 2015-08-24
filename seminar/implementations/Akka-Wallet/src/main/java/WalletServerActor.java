import akka.actor.UntypedActor;
import events.*;

/**
 * Created by dennish on 15/05/15.
 */
public class WalletServerActor extends UntypedActor {

  private Wallet wallet;

  public void preStart() {
    this.wallet = new Wallet();
  }

  @Override
  public void onReceive(Object message) {
    WalletEvent walletEvent = (WalletEvent) message;
    if (message instanceof AddMoneyEvent) {
      messageToConsole();
      wallet.addMoney(walletEvent.getEuro(), walletEvent.getCent());
      messageToConsole();
    }
    if (message instanceof RemoveMoneyEvent) {
      System.out.println(getSender().path());
      System.out.println(getSelf().path());
      messageToConsole();
      try {
        wallet.removeMoney(walletEvent.getEuro(), walletEvent.getCent());
      } catch (NotEnoughMoneyException e) {
        getContext().actorFor(getSender().path().toString()).tell(e.getMessage());
        getSender().tell(e.getMessage());
      }
      messageToConsole();
    }
    if (message instanceof AddEuroEvent) {
      messageToConsole();
      wallet.addEuro(walletEvent.getEuro());
      messageToConsole();
    }
    if (message instanceof RemoveEuroEvent) {
      messageToConsole();
      try {
        wallet.removeEuro(walletEvent.getEuro());
      } catch (NotEnoughMoneyException e) {
        getSender().tell(e.getMessage());
      }
      messageToConsole();
    }
    if (message instanceof AddCentEvent) {
      messageToConsole();
      wallet.addCent(walletEvent.getCent());
      messageToConsole();
    }
    if (message instanceof RemoveCentEvent) {
      messageToConsole();
      try {
        wallet.removeCent(walletEvent.getCent());
      } catch (NotEnoughMoneyException e) {
        getSender().tell(e.getMessage());
      }
      messageToConsole();
    }
    if (message.equals("HowMuch")) {
      getSender().tell(new TotalMoneyEvent(wallet.getEuro(), wallet.getCent()));
    } else {
      unhandled(message);
    }
  }

  private void messageToConsole() {
    System.out.printf("%s %10d,%02d Euro\n", "Money amount", wallet.getEuro(), wallet.getCent());
  }
}
