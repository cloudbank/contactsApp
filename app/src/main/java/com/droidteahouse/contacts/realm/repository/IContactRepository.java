package com.droidteahouse.contacts.realm.repository;

import com.droidteahouse.contacts.model.Contact;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by sgv on 16.10.15.
 */
public interface IContactRepository {
  interface OnSaveContactCallback {
    void onSuccess(String groupId);

    void onError(String message);
  }

  interface OnEditContactCallback {
    void onSuccess(String groupId);

    void onError(String message);
  }

  interface OnDeleteContactCallback {
    void onSuccess();

    void onError(String message);
  }

  interface OnGetContactByIdCallback {
    void onSuccess(Contact contact);

    void onError(String message);
  }

  interface OnGetAllContactsCallback {
    void onSuccess(RealmResults<Contact> contacts);

    void onError(String message);
  }

  interface OnGetContactsCallback {
    void onSuccess(RealmList<Contact> contacts);

    void onError(String message);
  }


  void addContactByGroupId(Contact contact, String groupId, OnSaveContactCallback callback);

  void editContactByGroupId(Contact contact, OnEditContactCallback callback);

  void deleteContactById(String id, OnDeleteContactCallback callback);

  void deleteContactByPosition(int position, OnDeleteContactCallback callback);

  void getAllContacts(OnGetAllContactsCallback callback);

  void getAllContactsByGroupId(String id, OnGetContactsCallback callback);

  void getContactById(String id, OnGetContactByIdCallback callback);
}
