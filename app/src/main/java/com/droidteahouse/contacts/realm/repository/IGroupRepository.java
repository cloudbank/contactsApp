package com.droidteahouse.contacts.realm.repository;

import com.droidteahouse.contacts.model.Group;

import io.realm.RealmResults;

/**
 * Created by sgv on 16.10.15.
 */
public interface IGroupRepository extends IBaseRepository {
  interface OnAddGroupCallback {
    void onSuccess();

    void onError(String message);
  }

  interface OnGetAllGroupsCallback {
    void onSuccess(RealmResults<Group> groups);

    void onError(String message);
  }

  interface OnGetGroupByIdCallback {
    void onSuccess(Group group);

    void onError(String message);
  }

  interface OnDeleteGroupCallback {
    void onSuccess();

    void onError(String message);
  }

  void addGroup(Group group, OnAddGroupCallback callback);

  void deleteGroupById(String Id, OnDeleteGroupCallback callback);

  void deleteGroupByPosition(int position, OnDeleteGroupCallback callback);

  void getAllGroups(OnGetAllGroupsCallback callback);

  void getGroupById(String id, OnGetGroupByIdCallback callback);
}
