package com.droidteahouse.contacts.realm.table;

/**
 * Created by sgv on 16.10.15.
 */
public interface RealmTable {
  String ID = "id";

  interface Group {
    String CONTACTS = "contacts";
    String NAME = "name";
  }

  interface Contact {
    String NAME = "name";
    String AGE = "age";
    String EMAIL = "email";
  }
}
