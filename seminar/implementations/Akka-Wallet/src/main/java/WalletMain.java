import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by dennish on 15/05/15.
 */
public class WalletMain {
  public static void main(String[] args) {
    System.out.print("Hello World");
    Wallet wallet = new Wallet();
    Config config = ConfigFactory.load().getConfig("ServerSys");
    ActorSystem actorSystem = ActorSystem.create("wallet", config);
    Props props = new Props(WalletServerActor.class);
    ActorRef server = actorSystem.actorOf(props, "walletKeeper");
  }
}
