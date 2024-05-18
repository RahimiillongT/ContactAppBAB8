package com.example.contactapppam6.helper;

import androidx.recyclerview.widget.DiffUtil;

import com.example.contactapppam6.database.ContactModel;

import java.security.PublicKey;
import java.util.List;

public class DiffCallback extends DiffUtil.Callback {
    private final List<ContactModel> oldList;
    private final List<ContactModel> newList;

    public DiffCallback(List<ContactModel> oldList, List<ContactModel> newList){
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize(){
        return oldList.size();
    }

    @Override
    public int getNewListSize(){
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition){
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition){
        final ContactModel oldContact = oldList.get(oldItemPosition);
        final ContactModel newContact = newList.get(newItemPosition);
        return oldContact.getName().equals(newContact.getName())
                && oldContact.getNumber().equals(newContact.getNumber())
                && oldContact.getGroup().equals(newContact.getGroup())
                && oldContact.getInstagram().equals(newContact.getInstagram());
    }
}
