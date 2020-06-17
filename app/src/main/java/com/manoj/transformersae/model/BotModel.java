package com.manoj.transformersae.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
@Entity
public class BotModel implements Parcelable, Comparable<BotModel> {

    @SerializedName("id")
    @NonNull
    @PrimaryKey
    private String id;

    @SerializedName("name")
    @ColumnInfo
    private String name;

    @SerializedName("strength")
    @ColumnInfo
    private int strength;

    @SerializedName("intelligence")
    @ColumnInfo
    private int intelligence;

    @SerializedName("speed")
    @ColumnInfo
    private int speed;

    @SerializedName("endurance")
    @ColumnInfo
    private int endurance;

    @SerializedName("rank")
    @ColumnInfo
    private int rank;

    @SerializedName("courage")
    @ColumnInfo
    private int courage;

    @SerializedName("firepower")
    @ColumnInfo
    private int firepower;

    @SerializedName("skill")
    @ColumnInfo
    private int skill;

    @SerializedName("team")
    @ColumnInfo
    private String team;

    @SerializedName("team_icon")
    @ColumnInfo
    private String teamIcon;

    protected BotModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        strength = in.readInt();
        intelligence = in.readInt();
        speed = in.readInt();
        endurance = in.readInt();
        rank = in.readInt();
        courage = in.readInt();
        firepower = in.readInt();
        skill = in.readInt();
        team = in.readString();
        teamIcon = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeInt(this.strength);
        parcel.writeInt(this.intelligence);
        parcel.writeInt(this.speed);
        parcel.writeInt(this.endurance);
        parcel.writeInt(this.rank);
        parcel.writeInt(this.courage);
        parcel.writeInt(this.firepower);
        parcel.writeInt(this.skill);
        parcel.writeString(this.team);
        parcel.writeString(this.teamIcon);
    }

    public BotModel() {

    }

    public static final Creator<BotModel> CREATOR = new Creator<BotModel>() {
        @Override
        public BotModel createFromParcel(Parcel in) {
            return new BotModel(in);
        }

        @Override
        public BotModel[] newArray(int size) {
            return new BotModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getCourage() {
        return courage;
    }

    public void setCourage(int courage) {
        this.courage = courage;
    }

    public int getFirepower() {
        return firepower;
    }

    public void setFirepower(int firepower) {
        this.firepower = firepower;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTeamIcon() {
        return teamIcon;
    }

    public void setTeamIcon(String teamIcon) {
        this.teamIcon = teamIcon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getOverAllRating() {
       return  (this.strength + this.intelligence + this.speed + this.endurance + this.firepower);
    }


    @Override
    public int compareTo(@NonNull BotModel botModel) {
        return botModel.getOverAllRating() - this.getOverAllRating();
    }
}
