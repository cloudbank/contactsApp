package com.droidteahouse.contacts.realm.module;

import com.droidteahouse.contacts.model.Address;
import com.droidteahouse.contacts.model.Contact;
import com.droidteahouse.contacts.model.Group;

import io.realm.annotations.RealmModule;

/**
 * Created by sgv on 21.09.17.
 */
@RealmModule(classes = {Contact.class, Group.class, Address.class})
public class SimpleContactsApp {
}
