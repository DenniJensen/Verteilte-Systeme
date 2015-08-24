package fucoin;

import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.*;

public class WalletImplTest {

  Wallet wallet;
  WalletPointer walletPointer;

  @Before
  public void init() {
    wallet = new WalletImpl("url1");
    Wallet wallet1 = new WalletImpl("url2");
    walletPointer = new WalletPointer("url2");
    Vector<WalletPointer> walletPointers = new Vector<>();
    walletPointers.add(walletPointer);
    wallet.initKnownNodes(walletPointers);
    wallet.storeOrUpdateWallet(wallet1);
    wallet.storeOrUpdateWallet(wallet);
  }

  @Test
  public void testJoin() throws Exception {
    Vector<WalletPointer> walletPointers = wallet.join(walletPointer);
    assertEquals(2, walletPointers.size());
    assertEquals("url2", walletPointers.get(0).getAddress());
  }

  @Test
  public void testSearchWallet() throws Exception {
    Wallet wallet1 = wallet.searchWallet("url2");
    assertEquals("url2", wallet1.getAddress());
  }

  @Test
  public void testStoreOrUpdateWallet() throws Exception {
    Wallet wallet1 = new WalletImpl("url3");
    wallet.storeOrUpdateWallet(wallet1);
    assertEquals(3, wallet.getSynchronizedNeighbors().size());
  }

  @Test
  public void testGetAddress() throws Exception {
    assertEquals("url1", wallet.getAddress());
  }

  @Test
  public void testUpdate() throws Exception {
    Wallet wallet1 = new WalletImpl("url1");
    wallet.update(wallet1);
    assertEquals(0, wallet.getMoneyAmount());
    assertEquals(0, wallet.getAllKnownNeighbors().size());
    assertEquals(0, wallet.getSynchronizedNeighbors().size());
    assertEquals("url1", wallet.getAddress());
  }

  @Test
  public void testRemoveKnownNeighbor() throws Exception {
    wallet.removeKnownNeighbor("url2");
    assertEquals(0, wallet.getAllKnownNeighbors().size());
  }

  @Test
  public void testReceiveTransaction() throws Exception {
    wallet.receiveTransaction(10);
    assertEquals(10, wallet.getMoneyAmount());
  }

  @Test
  public void testReceiveTransaction2() throws Exception {
    wallet.receiveTransaction(-10);
    assertEquals(-10, wallet.getMoneyAmount());
  }

  @Test
  public void testGetAllKnownNeighbors() throws Exception {
    assertEquals(1, wallet.getAllKnownNeighbors().size());
  }

  @Test
  public void testGetMoneyAmount() throws Exception {
    assertEquals(0, wallet.getMoneyAmount());
  }

  @Test
  public void testGetSynchronizedNeighbors() throws Exception {
    Vector<Wallet> wallets = wallet.getSynchronizedNeighbors();
    assertEquals(2, wallets.size());
    assertEquals("url2", wallets.get(0).getAddress());
  }

  @Test
  public void testUpdateWallets() throws Exception {
    wallet.updateWallets(10, "url1", "url2");
  }

  @Test
  public void testInitKnownNodes() throws Exception {
    Vector<WalletPointer> walletPointers = new Vector<>();
    walletPointers.add(walletPointer);
    wallet.initKnownNodes(walletPointers);
    Vector<WalletPointer> walletPointers1 = wallet.getAllKnownNeighbors();
    assertEquals(1, walletPointers.size());
    assertEquals("url2", walletPointers.get(0).getAddress());
  }
}