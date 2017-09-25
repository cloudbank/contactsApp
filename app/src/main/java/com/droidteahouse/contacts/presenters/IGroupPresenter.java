package com.droidteahouse.contacts.presenters;

/**
 * Created by sgv on 19.10.15.
 */
public interface IGroupPresenter extends IBasePresenter {
  void addGroup(String groupName);

  void deleteGroup(int position);

  void deleteGroupById(String Id);

  void getGroupById(String id);

  void getAllGroups();
}
