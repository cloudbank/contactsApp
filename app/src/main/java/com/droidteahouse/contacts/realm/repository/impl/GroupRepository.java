package com.droidteahouse.contacts.realm.repository.impl;

import com.droidteahouse.contacts.model.Group;
import com.droidteahouse.contacts.realm.repository.IGroupRepository;
import com.droidteahouse.contacts.realm.table.RealmTable;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by sgv on 16.10.15.
 */
public class GroupRepository implements IGroupRepository {
  //@todo add execute here
  @Override
  public void addGroup(Group group, OnAddGroupCallback callback) {
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    String id = UUID.randomUUID().toString();
    Group u = realm.createObject(Group.class,id);
    //u.setId(UUID.randomUUID().toString());
    u.setName(group.getName());
    realm.commitTransaction();
    if (callback != null)
      callback.onSuccess();
  }
  //@todo add execute here
  @Override
  public void deleteGroupById(String Id, OnDeleteGroupCallback callback) {
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    Group group = realm.where(Group.class).equalTo(RealmTable.ID, Id).findFirst();
    group.deleteFromRealm();
    realm.commitTransaction();
    if (callback != null)
      callback.onSuccess();
  }

  //@todo add edit group here

  @Override
  public void deleteGroupByPosition(int position, OnDeleteGroupCallback callback) {
    Realm realm = Realm.getDefaultInstance();
    realm.beginTransaction();
    RealmQuery<Group> query = realm.where(Group.class);
    RealmResults<Group> results = query.findAll();
    results.remove(position);
    realm.commitTransaction();
    if (callback != null)
      callback.onSuccess();
  }

  @Override
  public void getGroupById(String id, OnGetGroupByIdCallback callback) {
    Realm realm = Realm.getDefaultInstance();
    Group result = realm.where(Group.class).equalTo(RealmTable.ID, id).findFirst();
    if (callback != null)
      callback.onSuccess(result);
  }

  //@todo add execute here
  @Override
  public void getAllGroups(OnGetAllGroupsCallback callback) {
    Realm realm = Realm.getDefaultInstance();
    RealmQuery<Group> query = realm.where(Group.class);
    RealmResults<Group> results = query.findAll();
    if (callback != null)
      callback.onSuccess(results);
  }
}
