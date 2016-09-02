package id_app.rt_survey.Utilities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Carlos_Lopez on 2/7/16.
 */
public class Item implements Serializable {

    public String name="test";
    public String locate="test";
    public String date="test";
    public String color="test";
    public String order_name="test";

    public Item(String color, String date, String locate, String name, String order_name) {

        this.color = color;
        this.date = date;
        this.locate = locate;
        this.name = name;
        this.order_name = order_name;

    }
}
