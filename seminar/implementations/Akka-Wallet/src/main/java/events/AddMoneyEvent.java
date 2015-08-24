package events;

/**
 * Created by dennish on 15/05/15.
 */
public class AddMoneyEvent extends WalletEvent {

  public AddMoneyEvent(int euro, int cent) {
    super(euro, cent);
  }
}
