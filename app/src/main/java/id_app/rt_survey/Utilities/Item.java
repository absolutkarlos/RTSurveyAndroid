package id_app.rt_survey.Utilities;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Carlos_Lopez on 2/7/16.
 */
public class Item implements Parcelable,Serializable{

    public String id="test";
    public String orderNumber="test";
    public String createAt="test";
    public String idOrderStatus="test";
    public String nameOrderStatus="test";
    public String siteName="test";
    public String clientName="test";

    /*
    {"id":68,
    "orderNumber":"PA-0020-09",
    "createAt":"2016-06-23T08:52:04.14",
    "idOrderStatus":8
    ,"nameOrderStatus":"PREFACTIBILIDAD"
    ,"siteName":"Pocrí",
    "clientName":"Senacyt"}
    */

    public Item(String id,String orderNumber, String createAt, String idOrderStatus, String nameOrderStatus, String siteName, String clientName) {
        this.id=id;
        this.orderNumber = orderNumber;
        this.createAt= createAt;
        this.idOrderStatus = idOrderStatus;
        this.nameOrderStatus = nameOrderStatus;
        this.siteName = siteName;
        this.clientName = clientName;

    }


    protected Item(Parcel in) {
        id = in.readString();
        orderNumber = in.readString();
        createAt = in.readString();
        idOrderStatus = in.readString();
        nameOrderStatus = in.readString();
        siteName = in.readString();
        clientName = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(orderNumber);
        dest.writeString(createAt);
        dest.writeString(idOrderStatus);
        dest.writeString(nameOrderStatus);
        dest.writeString(siteName);
        dest.writeString(clientName);
    }
}
