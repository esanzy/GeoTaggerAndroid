package com.msk.geotagger;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 *
 */
public class HistoryFragment extends Fragment {


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*
         * since 2014-05-09
         * update 2014-05-09 이준원
         * reference http://sharepid.tistory.com/951
         */
        // 히스토리 리스트

        View v = getView();

        ArrayList<String> historyList = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, historyList);

        ListView historyListView = (ListView)v.findViewById(R.id.historyList);
        historyListView.setAdapter(adapter);
        historyListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        DBAdapter dba = new DBAdapter(getActivity());
        List<Location> locList = dba.selectAllLocation();


        for(int i = 0; i  < locList.size(); i++)
        {
            historyList.add(locList.get(i).getDesc());
        }
    }
}
