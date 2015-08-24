package events;

/**
 * Created by goerickm
 */
public class TotalMoneyEvent extends WalletEvent {

  public TotalMoneyEvent(int euro, int cent) {
    super(euro, cent);
  }
}
