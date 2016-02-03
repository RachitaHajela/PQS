package edu.nyu.cs.pqs.ps1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import edu.nyu.cs.pqs.utils.AddressBookUtils.SearchBy;

public class AddressBookTests {
  AddressBook addressBook;
  AddressEntry testEntry1;
  AddressEntry testEntry2;
  AddressEntry testEntry3;
  AddressEntry testEntry4;

  @Before
  public void setUp() throws Exception {
    addressBook = AddressBook.create("TestBook1");

    testEntry1 =
        new AddressEntry.Builder("TestName1")
            .postalAddress("Apt Test, Testing Ave, Test City, 12345").phoneNumber("0123456789")
            .emailAddress("test1@testing.com").note("TestNote1").build();
    testEntry2 =
        new AddressEntry.Builder("TestName2")
            .postalAddress("Apt Test, Testing Ave, Test City, 12345").phoneNumber("0123456788")
            .emailAddress("test2@testing.com").note("TestNote2").build();
    testEntry3 =
        new AddressEntry.Builder("TestName3")
            .postalAddress("Apt Test, Testing Ave, Test City, 12345").phoneNumber("0123456777")
            .emailAddress("test3@testing.com").note("TestNote3").build();
    testEntry4 =
        new AddressEntry.Builder("TestName4")
            .postalAddress("Apt Test, Testing Ave, Test City, 12345").phoneNumber("0123456777")
            .emailAddress("test3@testing.com").note("TestNote3").build();

    addressBook.addEntry(testEntry1);
    addressBook.addEntry(testEntry2);
    addressBook.addEntry(testEntry3);
  }

  @Test
  public void testCreate() {
    assertNotNull(addressBook);
    assertEquals("TestBook1", addressBook.getAddressBookName());
  }

  /*
   * The current implementation makes it possible to create AddressBook with no name
   */
  @Test
  public void testCreate_nullAddressBookName() {
    AddressBook addressBook = AddressBook.create(null);
    assertEquals(null, addressBook.getAddressBookName());
  }

  @Test
  public void testAddEntry() {
    assertTrue(addressBook.addEntry(testEntry4));
    assertFalse(addressBook.addEntry(testEntry4));

    AddressEntry sameValues =
        new AddressEntry.Builder("TestName4")
            .postalAddress("Apt Test, Testing Ave, Test City, 12345").phoneNumber("0123456777")
            .emailAddress("test3@testing.com").note("TestNote3").build();
    assertFalse(addressBook.addEntry(sameValues));
  }

  /*
   * the test fails because null can be added to the AddressBook
   */

  @Test
  public void testAddEntry_ifNull() {
    assertFalse("Null should not be added to the address book", addressBook.addEntry(null));
  }

  @Test
  public void testRemove() {
    assertTrue(addressBook.remove(testEntry1));
    assertFalse(addressBook.remove(null));
    assertFalse(addressBook.remove(testEntry4));
  }

  @Test
  public void testSearchByName_ifPresent() {
    List<AddressEntry> searchResult = addressBook.searchBy(SearchBy.NAME, "TestName1");
    assertEquals(testEntry1, searchResult.get(0));
  }

  @Test
  public void testSearchByName_ifNotPresent() {
    List<AddressEntry> searchResult = addressBook.searchBy(SearchBy.NAME, "TestName5");
    assertTrue("value is not present", searchResult.isEmpty());

    searchResult = addressBook.searchBy(SearchBy.NAME, "testName1");
    assertTrue("Search is case sensitive", searchResult.isEmpty());

    searchResult = addressBook.searchBy(SearchBy.NAME, "Test");
    assertTrue("Partial search option not available", searchResult.isEmpty());
  }

  @Test
  public void testSearchByPhoneNumber_ifPresent() {
    List<AddressEntry> searchResult = addressBook.searchBy(SearchBy.PHONE_NUMBER, "0123456788");
    assertEquals(testEntry2, searchResult.get(0));
  }

  @Test
  public void testSearchByPhoneNumber_ifNotPresent() {
    List<AddressEntry> searchResult = addressBook.searchBy(SearchBy.PHONE_NUMBER, "0123456666");
    assertTrue("value is not present", searchResult.isEmpty());

    searchResult = addressBook.searchBy(SearchBy.PHONE_NUMBER, "01234");
    assertTrue("Partial search option not available", searchResult.isEmpty());
  }

