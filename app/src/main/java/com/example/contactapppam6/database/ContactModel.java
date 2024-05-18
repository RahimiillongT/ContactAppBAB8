package com.example.contactapppam6.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.PrimaryKey;

@Entity (tableName = "contacts")
public class ContactModel implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private int id;
    @ColumnInfo(name="name")
    private String name;
    @ColumnInfo(name="number")
    private String number;
    @ColumnInfo(name="group")
    private String group;
    @ColumnInfo(name="instagram")
    private String instagram;

    public ContactModel(String name, String number, String group, String instagram){
        this.name = name;
        this.number = number;
        this.group = group;
        this.instagram = instagram;
    }

    @Ignore
    public ContactModel() {
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number){
        this.number = number;
    }

    public String getGroup(){
        return group;
    }

    public void setGroup(String group){
        this.group = group;
    }

    public String getInstagram(){
        return instagram;
    }

    public void setInstagram(String instagram){
        this.instagram = instagram;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.number);
        dest.writeString(this.group);
        dest.writeString(this.instagram);
    }

    private ContactModel(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.number = in.readString();
        this.group = in.readString();
        this.instagram = in.readString();
    }
    public static final Parcelable.Creator<ContactModel> CREATOR = new Parcelable.Creator<ContactModel>() {
        @Override
        public ContactModel createFromParcel(Parcel source) {
            return new ContactModel(source);
        }
        @Override
        public ContactModel[] newArray(int size) {
            return new ContactModel[size];
        }
    };
}
