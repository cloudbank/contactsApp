package com.droidteahouse.contacts.model;

import java.util.ArrayList;
import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by sgv on 14.10.15.
 */
public class Contact extends RealmObject {
  @PrimaryKey
  private String id;
  @Required
  private String groupId;
  @Required
  private String firstname;
  @Required
  private String lastname;
  @Required
  private Date birthday;
  @Required
  private String email; // 1 or more
  public RealmList<Address> addressList = new RealmList<>();
  @Required
  private String phone;  //1 or more
  @Ignore
  private ArrayList<Address> address = new ArrayList<Address>(); //0 or more

  public ArrayList<Address> getAddress() {
    return address;
  }

  public void setAddress(ArrayList list) {
    address = list;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public RealmList getAddressList() {
    return addressList;
  }

  public void setAddressList(RealmList addressList) {
    this.addressList = addressList;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }
}


