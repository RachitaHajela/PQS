package edu.nyu.cs.pqs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

/**
 * AddressBook class is used to create an Address Book. The users of the library can create an empty
 * address book, add a contact entry, remove an entry, search for a contact, save address book to
 * file and read address book from file.
 * 
 * @author Rachita
 *
 */
public class AddressBook {
  private Vector<Contact> contacts;

  private AddressBook() {
    contacts = new Vector<Contact>();
  }

  /**
   * create empty address book.
   * 
   * @return empty address book
   */
  public static AddressBook createEmptyAddressBook() {
    return new AddressBook();
  }

  /**
   * add a {@link Contact} to address book
   * 
   * @param contact
   *          object of class Contact
   */
  public void addContact(Contact contact) {
    contacts.add(contact);
  }

  /**
   * Remove a contact from address book given its reference
   * 
   * @param contact
   *          object of class Contact
   */
  public void removeContact(Contact contact) {
    contacts.remove(contact);
  }

  /**
   * search in all the fields of all contacts in address book, given a string
   * 
   * @param searchString
   *          the string to be searched in the address book
   * @return ArrayList of all the contact entries which contain the argument string
   */
  public ArrayList<Contact> search(String searchString) {
    ArrayList<Contact> resultList = new ArrayList<Contact>();

    for (Contact contact : contacts) {
      if (contact.toString().toLowerCase().contains(searchString.toLowerCase())) {
        resultList.add(contact);
      }
    }
    return resultList;
  }

  /**
   * save the contents of the Address book to the file using FileWriter.
   * 
   * @param filePath
   *          the file path where the contents have to be stored. Creates a new file if the file
   *          doesn't exist.
   * @throws IOException
   *           throws an IOexception if there is some error with the file
   */
  public void saveAddressBookToFile(String filePath) throws IOException {
    File file = new File(filePath);
    if (!file.exists()) {
      file.createNewFile();
    }
    FileWriter fileWriter;
    BufferedWriter bufferedWriter;

    fileWriter = new FileWriter(file);
    bufferedWriter = new BufferedWriter(fileWriter);
    bufferedWriter.write(this.toString());
    bufferedWriter.close();
  }

  /**
   * reading the address book from a given file using Scanner. It adds the contacts read from the
   * file to the AddressBook object calling this method
   * 
   * @param filePath
   *          the file path from where the contents have to be read.
   * @throws IOException
   *           throws an IOexception if there is some error with the file
   */
  public void loadContactsFromFile(String filePath) throws IOException {
    File file = new File(filePath);
    Scanner readFile = new Scanner(file);
    String contact = "";
    String[] contactFields;

    while (readFile.hasNextLine()) {
      contact = readFile.nextLine();
      if (contact.length() > 0) {

        // split on the delimiter ;
        contactFields = contact.split(";");

        // building the contact object
        Contact.Builder builder = new Contact.Builder(contactFields[0], contactFields[1]);

        // setting postal address
        if (contactFields[2].length() > 0) {
          builder.postalAddress(contactFields[2]);
        }

        // setting email address
        if (contactFields[3].length() > 0) {
          builder.emailAddress(contactFields[3]);
        }

        // setting note
        if (contactFields[4].length() > 0) {
          builder.note(contactFields[4]);
        }

        // adding the contact to the address book
        Contact contactFromFile = builder.build();
        addContact(contactFromFile);
      }
    }
    readFile.close();
  }

  /**
   * hashCode method for AddressBook
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((contacts == null) ? 0 : contacts.hashCode());
    return result;
  }

  /**
   * overriding equals for logical equality between two AddressBook objects.
   */
  @Override
  public boolean equals(Object obj) {
    // check if the obj is reference to this
    if (obj == this) {
      return true;
    }

    // check if obj is of the AddressBook type
    if (!(obj instanceof AddressBook)) {
      return false;
    }

    // check if the field is equal
    AddressBook other = (AddressBook) obj;
    if (contacts == null) {
      if (other.contacts != null)
        return false;
    } else if (!contacts.equals(other.contacts))
      return false;
    return true;
  }

  /**
   * It puts information related to each contact on a new line.
   */
  @Override
  public String toString() {
    String resultString = "";
    for (Contact contact : contacts) {
      resultString += contact.toString() + "\n";
    }
    return resultString;
  }
}