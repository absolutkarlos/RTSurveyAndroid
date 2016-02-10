package id_app.rt_survey.Utilities;

import java.util.Date;

/**
 * Created by Carlos_Lopez on 2/7/16.
 */
public class Item {

    public String name;
    public String locate;
    public String date;
    public String color;

    public Item(String color, String date, String locate, String name) {
        this.color = color;
        this.date = date;
        this.locate = locate;
        this.name = name;
    }


}
