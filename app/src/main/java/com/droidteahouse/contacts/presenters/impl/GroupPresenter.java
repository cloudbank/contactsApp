package com.droidteahouse.contacts.presenters.impl;

import com.droidteahouse.contacts.model.Group;
import com.droidteahouse.contacts.presenters.IGroupPresenter;
import com.droidteahouse.contacts.realm.repository.IGroupRepository;
import com.droidteahouse.contacts.realm.repository.impl.GroupRepository;
import com.droidteahouse.contacts.view.activity.GroupActivity;

import io.realm.RealmResults;

/**
 * Created by sgv on 19.10.15.
 */
public class GroupPresenter implements IGroupPresenter {
  private GroupActivity view;
  private IGroupRepository repository;
  private IGroupRepository.OnGetAllGroupsCallback getAllGroupsCallback;
  private IGroupRepository.OnAddGroupCallback addGroupCallback;
  private IGroupRepository.OnGetGroupByIdCallback getSpecialGroupCallback;
  private IGroupRepository.OnDeleteGroupCallback deleteGroupCallback;

  public GroupPresenter(GroupActivity view) {
    this.view = view;
    repository = new GroupRepository();
  }

  @Override
  public void getAllGroups() {
    repository.getAllGroups(getAllGroupsCallback);
  }

  @Override
  public void addGroup(String GroupName) {
    Group group = new Group(GroupName);
    repository.addGroup(group, addGroupCallback);
  }

  @Override
  public void getGroupById(String id) {
    repository.getGroupById(id, getSpecialGroupCallback);
  }

  @Override
  public void deleteGroup(int position) {
    repository.deleteGroupByPosition(position, deleteGroupCallback);
  }

  @Override
  public void deleteGroupById(String Id) {
    repository.deleteGroupById(Id, deleteGroupCallback);
  }

  @Override
  public void subscribeCallbacks() {
    getAllGroupsCallback = new IGroupRepository.OnGetAllGroupsCallback() {
      @Override
      public void onSuccess(RealmResults<Group> Groups) {
        view.showGroups(Groups);
      }

      @Override
      public void onError(String message) {
        view.showMessage(message);
      }
    };
    addGroupCallback = new IGroupRepository.OnAddGroupCallback() {
      @Override
      public void onSuccess() {
        view.showMessage("Added");
        getAllGroups();
      }

      @Override
      public void onError(String message) {
        view.showMessage(message);
      }
    };
    getSpecialGroupCallback = new IGroupRepository.OnGetGroupByIdCallback() {
      @Override
      public void onSuccess(Group group) {
      }

      @Override
      public void onError(String message) {
        view.showMessage(message);
      }
    };
    deleteGroupCallback = new IGroupRepository.OnDeleteGroupCallback() {
      @Override
      public void onSuccess() {
        view.showMessage("Deleted");
      }

      @Override
      public void onError(String message) {
        view.showMessage(message);
      }
    };
  }

  @Override
  public void unSubscribeCallbacks() {
    getAllGroupsCallback = null;
    addGroupCallback = null;
    getSpecialGroupCallback = null;
    deleteGroupCallback = null;
  }
}
