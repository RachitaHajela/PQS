package edu.nyu.cs.pqs.ps1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class AddressEntryTest {
  AddressEntry testEntry1;
  AddressEntry testEntry2;

  @Before
  public void setUp() throws Exception {
    testEntry1 =
        new AddressEntry.Builder("TestName1")
            .postalAddress("Apt Test, Testing Ave, Test City, 12345").phoneNumber("0123456789")
            .emailAddress("test1@testing.com").note("TestNote1").build();
    testEntry2 = new AddressEntry.Builder("TestName2").build();
  }

  @Test
  public void testHashCode() {
    AddressEntry sameValues =
        new AddressEntry.Builder("TestName1")
            .postalAddress("Apt Test, Testing Ave, Test City, 12345").phoneNumber("0123456789")
            .emailAddress("test1@testing.com").note("TestNote1").build();

    assertNotNull(testEntry1.hashCode());
    assertEquals(testEntry1.hashCode(), sameValues.hashCode());
    assertNotEquals(testEntry1.hashCode(), testEntry2.hashCode());
  }

  @Test
  public void testEqualsObject() {
    AddressEntry testEntry3 =
        new AddressEntry.Builder("TestName1")
            .postalAddress("Apt Test, Testing Ave, Test City, 12345").phoneNumber("0123456789")
            .emailAddress("test1@testing.com").note("TestNote1").build();

    assertFalse(testEntry1.equals(null));
    assertFalse(testEntry1.equals("string object"));
    assertFalse(testEntry1.equals(testEntry2));
    assertTrue(testEntry1.equals(testEntry1));
    assertTrue(testEntry1.equals(testEntry3));
  }

  @Test
  public void testGetName() {
    assertEquals("TestName1", testEntry1.getName());
  }

  /*
   * this test fails because AddressEntry object can be built with null as Name which should not be
   * possible.
   */
  @Test
  public void testGetName_nameNull() {
    AddressEntry testEntry3 = new AddressEntry.Builder(null).build();
    assertNotNull("Name should not be null", testEntry3.getName());
  }

  @Test
  public void testGetEmailAddress() {
    assertEquals("test1@testing.com", testEntry1.getEmailAddress());
    assertTrue(testEntry2.getEmailAddress().isEmpty());
  }

  @Test
  public void testGetNote() {
    assertEquals("TestNote1", testEntry1.getNote());
    assertTrue(testEntry2.getNote().isEmpty());
  }

  @Test
  public void testGetPhoneNumber() {
    assertEquals("0123456789", testEntry1.getPhoneNumber());
    assertTrue(testEntry2.getPhoneNumber().isEmpty());
  }

  @Test
  public void testGetPostalAddress() {
    assertEquals("Apt Test, Testing Ave, Test City, 12345", testEntry1.getPostalAddress());
    assertTrue(testEntry2.getPostalAddress().isEmpty());
  }

  @Test
  public void testSetEmailAddress() {
    testEntry1.setEmailAddress("testEmailAddress@testing.com");
    assertEquals("testEmailAddress@testing.com", testEntry1.getEmailAddress());
    testEntry2.setEmailAddress("testEmailAddress@testing.com");
    assertEquals("testEmailAddress@testing.com", testEntry2.getEmailAddress());
  }

  @Test
  public void testSetName() {
    testEntry1.setName("name1");
    assertEquals("name1", testEntry1.getName());
    testEntry2.setName("name2");
    assertEquals("name2", testEntry2.getName());
  }

  @Test
  public void testSetNote() {
    testEntry1.setNote("note1");
    assertEquals("note1", testEntry1.getNote());
    testEntry2.setNote("note2");
    assertEquals("note2", testEntry2.getNote());
  }

  @Test
  public void testSetPhoneNumber() {
    testEntry1.setPhoneNumber("551-998");
    assertEquals("551-998", testEntry1.getPhoneNumber());
    testEntry2.setPhoneNumber("551-998-0985");
    assertEquals("551-998-0985", testEntry2.getPhoneNumber());

  }

  @Test
  public void testSetPostalAddress() {
    testEntry1.setPostalAddress("Apt 1, Some ave, Some city");
    assertEquals("Apt 1, Some ave, Some city", testEntry1.getPostalAddress());
    testEntry2.setPostalAddress("Apt 2, Some ave, Some city");
    assertEquals("Apt 2, Some ave, Some city", testEntry2.getPostalAddress());
  }

  @Test
  public void testToString() {
    assertNotNull(testEntry1.toString());
    assertTrue(testEntry1.toString().length() > 0);
    assertNotEquals(testEntry1.toString(), testEntry2.toString());
  }
}