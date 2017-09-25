package com.droidteahouse.contacts.app;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by sgv on 21.09.17.
 */
public class SimpleContactsApp extends Application {
  private static SimpleContactsApp instance;

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
    Realm.init(this);
    //RealmConfiguration config = new RealmConfiguration.Builder().build();
    //Realm.setDefaultConfiguration(config);
    RealmConfiguration config = new RealmConfiguration.Builder().modules(new com.droidteahouse.contacts.realm.module.SimpleContactsApp()).build();
    Realm.setDefaultConfiguration(config);
  }

  public static SimpleContactsApp getInstance() {
    return instance;
  }
}
