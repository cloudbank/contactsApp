package com.droidteahouse.contacts.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sabine on 9/21/17.
 */
public class Address extends RealmObject implements Parcelable {
  //@todo add constructor for break on , or separate into et fields

  public Address(String street, String city, String zipcode) {
    this.setStreet(street);
    this.setCity(city);
    this.setZipcode(zipcode);
  }

  public Address() {
  }

  @PrimaryKey
  //@todo persist id
  private String id;
  private String street;
  private String city;
  private String zipcode;

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getZipcode() {
    return zipcode;
  }
//@todo set the id
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  @Override
  public void writeToParcel(Parcel out, int flags) {
    out.writeString(street);
    out.writeString(city);
    out.writeString(zipcode);
  }

  // Using the `in` variable, we can retrieve the values that
  // we originally wrote into the `Parcel`.  This constructor is usually
  // private so that only the `CREATOR` field can access.
  private Address(Parcel in) {
    street = in.readString();
    city = in.readString();
    zipcode = in.readString();
  }

  // In the vast majority of cases you can simply return 0 for this.
  // There are cases where you need to use the constant `CONTENTS_FILE_DESCRIPTOR`
  // But this is out of scope of this tutorial
  @Override
  public int describeContents() {
    return 0;
  }

  // After implementing the `Parcelable` interface, we need to create the
  // `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
  // Notice how it has our class specified as its type.
  public static final Parcelable.Creator<Address> CREATOR
      = new Parcelable.Creator<Address>() {
    // This simply calls our new constructor (typically private) and
    // passes along the unmarshalled `Parcel`, and then returns the new object!
    @Override
    public Address createFromParcel(Parcel in) {
      return new Address(in);
    }

    // We just need to copy this and change the type to match our class.
    @Override
    public Address[] newArray(int size) {
      return new Address[size];
    }
  };
}


