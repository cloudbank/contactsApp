package com.droidteahouse.contacts.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.droidteahouse.contacts.R;
import com.droidteahouse.contacts.model.Group;
import com.droidteahouse.contacts.presenters.IGroupPresenter;
import com.droidteahouse.contacts.presenters.impl.GroupPresenter;
import com.droidteahouse.contacts.realm.table.RealmTable;
import com.droidteahouse.contacts.view.activity.base.BaseActivity;
import com.droidteahouse.contacts.view.adapters.GroupAdapter;
import com.droidteahouse.contacts.view.dialogs.AddGroupDialog;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

public class GroupActivity extends BaseActivity implements View.OnClickListener {
  private FloatingActionButton fbAdd;
  private RecyclerView rvGroups;
  private GroupAdapter adapter;
  private IGroupPresenter presenter;
  private List<Group> groups = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_groups);
    adapter = new GroupAdapter(groups);
    presenter = new GroupPresenter(this);
    initComponents();
    rvGroups.setAdapter(adapter);
  }

  @Override
  protected void onStart() {
    super.onStart();
    presenter.subscribeCallbacks();
    presenter.getAllGroups();
  }

  @Override
  protected void onStop() {
    super.onStop();
    presenter.unSubscribeCallbacks();
  }

  @Override
  protected void initComponents() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle(R.string.groups);
    setSupportActionBar(toolbar);
    fbAdd = (FloatingActionButton) findViewById(R.id.fab_add_group);
    fbAdd.setOnClickListener(this);
    initRecyclerListener();
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.fab_add_group: {
        showAddGroupDialog();
        break;
      }
    }
  }

  private void initRecyclerListener() {
    rvGroups = (RecyclerView) findViewById(R.id.rv_groups);
    rvGroups.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    rvGroups.setItemAnimator(new DefaultItemAnimator());
    ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
      @Override
      public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
      }

      @Override
      public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        presenter.deleteGroupById(groups.get(viewHolder.getAdapterPosition()).getId());
        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
      }
    });
    swipeToDismissTouchHelper.attachToRecyclerView(rvGroups);
  }

  public void showGroups(RealmResults<Group> r_groups) {
    groups.clear();
    groups.addAll(r_groups.subList(0, r_groups.size()));
    adapter.notifyDataSetChanged();
    adapter.setOnItemClickListener(new GroupAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(String id) {
        Intent intent = new Intent(getApplicationContext(), ContactsActivity.class);
        intent.putExtra(RealmTable.ID, id);
        startActivity(intent);
      }
    });
  }

  private void showAddGroupDialog() {
    final AddGroupDialog dialog = new AddGroupDialog();
    dialog.show(getSupportFragmentManager(), dialog.getClass().getName());
    dialog.setListener(new AddGroupDialog.OnAddGroupClickListener() {
      @Override
      public void onAddGroupClickListener(String GroupName) {
        dialog.dismiss();
        presenter.addGroup(GroupName);
      }
    });
  }
}
