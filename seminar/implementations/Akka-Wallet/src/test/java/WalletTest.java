import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;


public class WalletTest {

  private Wallet wallet;

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Before
  public void init() {
    wallet = new Wallet(4, 230);
  }

  @Test
  public void testInitMoney() {
    Wallet wallet = new Wallet(4, 230);
    assertEquals(wallet.getEuro(), 6);
    assertEquals(wallet.getCent(), 30);
  }

  @Test
  public void testAddCent() {
    Wallet wallet = new Wallet();
    wallet.addCent(200);
    assertEquals(wallet.getEuro(), 2);
    assertEquals(wallet.getCent(), 0);
  }

  @Test
  public void testAddCent2() {
    wallet.addCent(80);
    assertEquals(wallet.getEuro(), 7);
    assertEquals(wallet.getCent(), 10);
  }

  @Test
  public void testAddEuro() {
    wallet.addEuro(3);
    assertEquals(wallet.getEuro(), 9);
    assertEquals(wallet.getCent(), 30);
  }

  @Test
  public void testAddMoney() {
    Wallet wallet = new Wallet();
    wallet.addMoney(4, 230);
    assertEquals(wallet.getEuro(), 6);
    assertEquals(wallet.getCent(), 30);
  }


  @Test
  public void testRemoveCent() throws Exception {
    wallet.removeCent(340);
    assertEquals(wallet.getEuro(), 2);
    assertEquals(wallet.getCent(), 90);
  }

  @Test
  public void testRemoveEuro() throws Exception {
    wallet.removeEuro(3);
    assertEquals(wallet.getEuro(), 3);
    assertEquals(wallet.getCent(), 30);
  }

  @Test
  public void testRemoveMoney() throws Exception {
    wallet.removeMoney(2, 150);
    assertEquals(wallet.getEuro(), 2);
    assertEquals(wallet.getCent(), 80);
  }

  @Test
  public void testNotEnoughMoneyException() throws NotEnoughMoneyException{
    exception.expect(NotEnoughMoneyException.class);
    wallet.removeMoney(6, 40);
  }
}