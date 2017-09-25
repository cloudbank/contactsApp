package com.droidteahouse.contacts.view.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.droidteahouse.contacts.R;

/**
 * Created by sgv on 05.11.15.
 */
public class AddGroupDialog extends DialogFragment implements View.OnClickListener {
  private EditText etGroupName;
  private Button btAddGroup;
  private OnAddGroupClickListener listener;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialogStyle);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.dialog_add_group, container);
    initComponents(view);
    return view;
  }

  private void initComponents(View view) {
    etGroupName = (EditText) view.findViewById(R.id.et_group_name);
    etGroupName.requestFocus();
    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    btAddGroup = (Button) view.findViewById(R.id.bt_add_group);
    btAddGroup.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bt_add_group: {
        if (isGroupInfoValid())
          listener.onAddGroupClickListener(etGroupName.getText().toString());
        break;
      }
    }
  }

  private boolean isGroupInfoValid() {
    return !etGroupName.getText().toString().isEmpty();
  }

  public void setListener(OnAddGroupClickListener listener) {
    this.listener = listener;
  }

  public interface OnAddGroupClickListener {
    void onAddGroupClickListener(String groupName);
  }
}
