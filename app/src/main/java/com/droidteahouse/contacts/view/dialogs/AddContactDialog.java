package com.droidteahouse.contacts.view.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.droidteahouse.contacts.R;
import com.droidteahouse.contacts.model.Address;
import com.droidteahouse.contacts.model.Contact;
import com.droidteahouse.contacts.tools.DateFormatter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sgv on 04.11.15.
 */
public class AddContactDialog extends DialogFragment implements View.OnClickListener {
  private EditText etFName,etLName, etEmail, etBirthday, etPhone, etStreet, etCity, etZipCode;
  private Date date;
  private Button btAdd;
  private OnAddContactClickListener listener;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialogStyle);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.dialog_add_contact, container);
    initComponents(view);
    return view;
  }

  private void initComponents(View view) {
    etFName = (EditText) view.findViewById(R.id.et_fname);
    etLName = (EditText) view.findViewById(R.id.et_lname);
    etEmail = (EditText) view.findViewById(R.id.et_email);
    etPhone = (EditText) view.findViewById(R.id.et_phone);
    etStreet = (EditText) view.findViewById(R.id.et_street);
    etCity = (EditText) view.findViewById(R.id.et_city);
    etZipCode = (EditText) view.findViewById(R.id.et_zipcode);
    etBirthday = (EditText) view.findViewById(R.id.et_birthday);
    btAdd = (Button) view.findViewById(R.id.bt_add);
    etFName.requestFocus();
    getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    btAdd.setOnClickListener(this);
    etBirthday.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bt_add: {
        if (isUserInfoValidate()) {
          Contact contact = new Contact();
          contact.setFirstname(etFName.getText().toString());
          contact.setLastname(etLName.getText().toString());
          contact.setEmail(etEmail.getText().toString());
          contact.setPhone(etPhone.getText().toString());
          //@todo multiple addresses
          ArrayList list = new ArrayList();
          list.add(new Address(etStreet.getText().toString(), etCity.getText().toString(), etZipCode.getText().toString()));
          contact.setAddress(list);
          contact.setBirthday(date);
          listener.onAddContactClickListener(contact);
        }
        break;
      }
      case R.id.et_birthday: {
        Calendar now = Calendar.getInstance();
        final DatePickerDialog d = DatePickerDialog.newInstance(
            new DatePickerDialog.OnDateSetListener() {
              @Override
              public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
                Calendar checkedCalendar = Calendar.getInstance();
                checkedCalendar.set(year, monthOfYear, dayOfMonth);
                date = checkedCalendar.getTime();
                etBirthday.setText(DateFormatter.convertDateToString(date));
              }
            },
            now.get(Calendar.YEAR),
            now.get(Calendar.MONTH),
            now.get(Calendar.DAY_OF_MONTH)
        );
        d.setMaxDate(now);
        d.show((getActivity()).getFragmentManager(), this.getClass().getName());
        break;
      }
      case R.id.edit_contact: {
        Toast.makeText(getActivity().getApplicationContext(), "edit", Toast.LENGTH_LONG).show();

        break;
      }
    }
  }

  private boolean isUserInfoValidate() {
    return !etFName.getText().toString().isEmpty() && !etLName.getText().toString().isEmpty() &&
        !etEmail.getText().toString().isEmpty() &&
        !etBirthday.getText().toString().isEmpty();
  }

  public void setListener(OnAddContactClickListener listener) {
    this.listener = listener;
  }

  public interface OnAddContactClickListener {
    void onAddContactClickListener(Contact contact);
  }
}
