package com.droidteahouse.contacts.presenters;

import com.droidteahouse.contacts.model.Contact;

/**
 * Created by sgv on 03.11.15.
 */
public interface IContactPresenter extends IBasePresenter {

  void addContactByGroupId(Contact contact, String GroupId);

  void editContactByGroupId(Contact contact, String GroupId);

  void deleteContactByPosition(int position);

  void deleteContactById(String ContactId);

  void getAllContacts();

  void getAllContactsByGroupId(String id);

  void getContactById(String id);

  void getGroupById(String id);
}
