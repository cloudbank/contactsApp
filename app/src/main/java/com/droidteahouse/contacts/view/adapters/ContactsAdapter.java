package com.droidteahouse.contacts.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.droidteahouse.contacts.R;
import com.droidteahouse.contacts.app.SimpleContactsApp;
import com.droidteahouse.contacts.model.Contact;
import com.droidteahouse.contacts.tools.DateFormatter;

import java.util.List;

/**
 * Created by sgv on 03.11.15.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {
  private List<Contact> contacts;
  private OnItemClickListener onItemClickListener;

  public ContactsAdapter(List<Contact> contacts) {
    this.contacts = contacts;
  }

  @Override
  public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_contact, parent, false);
    return new ContactViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ContactViewHolder holder, int position) {
    //@todo stringbuffer or labels
    Contact contact = contacts.get(position);
    holder.tvFName.setText(contact.getFirstname());
    holder.tvLName.setText(contact.getLastname());
    String birthday = SimpleContactsApp.getInstance().getString(R.string.birthday) + " " + DateFormatter.convertDateToString(contact.getBirthday());
    holder.tvBirthday.setText(birthday);
    String email = SimpleContactsApp.getInstance().getString(R.string.email) + " " + contact.getEmail();
    holder.tvEmail.setText(email);
    String phone = SimpleContactsApp.getInstance().getString(R.string.phone) + " " + contact.getPhone();
    holder.tvPhone.setText(phone);
    String address =  SimpleContactsApp.getInstance().getString(R.string.address) + (contact.getAddressList() != null ? " " +contact.getAddressList().get(0).toString() : "");
    holder.tvAddress.setText(address);

  }

  @Override
  public int getItemCount() {
    return contacts.size();
  }

  public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView tvFName;
    private TextView tvLName;
    private TextView tvBirthday;
    private TextView tvEmail;
    private TextView tvPhone;
    private TextView tvAddress;

    public ContactViewHolder(View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);
      tvFName = (TextView) itemView.findViewById(R.id.tv_contact_fname);
      tvLName = (TextView) itemView.findViewById(R.id.tv_contact_lname);
      tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
      tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
      tvBirthday = (TextView) itemView.findViewById(R.id.tv_birthday);
      tvEmail = (TextView) itemView.findViewById(R.id.tv_email);
    }
    @Override
    public void onClick(View v) {
      Contact contact = contacts.get(getAdapterPosition());
      onItemClickListener.onItemClick(contact.getId());
    }
  }

  public interface OnItemClickListener {
    void onItemClick(String contactId);
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

}
