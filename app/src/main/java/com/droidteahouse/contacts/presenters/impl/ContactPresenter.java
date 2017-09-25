package com.droidteahouse.contacts.presenters.impl;

import android.os.Handler;
import android.os.Looper;

import com.droidteahouse.contacts.model.Contact;
import com.droidteahouse.contacts.model.Group;
import com.droidteahouse.contacts.presenters.IContactPresenter;
import com.droidteahouse.contacts.realm.repository.IContactRepository;
import com.droidteahouse.contacts.realm.repository.IGroupRepository;
import com.droidteahouse.contacts.realm.repository.impl.ContactRepository;
import com.droidteahouse.contacts.realm.repository.impl.GroupRepository;
import com.droidteahouse.contacts.view.activity.ContactsActivity;

import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by sgv on 03.11.15.
 */
public class ContactPresenter implements IContactPresenter {
  private ContactsActivity view;
  private IContactRepository.OnDeleteContactCallback onDeleteContactCallback;
  private IContactRepository.OnSaveContactCallback onSaveContactCallback;
  private IContactRepository.OnEditContactCallback OnEditContactCallback;
  private IContactRepository.OnGetAllContactsCallback onGetAllContactsCallback;
  private IContactRepository.OnGetContactByIdCallback onGetContactByIdCallback;
  private IContactRepository.OnGetContactsCallback onGetContactsCallback;
  private IGroupRepository.OnGetGroupByIdCallback OnGetGroupByIdCallback;
  private IContactRepository contactRepository;
  private IGroupRepository groupRepository;
  private Handler uiHandler;

  public ContactPresenter(ContactsActivity view) {
    this.view = view;
    contactRepository = new ContactRepository();
    groupRepository = new GroupRepository();
    uiHandler = new Handler(Looper.getMainLooper());
  }

  @Override
  public void addContactByGroupId(final Contact contact, final String groupId) {
    contactRepository.addContactByGroupId(contact, groupId, onSaveContactCallback);
  }

  @Override
  public void editContactByGroupId(final Contact contact, String groupId) {
    contactRepository.editContactByGroupId(contact, OnEditContactCallback);
  }

  @Override
  public void deleteContactByPosition(int position) {
    contactRepository.deleteContactByPosition(position, onDeleteContactCallback);
  }

  @Override
  public void deleteContactById(String ContactId) {
    contactRepository.deleteContactById(ContactId, onDeleteContactCallback);
  }

  @Override
  public void getAllContacts() {
    contactRepository.getAllContacts(onGetAllContactsCallback);
  }

  @Override
  public void getAllContactsByGroupId(String id) {
    contactRepository.getAllContactsByGroupId(id, onGetContactsCallback);
  }

  @Override
  public void getContactById(String id) {
    contactRepository.getContactById(id, onGetContactByIdCallback);
  }

  @Override
  public void getGroupById(String id) {
    groupRepository.getGroupById(id, OnGetGroupByIdCallback);
  }

  @Override
  public void subscribeCallbacks() {
    onSaveContactCallback = new IContactRepository.OnSaveContactCallback() {
      @Override
      public void onSuccess(final String groupId) {
        uiHandler.post(new Runnable() {
          @Override
          public void run() {
            view.showMessage("Added");
            getAllContactsByGroupId(groupId);
          }
        });
      }

      @Override
      public void onError(String message) {
        view.showMessage(message);
      }
    };
    OnEditContactCallback = new IContactRepository.OnEditContactCallback() {
      @Override
      public void onSuccess(final String groupId) {
        uiHandler.post(new Runnable() {
          @Override
          public void run() {
            view.showMessage("Edited");
            getAllContactsByGroupId(groupId);
          }
        });
      }

      @Override
      public void onError(String message) {
        view.showMessage(message);
      }
    };
    onDeleteContactCallback = new IContactRepository.OnDeleteContactCallback() {
      @Override
      public void onSuccess() {
        view.showMessage("Deleted");
      }

      @Override
      public void onError(String message) {
        view.showMessage(message);
      }
    };
    onGetAllContactsCallback = new IContactRepository.OnGetAllContactsCallback() {
      @Override
      public void onSuccess(RealmResults<Contact> contacts) {
      }

      @Override
      public void onError(String message) {
        view.showMessage(message);
      }
    };
    onGetContactByIdCallback = new IContactRepository.OnGetContactByIdCallback() {
      @Override
      public void onSuccess(Contact contact) {
        view.showEditContactDialog(contact);
      }

      @Override
      public void onError(String message) {
      }
    };
    onGetContactsCallback = new IContactRepository.OnGetContactsCallback() {
      @Override
      public void onSuccess(final RealmList<Contact> contacts) {
        view.showContacts(contacts);
      }

      @Override
      public void onError(String message) {
        view.showMessage(message);
      }
    };
    OnGetGroupByIdCallback = new IGroupRepository.OnGetGroupByIdCallback() {
      @Override
      public void onSuccess(Group group) {
        view.updateToolbarTitle(group.getName());
      }

      @Override
      public void onError(String message) {
        view.showMessage(message);
      }
    }
    ;
  }

  @Override
  public void unSubscribeCallbacks() {
    onDeleteContactCallback = null;
    OnEditContactCallback = null;
    onSaveContactCallback = null;
    onGetAllContactsCallback = null;
    onGetContactByIdCallback = null;
    uiHandler = null;
  }
}
