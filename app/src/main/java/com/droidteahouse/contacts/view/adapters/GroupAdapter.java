package com.droidteahouse.contacts.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.droidteahouse.contacts.R;
import com.droidteahouse.contacts.model.Group;

import java.util.List;

/**
 * Created by sgv on 20.10.15.
 */
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
  private OnItemClickListener onItemClickListener;
  private List<Group> groups;

  public GroupAdapter(List<Group> groups) {
    this.groups = groups;
  }

  @Override
  public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_group, parent, false);
    return new GroupViewHolder(view);
  }

  @Override
  public void onBindViewHolder(GroupViewHolder holder, int position) {
    //@todo show # contacts
    holder.tvGroupName.setText(groups.get(position).getName());
  }

  @Override
  public int getItemCount() {
    return groups.size();
  }

  public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView tvGroupName;

    public GroupViewHolder(View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);
      tvGroupName = (TextView) itemView.findViewById(R.id.tv_name_group);
    }

    @Override
    public void onClick(View v) {
      Group group = groups.get(getAdapterPosition());
      onItemClickListener.onItemClick(group.getId());
    }
  }

  public interface OnItemClickListener {
    void onItemClick(String id);
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }
}
