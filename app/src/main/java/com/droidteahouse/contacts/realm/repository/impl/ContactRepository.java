package com.droidteahouse.contacts.realm.repository.impl;

import com.droidteahouse.contacts.model.Address;
import com.droidteahouse.contacts.model.Contact;
import com.droidteahouse.contacts.model.Group;
import com.droidteahouse.contacts.realm.repository.IContactRepository;
import com.droidteahouse.contacts.realm.table.RealmTable;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by sgv on 16.10.15.
 */
public class ContactRepository implements IContactRepository {
  @Override
  public void addContactByGroupId(final Contact contact, final String groupId, final OnSaveContactCallback callback) {
    Realm realm = Realm.getDefaultInstance();
    realm.executeTransactionAsync(new Realm.Transaction() {
      @Override
      public void execute(Realm bgRealm) {
        String id = UUID.randomUUID().toString();
        Contact realmContact = bgRealm.createObject(Contact.class, id);
        //realmContact.setId(UUID.randomUUID().toString());
        realmContact.setGroupId(groupId);
        realmContact.setFirstname(contact.getFirstname());
        realmContact.setLastname(contact.getLastname());
        realmContact.setPhone(contact.getPhone());
        realmContact.setEmail(contact.getEmail());
        realmContact.setBirthday(contact.getBirthday());
        realmContact.addressList.clear();
        realmContact.addressList.addAll(contact.getAddress());
        Group group = bgRealm.where(Group.class).equalTo(RealmTable.ID, groupId).findFirst();
        group.contacts.add(realmContact);
      }
    }, new Realm.Transaction.OnSuccess() {
      @Override
      public void onSuccess() {
        callback.onSuccess(groupId);
      }
    }, new Realm.Transaction.OnError() {
      @Override
      public void onError(Throwable error) {
        // Transaction failed and was automatically canceled.
      }
    });
  }

  @Override
  public void editContactByGroupId(final Contact contact, final OnEditContactCallback callback) {
    Realm realm = Realm.getDefaultInstance();
    realm.executeTransactionAsync(new Realm.Transaction() {
      @Override
      public void execute(Realm bgRealm) {
        Contact realmContact = bgRealm.where(Contact.class).equalTo(RealmTable.ID, contact.getId()).findFirst();
        Group group = bgRealm.where(Group.class).equalTo(RealmTable.ID, contact.getGroupId()).findFirst();
        group.contacts.remove(realmContact);
        realmContact.setGroupId(contact.getGroupId());
        realmContact.setFirstname(contact.getFirstname());
        realmContact.setLastname(contact.getLastname());
        realmContact.setPhone(contact.getPhone());
        realmContact.setEmail(contact.getEmail());
        realmContact.setBirthday(contact.getBirthday());
        RealmList<Address> rAddressList = realmContact.getAddressList();
        rAddressList.clear(); // worst case everything has been deleted on update
        rAddressList.addAll(contact.getAddress());
        group.contacts.add(realmContact); //@todo uniq
      }
    }, new Realm.Transaction.OnSuccess() {
      @Override
      public void onSuccess() {
        callback.onSuccess(contact.getGroupId());
      }
    }, new Realm.Transaction.OnError() {
      @Override
      public void onError(Throwable error) {
        // Transaction failed and was automatically canceled.
      }
    });
  }

  //@todo add execute here
  @Override
  public void deleteContactById(String id, OnDeleteContactCallback callback) {
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    Contact result = realm.where(Contact.class).equalTo(RealmTable.ID, id).findFirst();
    result.deleteFromRealm();
    realm.commitTransaction();
    if (callback != null)
      callback.onSuccess();
  }

  @Override
  public void deleteContactByPosition(int position, OnDeleteContactCallback callback) {
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    RealmQuery<Contact> query = realm.where(Contact.class);
    RealmResults<Contact> results = query.findAll();
    results.remove(position);
    realm.commitTransaction();
    if (callback != null)
      callback.onSuccess();
  }

  @Override
  public void getAllContacts(OnGetAllContactsCallback callback) {
    Realm realm = Realm.getDefaultInstance();
    RealmResults<Contact> results = realm.where(Contact.class).findAll();
    if (callback != null)
      callback.onSuccess(results);
  }

  //@todo .findAllAsync() with onchange if large
  @Override
  public void getAllContactsByGroupId(String id, OnGetContactsCallback callback) {
    Realm realm = Realm.getDefaultInstance();
    Group group = realm.where(Group.class).equalTo(RealmTable.ID, id).findFirst();
    RealmList<Contact> contacts = group.getContacts(); //@todo sort change to realmresults or sort in model
    if (callback != null)
      callback.onSuccess(contacts);
  }

  @Override
  public void getContactById(String id, OnGetContactByIdCallback callback) {
    Realm realm = Realm.getDefaultInstance();
    Contact contact = realm.where(Contact.class).equalTo(RealmTable.ID, id).findFirst();
    if (callback != null)
      callback.onSuccess(contact);
  }
  //@todo getContactByName for search
}