  @Test
  public void testSearchByPostalAddress_ifPresent() {
    List<AddressEntry> searchResult =
        addressBook.searchBy(SearchBy.POSTAL_ADDRESS, "Apt Test, Testing Ave, Test City, 12345");
    assertEquals(3, searchResult.size());
    assertEquals(testEntry2, searchResult.get(1));
  }

  @Test
  public void testSearchByPostalAddress_ifNotPresent() {
    List<AddressEntry> searchResult = addressBook.searchBy(SearchBy.POSTAL_ADDRESS, null);
    assertTrue(searchResult.isEmpty());

    searchResult =
        addressBook.searchBy(SearchBy.POSTAL_ADDRESS, "Apt test, testing Ave, test City, 12345");
    assertTrue("Search is case sensitive", searchResult.isEmpty());

    searchResult = addressBook.searchBy(SearchBy.POSTAL_ADDRESS, " ");
    assertTrue("Partial search option not available", searchResult.isEmpty());
  }

  @Test
  public void testSearchByNote_ifPresent() {
    List<AddressEntry> searchResult = addressBook.searchBy(SearchBy.NOTE, "TestNote1");
    assertEquals(testEntry1, searchResult.get(0));
  }

  @Test
  public void testSearchByNote_ifNotPresent() {
    List<AddressEntry> searchResult = addressBook.searchBy(SearchBy.NOTE, "not present");
    assertTrue(searchResult.isEmpty());

    searchResult = addressBook.searchBy(SearchBy.NOTE, "TEstNote1");
    assertTrue("Search is case sensitive", searchResult.isEmpty());

    searchResult = addressBook.searchBy(SearchBy.NOTE, "Note1");
    assertTrue("Partial search option not available", searchResult.isEmpty());
  }

  @Test
  public void testSearchByEmail_ifPresent() {
    List<AddressEntry> searchResult = addressBook.searchBy(SearchBy.EMAIL, "test3@testing.com");
    assertEquals(testEntry3, searchResult.get(0));

  }

  @Test
  public void testSearchByEmail_ifNotPresent() {
    List<AddressEntry> searchResult = addressBook.searchBy(SearchBy.EMAIL, "not present");
    assertTrue(searchResult.isEmpty());

    searchResult = addressBook.searchBy(SearchBy.EMAIL, "Test3@testing.com");
    assertTrue("Search is case sensitive", searchResult.isEmpty());

    searchResult = addressBook.searchBy(SearchBy.EMAIL, "test3");
    assertTrue("Partial search option not available", searchResult.isEmpty());
  }

  @Test
  public void testSaveAndLoad() {
    addressBook.saveToFile("output.xml", addressBook);
    AddressBook addressBookFromFile = addressBook.loadFromFile("output.xml");
    assertEquals("TestBook1", addressBookFromFile.getAddressBookName());
    assertEquals(addressBookFromFile.getEntry(), addressBook.getEntry());
  }

  /*
   * Bad code design. The method was not returning anything for success or failure. Neither was it
   * throwing an exception on wrong file name. Changed the function to return boolean instead of
   * void to make Catch block reachable.
   */
  @Test
  public void testSaveToFile_pathWrong() {
    assertFalse(addressBook.saveToFile(" ", addressBook));
  }

  /*
   * Bad code design. The method loadFromFile(String path) does not throw an exception or deal with
   * wrong file names.
   */
  @Test
  public void testLoadFromFile_pathWrong() {
    AddressBook addressBookFromFile = addressBook.loadFromFile("abc");
    assertFalse(addressBookFromFile.getAddressBookName() != null);
  }

  @Test
  public void testToString() {
    assertNotNull(addressBook.toString());
    assertTrue(addressBook.toString().length() > 0);
  }

  @Test
  public void testSetAddressBookName() {
    addressBook.setAddressBookName("TestBook2");
    assertEquals("TestBook2", addressBook.getAddressBookName());
  }

  @Test
  public void testSetEntry() {
    Set<AddressEntry> entrySet = addressBook.getEntry();
    AddressBook testAddressBook = AddressBook.create("Test AddressBook");
    assertEquals(0, testAddressBook.getEntry().size());

    testAddressBook.setEntry(entrySet);
    assertEquals(3, testAddressBook.getEntry().size());

    testAddressBook.setEntry(null);
    assertEquals(null, testAddressBook.getEntry());
  }
}