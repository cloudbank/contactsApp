package com.droidteahouse.contacts.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.droidteahouse.contacts.R;
import com.droidteahouse.contacts.model.Contact;
import com.droidteahouse.contacts.presenters.IContactPresenter;
import com.droidteahouse.contacts.presenters.impl.ContactPresenter;
import com.droidteahouse.contacts.realm.table.RealmTable;
import com.droidteahouse.contacts.view.activity.base.BaseActivity;
import com.droidteahouse.contacts.view.adapters.ContactsAdapter;
import com.droidteahouse.contacts.view.dialogs.AddContactDialog;
import com.droidteahouse.contacts.view.dialogs.EditContactDialog;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by sgv on 03.11.15.
 */
public class ContactsActivity extends BaseActivity implements View.OnClickListener {
  private IContactPresenter presenter;
  private FloatingActionButton fbAdd;
  private RecyclerView rvContacts;
  private ContactsAdapter adapter;
  private List<Contact> contacts = new ArrayList<>();
  private String GroupId;
  private Handler handler;
  private HandlerThread handlerThread;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_contacts);
    presenter = new ContactPresenter(this);
    adapter = new ContactsAdapter(contacts);
    GroupId = getIntent().getStringExtra(RealmTable.ID);
    initComponents();
    rvContacts.setAdapter(adapter);
  }

  @Override
  protected void initComponents() {
    fbAdd = (FloatingActionButton) findViewById(R.id.fab_add_contact);
    fbAdd.setOnClickListener(this);
    initRecyclerListener();
  }

  public void updateToolbarTitle(String title) {
    getSupportActionBar().setTitle(getString(R.string.contacts) + " - " + title);
  }

  @Override
  protected void onStart() {
    super.onStart();
    presenter.subscribeCallbacks();
    presenter.getGroupById(GroupId);
    presenter.getAllContactsByGroupId(GroupId);
  }

  @Override
  protected void onStop() {
    super.onStop();
    presenter.unSubscribeCallbacks();
    //@todo should this be weakref or what should, static runnabless
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    //@todo handlercleanup versus onstop
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.fab_add_contact: {
        showAddContactDialog();
        break;
      }
    }
  }

  private void initRecyclerListener() {
    rvContacts = (RecyclerView) findViewById(R.id.rv_contacts);
    rvContacts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    rvContacts.setItemAnimator(new DefaultItemAnimator());
    ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
      @Override
      public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
      }

      @Override
      public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        presenter.deleteContactById(contacts.get(viewHolder.getAdapterPosition()).getId());
        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
      }
    });
    swipeToDismissTouchHelper.attachToRecyclerView(rvContacts);
  }

  private void showAddContactDialog() {
    final AddContactDialog dialog = new AddContactDialog();
    dialog.show(getSupportFragmentManager(), dialog.getClass().getName());
    dialog.setListener(new AddContactDialog.OnAddContactClickListener() {
      @Override
      public void onAddContactClickListener(Contact contact) {
        dialog.dismiss();
        presenter.addContactByGroupId(contact, GroupId);
      }
    });
  }

  public void showEditContactDialog(Contact contact) {
    final EditContactDialog dialog = EditContactDialog.newInstance(contact.getId(), contact.getGroupId(), contact.getFirstname(), contact.getLastname(), contact.getEmail(), contact.getPhone(), contact.getAddress(), contact.getBirthday().getTime());
    dialog.show(getSupportFragmentManager(), dialog.getClass().getName());
    dialog.setListener(new EditContactDialog.OnEditContactClickListener() {
      @Override
      public void onEditContactClickListener(Contact contact) {
        dialog.dismiss();
        //@// TODO:
        presenter.editContactByGroupId(contact, contact.getGroupId());

      }
    });
  }

  public void showContacts(RealmList<Contact> r_contacts) {
    contacts.clear();
    contacts.addAll(r_contacts);
    adapter.notifyDataSetChanged();
    adapter.setOnItemClickListener(new ContactsAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(String contactId) {
        presenter.getContactById(contactId);
      }
    });
  }
}
