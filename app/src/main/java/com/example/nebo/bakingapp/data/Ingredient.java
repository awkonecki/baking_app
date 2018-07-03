package com.example.nebo.bakingapp.data;

/*
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
*/
import android.os.Parcel;
import android.os.Parcelable;

// @Entity (tableName="ingredient")
public class Ingredient implements Parcelable {

    // @PrimaryKey(autoGenerate = true)
    private int id;
    private float quantity;
    private String measure;
    private String ingredient;

    public Ingredient(int id, float quantity, String measure, String ingredient) {
        this.id = id;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public float getQuantity() {
        return quantity;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getMeasure() {
        return measure;
    }

    // @Ignore
    private Ingredient(Parcel src) {
        if (src != null) {
            this.quantity = src.readFloat();
            this.measure = src.readString();
            this.ingredient = src.readString();
        }
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {

        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            if (size < 1) {
                return new Ingredient[0];
            }
            else {
                return new Ingredient[size];
            }
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (dest != null) {
            dest.writeFloat(this.quantity);
            dest.writeString(this.measure);
            dest.writeString(this.ingredient);
        }
    }
}
