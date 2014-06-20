package com.msk.geotagger.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.msk.geotagger.R;
import com.msk.geotagger.model.Location;
import com.msk.geotagger.utils.DBAdapter;

import java.io.File;

/**
 * Created by junwon on 14. 6. 17.
 */
public class HistoryItemFragment extends Fragment
{
    private int rowid;
    private DBAdapter db;

    public HistoryItemFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View v = getView();

        db = new DBAdapter(getActivity());
        Location loc = db.selectLocation(rowid);

        // 질문 1
        final CheckBox chkQ1_1 = (CheckBox) v.findViewById(R.id.chk_q1_1); // Evangelism
        final CheckBox chkQ1_2 = (CheckBox) v.findViewById(R.id.chk_q1_2); // Training
        final CheckBox chkQ1_3 = (CheckBox) v.findViewById(R.id.chk_q1_3); // Mercy

        // 질문 2
        final CheckBox chkQ2_1 = (CheckBox) v.findViewById(R.id.chk_q2_1); // Youth / Children
        final CheckBox chkQ2_2 = (CheckBox) v.findViewById(R.id.chk_q2_2); // Campus Ministry
        final CheckBox chkQ2_3 = (CheckBox) v.findViewById(R.id.chk_q2_3); // Indigenous People
        final CheckBox chkQ2_4 = (CheckBox) v.findViewById(R.id.chk_q2_4); // Prison Ministry
        final CheckBox chkQ2_5 = (CheckBox) v.findViewById(R.id.chk_q2_5); // Prostitutes
        final CheckBox chkQ2_6 = (CheckBox) v.findViewById(R.id.chk_q2_6); // Orphans
        final CheckBox chkQ2_7 = (CheckBox) v.findViewById(R.id.chk_q2_7); // Woman
        final CheckBox chkQ2_8 = (CheckBox) v.findViewById(R.id.chk_q2_8); // Urban Ministry
        final CheckBox chkQ2_9 = (CheckBox) v.findViewById(R.id.chk_q2_9); // Hospital Ministry
        final CheckBox chkQ2_10 = (CheckBox) v.findViewById(R.id.chk_q2_10); // Media / Communications
        final CheckBox chkQ2_11 = (CheckBox) v.findViewById(R.id.chk_q2_11); // Community Development
        final CheckBox chkQ2_12 = (CheckBox) v.findViewById(R.id.chk_q2_12); // Bible Studies
        final CheckBox chkQ2_13 = (CheckBox) v.findViewById(R.id.chk_q2_13); // Church Planting
        final CheckBox chkQ2_14 = (CheckBox) v.findViewById(R.id.chk_q2_14); // Arts / Entertainment / Sports
        final CheckBox chkQ2_15 = (CheckBox) v.findViewById(R.id.chk_q2_15); // Counseling
        final CheckBox chkQ2_16 = (CheckBox) v.findViewById(R.id.chk_q2_16); // Healthcare
        final CheckBox chkQ2_17 = (CheckBox) v.findViewById(R.id.chk_q2_17); // Maintenance / Construction
        final CheckBox chkQ2_18 = (CheckBox) v.findViewById(R.id.chk_q2_18); // Research

        // 질문 3
        final EditText editText1 = (EditText) v.findViewById(R.id.editText1); // description

        // 질문 4
        final EditText editText2 = (EditText) v.findViewById(R.id.EditText2); // keywords (comma)

        // 질문 5 - 정보 수집 동의
        final CheckBox chkQ5 = (CheckBox) v.findViewById(R.id.chk_q5);

        final EditText editText3 = (EditText) v.findViewById(R.id.editText3); // email
        final EditText editText4 = (EditText) v.findViewById(R.id.editText4); // phone
        final EditText editText5 = (EditText) v.findViewById(R.id.editText5); // website

        final ImageView myImage = (ImageView) v.findViewById(R.id.myImage);


        chkQ1_1.setChecked(checkBool(loc.getEvanType()));
        chkQ1_1.setClickable(false);
        chkQ1_2.setChecked(checkBool(loc.getTrainType()));
        chkQ1_2.setClickable(false);
        chkQ1_3.setChecked(checkBool(loc.getMercyType()));
        chkQ1_3.setClickable(false);

        chkQ2_1.setChecked(checkBool(loc.getYouthType()));
        chkQ2_1.setClickable(false);
        chkQ2_2.setChecked(checkBool(loc.getCampusType()));
        chkQ2_2.setClickable(false);
        chkQ2_3.setChecked(checkBool(loc.getIndigenousType()));
        chkQ2_3.setClickable(false);
        chkQ2_4.setChecked(checkBool(loc.getPrisonType()));
        chkQ2_4.setClickable(false);
        chkQ2_5.setChecked(checkBool(loc.getProstitutesType()));
        chkQ2_5.setClickable(false);
        chkQ2_6.setChecked(checkBool(loc.getOrphansType()));
        chkQ2_6.setClickable(false);
        chkQ2_7.setChecked(checkBool(loc.getWomenType()));
        chkQ2_7.setClickable(false);
        chkQ2_8.setChecked(checkBool(loc.getUrbanType()));
        chkQ2_8.setClickable(false);
        chkQ2_9.setChecked(checkBool(loc.getHospitalType()));
        chkQ2_9.setClickable(false);
        chkQ2_10.setChecked(checkBool(loc.getMediaType()));
        chkQ2_10.setClickable(false);
        chkQ2_11.setChecked(checkBool(loc.getCommunityDevType()));
        chkQ2_11.setClickable(false);
        chkQ2_12.setChecked(checkBool(loc.getBibleStudyType()));
        chkQ2_12.setClickable(false);
        chkQ2_13.setChecked(checkBool(loc.getChurchPlantingType()));
        chkQ2_13.setClickable(false);
        chkQ2_14.setChecked(checkBool(loc.getArtsType()));
        chkQ2_14.setClickable(false);
        chkQ2_15.setChecked(checkBool(loc.getCounselingType()));
        chkQ2_15.setClickable(false);
        chkQ2_16.setChecked(checkBool(loc.getHealthcareType()));
        chkQ2_16.setClickable(false);
        chkQ2_17.setChecked(checkBool(loc.getConstructionType()));
        chkQ2_17.setClickable(false);
        chkQ2_18.setChecked(checkBool(loc.getResearchType()));
        chkQ2_18.setClickable(false);

        editText1.setText(loc.getDesc());
        editText1.setClickable(false);
        editText2.setText(loc.getTags());
        editText2.setClickable(false);

        chkQ5.setChecked(checkBool(loc.getContactConfirmed()));
        chkQ5.setClickable(false);

        editText3.setText(loc.getContactEmail());
        editText3.setClickable(false);
        editText4.setText(loc.getContactPhone());
        editText4.setClickable(false);
        editText5.setText(loc.getContactWebsite());
        editText5.setClickable(false);

        File imgFile = new  File(loc.getPhotoRealPath());
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            myImage.setImageBitmap(myBitmap);
            myImage.invalidate();
        }

        final RelativeLayout add_footer = (RelativeLayout) v.findViewById(R.id.add_footer);
        add_footer.setVisibility(View.GONE);

    }

    public void setRowid(int rowid)
    {
        this.rowid = rowid;
    }

    private boolean checkBool(int arg)
    {
        if(arg > 0)
        {
            return true;
        }

        else
        {
            return false;
        }
    }
}
