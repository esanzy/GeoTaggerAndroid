package com.msk.geotagger.fragments;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.msk.geotagger.AddActivity;
import com.msk.geotagger.R;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class TagFragment extends Fragment
{
    private double latitude;
    private double longitude;

    public TagFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tag, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View v = getView();
        ImageView btnAdd = (ImageView) v.findViewById (R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                getActivity().startActivity(intent);
            }
        });

        getFragmentManager().beginTransaction().add(R.id.map_canvas, new MapFragment());
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }
}
