package com.inovex.bikroyik.adapter;

/**
 * Created by DELL on 11/8/2018.
 */

public class Contacts {
    String contactsTitle;
    String contactsNumber;
    String contactsAddress;
    String contactsType;

    public Contacts(String contactsTitle, String contactsNumber, String contactsAddress, String contactsType) {
        this.contactsTitle = contactsTitle;
        this.contactsNumber = contactsNumber;
        this.contactsAddress = contactsAddress;
        this.contactsType = contactsType;
    }

    public String getContactsAddress() {
        return contactsAddress;
    }

    public void setContactsAddress(String contactsAddress) {
        this.contactsAddress = contactsAddress;
    }

    public String getContactsTitle() {
        return contactsTitle;
    }

    public void setContactsTitle(String contactsTitle) {
        this.contactsTitle = contactsTitle;
    }

    public String getContactsNumber() {
        return contactsNumber;
    }

    public void setContactsNumber(String contactsNumber) {
        this.contactsNumber = contactsNumber;
    }

    public String getContactsType() {
        return contactsType;
    }

    public void setContactsType(String contactsType) {
        this.contactsType = contactsType;
    }
}
