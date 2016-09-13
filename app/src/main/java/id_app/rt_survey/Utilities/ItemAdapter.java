package id_app.rt_survey.Utilities;

import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import id_app.rt_survey.Item_Fragment;
import id_app.rt_survey.R;

/**
 * Created by Carlos_Lopez on 2/7/16.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.Holder> {

    private List<Item> data= Collections.emptyList();
    private LayoutInflater inflater;
    private AppCompatActivity activity;

    public void swapItems(List<Item> todolist){
        this.data = todolist;
        notifyDataSetChanged();
    }

    public ItemAdapter(List<Item> data,AppCompatActivity activity) {
        this.inflater = LayoutInflater.from(activity.getApplicationContext());
        this.activity=activity;
        this.data = data;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_row,parent,false);
        Holder holder=new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Item item=data.get(position);

        holder.clientName.setText(item.clientName);
        holder.siteName.setText(item.siteName);
        holder.orderNumber.setText(item.orderNumber);




        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
            Date d = sdf.parse(item.createAt);
            String formattedTime = output.format(d);
            holder.createAt.setText(formattedTime);

        } catch (ParseException e) {

            e.printStackTrace();
        }

        if(item.idOrderStatus.equals("1")){
            holder.idOrderStatus.setBackgroundColor(Color.parseColor("#006ff"));
        }else if(item.idOrderStatus.equals("2")){
            holder.idOrderStatus.setBackgroundColor(Color.parseColor("#a9a9a9"));
        }else if(item.idOrderStatus.equals("3")) {
            holder.idOrderStatus.setBackgroundColor(Color.parseColor("#cc33cc"));
        }else if(item.idOrderStatus.equals("4")) {
            holder.idOrderStatus.setBackgroundColor(Color.parseColor("#ffffff"));
        }else if(item.idOrderStatus.equals("5")){
            holder.idOrderStatus.setBackgroundColor(Color.parseColor("#000000"));
        }else if(item.idOrderStatus.equals("6")){
            holder.idOrderStatus.setBackgroundColor(Color.parseColor("#cc0000"));
        }else if(item.idOrderStatus.equals("7")){
            holder.idOrderStatus.setBackgroundColor(Color.parseColor("#ff6600"));
        }else if(item.idOrderStatus.equals("8")){
            holder.idOrderStatus.setBackgroundColor(Color.parseColor("#ffff33"));
        }else if(item.idOrderStatus.equals("9")) {
            holder.idOrderStatus.setBackgroundColor(Color.parseColor("#99ff00"));
        }else if(item.idOrderStatus.equals("10")){
            holder.idOrderStatus.setBackgroundColor(Color.parseColor("#006600"));
        }else if(item.idOrderStatus.equals("11")){
            holder.idOrderStatus.setBackgroundColor(Color.parseColor("#0000ff"));
        }

    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    class Holder extends RecyclerView.ViewHolder{

        ImageView idOrderStatus;
        TextView clientName;
        TextView createAt;
        TextView siteName;
        TextView orderNumber;

        public Holder(View itemView) {
            super(itemView);

            idOrderStatus = (ImageView) itemView.findViewById(R.id.idOrderStatus);
            clientName = (TextView) itemView.findViewById(R.id.clientName);
            createAt = (TextView) itemView.findViewById(R.id.createAt);
            siteName = (TextView) itemView.findViewById(R.id.siteName);
            orderNumber = (TextView) itemView.findViewById(R.id.orderNumber);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    Item_Fragment one = new Item_Fragment();
                    transaction.replace(R.id.sub_frame,one,"F1");
                    transaction.addToBackStack(null);
                    transaction.commit();

                    return true;
                }
            });

        }

    }

}
