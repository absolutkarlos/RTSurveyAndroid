package id_app.rt_survey.Tabs_Items;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;

import id_app.rt_survey.R;

/**
 * Created by Carlos_Lopez on 2/10/16.
 */
public class Tab_Pictures extends Fragment {


    private View view;
    private LinearLayout mLinearLayout;
    private ImageView mImageView;
    private int PICK_IMAGE_REQUEST = 1;
    private int AUX_INT=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.b_tab_picture,container,false);
        mLinearLayout=(LinearLayout)view.findViewById(R.id.lineal_frame);
        mImageView=(ImageView)view.findViewById(R.id.guide);
        setHasOptionsMenu(true);
        return view;

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
        inflater.inflate(R.menu.camara_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.Camara:

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);

                break;

            default:
                break;
        }

        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);


                LinearLayout.LayoutParams mParam;

                if(AUX_INT==0){

                    mImageView.setImageBitmap(bitmap);
                    AUX_INT=AUX_INT+1;

                }else{

                    mParam= (LinearLayout.LayoutParams) mImageView.getLayoutParams();
                    ImageView iv = new ImageView(getContext());
                    iv.setLayoutParams(mParam);
                    iv.setImageBitmap(bitmap);
                    mLinearLayout.addView(iv);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
