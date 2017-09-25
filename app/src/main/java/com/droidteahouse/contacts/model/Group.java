package com.droidteahouse.contacts.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sgv on 14.10.15.
 */
public class Group extends RealmObject {
  @PrimaryKey
  private String id;
  private String name;
  public RealmList<Contact> contacts;

  public Group() {
  }

  public Group(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RealmList<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(RealmList<Contact> contacts) {
    this.contacts = contacts;
  }
}
