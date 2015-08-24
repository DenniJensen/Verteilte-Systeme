package events;

/**
 * Created by dennish on 15/05/15.
 */
public class RemoveMoneyEvent extends WalletEvent {

  public RemoveMoneyEvent(int euro, int cent) {
    super(euro, cent);
  }
}
