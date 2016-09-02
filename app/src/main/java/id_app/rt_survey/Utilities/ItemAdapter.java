package id_app.rt_survey.Utilities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import id_app.rt_survey.Item_Fragment;
import id_app.rt_survey.R;
import id_app.rt_survey.Survey_one;

/**
 * Created by Carlos_Lopez on 2/7/16.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.Holder> {

    private List<Item> data= Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
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
        holder.name.setText(item.name);
        holder.locate.setText(item.locate);
        holder.date.setText("...");
        holder.order_number.setText(item.order_name);
        holder.circle_status.setBackgroundColor(Color.parseColor(item.color));

    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    class Holder extends RecyclerView.ViewHolder{

        RoundedImageView circle_status;
        TextView name;
        TextView locate;
        TextView date;
        TextView order_number;

        public Holder(View itemView) {
            super(itemView);

            circle_status= (RoundedImageView)itemView.findViewById(R.id.circle_status);
            name=(TextView)itemView.findViewById(R.id.name);
            locate=(TextView)itemView.findViewById(R.id.locate);
            date=(TextView)itemView.findViewById(R.id.date);
            order_number=(TextView) itemView.findViewById(R.id.order_number);

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
