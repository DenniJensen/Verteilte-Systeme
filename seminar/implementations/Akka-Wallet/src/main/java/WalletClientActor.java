import akka.actor.UntypedActor;
import scala.util.parsing.combinator.testing.Str;

/**
 * Created by dennish on 15/05/15.
 */
public class WalletClientActor extends UntypedActor {

  @Override
  public void onReceive(Object message) throws Exception {

    if (message instanceof String) {
      System.out.println(message);
    } else {
      unhandled(message);
    }
  }
}
