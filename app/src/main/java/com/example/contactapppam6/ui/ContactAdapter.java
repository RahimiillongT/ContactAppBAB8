package com.example.contactapppam6.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactapppam6.database.ContactModel;
import com.example.contactapppam6.databinding.ItemContactBinding;
import com.example.contactapppam6.helper.DiffCallback;
import com.example.contactapppam6.repository.ViewModelFactory;
import com.example.contactapppam6.R;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<ContactModel> contactList;
    private InsertUpdateViewModel insertUpdateViewModel;

    private final Context context;

    public ContactAdapter(Context context) {
        this.context = context;
        this.contactList = new ArrayList<>();
        this.insertUpdateViewModel = obtainViewModel((AppCompatActivity) context);
    }
    void setContacts(List<ContactModel> newContactList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallback(this.contactList, newContactList));
        this.contactList.clear();
        this.contactList.addAll(newContactList);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContactBinding binding = ItemContactBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ContactViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(contactList.get(position));
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        private final ItemContactBinding binding;

        public ContactViewHolder(@NonNull ItemContactBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ContactModel contact) {
            binding.tvName.setText(contact.getName());
            binding.tvNumber.setText(contact.getNumber());

            binding.tvCall.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + contact.getNumber()));
                if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) binding.getRoot().getContext(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                v.getContext().startActivity(intent);
            });

            binding.tvMessage.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("sms:" + contact.getNumber()));
                v.getContext().startActivity(intent);
            });

            binding.tvWhatsapp.setOnClickListener(v -> {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setPackage("com.whatsapp");
                String url = "https://api.whatsapp.com/send?phone=" + contact.getNumber() + "&text=";
                sendIntent.setData(Uri.parse(url));
                v.getContext().startActivity(sendIntent);
            });

            binding.contactLayout.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), DetailContactActivity.class);
                intent.putExtra(DetailContactActivity.EXTRA_CONTACT, contact);
                v.getContext().startActivity(intent);
            });

            binding.tvEdit.setOnClickListener(v->{
                Intent intent = new Intent(v.getContext(), InsertUpdateActivity.class);
                intent.putExtra(DetailContactActivity.EXTRA_CONTACT, contact);
                v.getContext().startActivity(intent);
            });
            binding.tvDelete.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Delete Contact");
                builder.setMessage("Are you sure you want to delete this contact?");
                builder.setPositiveButton("Delete", (dialog, which) -> {
                    if (insertUpdateViewModel != null) {
                        insertUpdateViewModel.delete(contact);
                        Toast.makeText(v.getContext(), "Contact deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                builder.show();
            });
        }
    }

    private static InsertUpdateViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, (ViewModelProvider.Factory) factory).get(InsertUpdateViewModel.class);
    }
}
