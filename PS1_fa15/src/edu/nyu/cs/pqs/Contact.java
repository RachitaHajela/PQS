package edu.nyu.cs.pqs;

/**
 * Contact class is for creating an entry object in the AddressBook. It consists of five fields :
 * name, postal address, phone number, email address and note. Name and phone number fields are
 * required, other fields can be optional. Builder pattern is used to build this class.
 * 
 * @author Rachita
 *
 */
public class Contact {
  private String name;
  private String postalAddress;
  private String phoneNumber;
  private String emailAddress;
  private String note;

  /**
   * Builder class used to build an object of Contact class using builder pattern.
   *
   */
  public static class Builder {
    // Required Parameters
    private String name;
    private String phoneNumber;
    // Optional parameters ;
    private String postalAddress;
    private String emailAddress;
    private String note;

    /**
     * Constructor with the required fields
     * 
     * @param name
     *          name of the contact
     * 
     * @param phoneNumber
     *          phone number of the contact
     */
    public Builder(String name, String phoneNumber) {
      this.name = name;
      this.phoneNumber = phoneNumber;
    }

    /**
     * method to set optional parameter postal address
     * 
     * @param postalAddress
     *          optional field postal address
     * @return the builder object
     */
    public Builder postalAddress(String postalAddress) {
      this.postalAddress = postalAddress;
      return this;
    }

    /**
     * method to set optional parameter email address
     * 
     * @param emailAddress
     *          optional field postal address
     * @return the builder object
     */
    public Builder emailAddress(String emailAddress) {
      this.emailAddress = emailAddress;
      return this;
    }

    /**
     * method to set optional parameter note
     * 
     * @param note
     *          optional field note
     * @return the builder object
     */
    public Builder note(String note) {
      this.note = note;
      return this;
    }

    /**
     * builds an instance of Contact
     * 
     * @return Contact object
     */
    public Contact build() {
      return new Contact(this);
    }
  }

  // create an instance of Contact using fields of builder object
  private Contact(Builder builder) {
    name = builder.name;
    phoneNumber = builder.phoneNumber;
    emailAddress = builder.emailAddress;
    postalAddress = builder.postalAddress;
    note = builder.note;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the postalAddress
   */
  public String getPostalAddress() {
    return postalAddress;
  }

  /**
   * @return the phoneNumber
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @return the emailAddress
   */
  public String getEmailAddress() {
    return emailAddress;
  }

  /**
   * @return the note
   */
  public String getNote() {
    return note;
  }

  /**
   * Hashcode method for this class
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((note == null) ? 0 : note.hashCode());
    result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
    result = prime * result + ((postalAddress == null) ? 0 : postalAddress.hashCode());
    return result;
  }

  /**
   * overriding equals for logical equality between two contact objects.
   */
  @Override
  public boolean equals(Object obj) {
    // check if the obj is reference to this
    if (obj == this) {
      return true;
    }

    // check if obj is of the Contact type
    if (!(obj instanceof Contact)) {
      return false;
    }

    // check if all the non empty fields are equal
    Contact otherContact = (Contact) obj;

    if (emailAddress == null) {
      if (otherContact.emailAddress != null) {
        return false;
      }
    } else if (!emailAddress.equals(otherContact.emailAddress)) {
      return false;
    }
    if (name == null) {
      if (otherContact.name != null) {
        return false;
      }
    } else if (!name.equals(otherContact.name)) {
      return false;
    }
    if (note == null) {
      if (otherContact.note != null) {
        return false;
      }
    } else if (!note.equals(otherContact.note)) {
      return false;
    }
    if (phoneNumber == null) {
      if (otherContact.phoneNumber != null) {
        return false;
      }
    } else if (!phoneNumber.equals(otherContact.phoneNumber)) {
      return false;
    }
    if (postalAddress == null) {
      if (otherContact.postalAddress != null) {
        return false;
      }
    } else if (!postalAddress.equals(otherContact.postalAddress)) {
      return false;
    }
    return true;
  }

  /**
   * returns string in the format of name;phoneNumber;postalAddress; emailAddress;note if a field is
   * null it will be an empty field like name;phoneNumber;;emailAddress;;
   */
  @Override
  public String toString() {
    String resultString = getName() + ";" + getPhoneNumber() + ";";
    if (postalAddress != null) {
      resultString += getPostalAddress();
    }
    resultString += ";";
    if (emailAddress != null) {
      resultString += getEmailAddress();
    }
    resultString += ";";
    if (note != null) {
      resultString += getNote();
    }
    resultString += ";";
    return resultString;
  }
}