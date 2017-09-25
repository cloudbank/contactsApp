package com.droidteahouse.contacts.view.dialogs;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.droidteahouse.contacts.R;
import com.droidteahouse.contacts.model.Address;
import com.droidteahouse.contacts.model.Contact;
import com.droidteahouse.contacts.presenters.impl.ContactPresenter;
import com.droidteahouse.contacts.tools.DateFormatter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sgv on 04.11.15.
 */
public class EditContactDialog extends DialogFragment implements View.OnClickListener {
  private EditText etFName, etLName, etEmail, etBirthday, etPhone, etStreet, etCity, etZipCode;
  private Date date;
  private Button btAdd;
  private OnEditContactClickListener listener;
  private ContactPresenter presenter;
  private String id, groupId, firstname,lastname, email, phone;
  private Date birthday;
  private ArrayList<Address> address;

  public static EditContactDialog newInstance(String id, String groupId, String fname, String lname, String email, String phone, ArrayList<Address> address, long birthday) {
    EditContactDialog frag = new EditContactDialog();
    Bundle args = new Bundle();
    args.putString("id", id);
    args.putString("groupId", groupId);
    args.putString("firstname", fname);
    args.putString("lastname", lname);
    args.putString("email", email);
    args.putString("phone", phone);
    args.putParcelableArrayList("address", (ArrayList<? extends Parcelable>) address);
    args.putLong("birthday", birthday);
    frag.setArguments(args);
    return frag;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialogStyle);
    setRetainInstance(true);
    id = getArguments().getString("id");
    groupId = getArguments().getString("groupId");
    firstname = getArguments().getString("firstname");
    lastname = getArguments().getString("lastname");
    email = getArguments().getString("email");
    phone = getArguments().getString("phone");
    address = getArguments().getParcelableArrayList("address");
    birthday = new Date(getArguments().getLong("birthday"));
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.dialog_add_contact, container);
    initComponents(view);
    return view;
  }

  private void initComponents(View view) {
    etFName = (EditText) view.findViewById(R.id.et_fname);
    etFName.setText(firstname);
    etLName = (EditText) view.findViewById(R.id.et_lname);
    etLName.setText(lastname);
    etEmail = (EditText) view.findViewById(R.id.et_email);
    etEmail.setText(email);
    etPhone = (EditText) view.findViewById(R.id.et_phone);
    etPhone.setText(phone);
    etStreet = (EditText) view.findViewById(R.id.et_street);
    //@todo handle multiple addresses
    etStreet.setText(address.size() > 0 ? address.get(0).getStreet() : "");
    etCity = (EditText) view.findViewById(R.id.et_city);
    etCity.setText(address.size() > 0 ? address.get(0).getCity() : "");
    etZipCode = (EditText) view.findViewById(R.id.et_zipcode);
    etZipCode.setText(address.size() > 0 ? address.get(0).getZipcode() : "");
    etBirthday = (EditText) view.findViewById(R.id.et_birthday);
    etBirthday.setText(DateFormatter.convertDateToString(birthday));
    btAdd = (Button) view.findViewById(R.id.bt_add);
    btAdd.setText("Save");
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
          Contact contact = new Contact();  //@todo null out these type of holders
          contact.setId(id);
          contact.setGroupId(groupId);
          contact.setFirstname(etFName.getText().toString());
          contact.setLastname(etLName.getText().toString());
          contact.setEmail(etEmail.getText().toString());
          contact.setPhone(etPhone.getText().toString());
          //@todo multiple addresses
          contact.getAddress().add(new Address(etStreet.getText().toString(), etCity.getText().toString(), etZipCode.getText().toString()));
          contact.setAddress(contact.getAddress());
          contact.setBirthday(DateFormatter.convertStringToDate(etBirthday.getText().toString()));
          listener.onEditContactClickListener(contact);
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
    }
  }

  private boolean isUserInfoValidate() {
    return !etFName.getText().toString().isEmpty() && !etLName.getText().toString().isEmpty() &&
        !etEmail.getText().toString().isEmpty() &&
        !etBirthday.getText().toString().isEmpty();
  }

  public void setListener(OnEditContactClickListener listener) {
    this.listener = listener;
  }

  public interface OnEditContactClickListener {
    void onEditContactClickListener(Contact contact);
  }
}
