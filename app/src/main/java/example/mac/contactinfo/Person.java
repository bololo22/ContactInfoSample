package example.mac.contactinfo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Android1 on 6/29/2015.
 */
public class Person implements Parcelable {
    private String nombre;
    private String apellido;

    public Person (){}

    public Person (Parcel in){
        nombre = in.readString();
        apellido = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(apellido);
    }

    public void readFromParcel(Parcel in){
        nombre = in.readString();
        apellido = in.readString();
    }

    public static final Parcelable.Creator<Person> CREATOR
            = new Parcelable.Creator<Person>() {
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
