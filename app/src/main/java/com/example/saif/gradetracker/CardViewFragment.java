package com.example.saif.gradetracker;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class CardViewFragment extends Fragment {

    CardView cardView_add_grade , cardView_show_grade, cardView_progress_graph,cardView_about_us;
    Vibrator vibrator;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_view,container,false);

        cardView_add_grade = (CardView)view.findViewById(R.id.card_view_add);
        cardView_show_grade = (CardView)view.findViewById(R.id.card_view_show_grade);
        cardView_progress_graph = (CardView)view.findViewById(R.id.card_view_progress_graph);
        cardView_about_us = (CardView)view.findViewById(R.id.card_view_about_us);
        vibrator = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        changeToFragments();

        return view;
    }


    public void changeToFragments(){
        cardView_add_grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(40);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,new AddFragment()).addToBackStack("my_fragment").commit();
            }
        });

        cardView_show_grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(40);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,new GradesFragment()).addToBackStack("my_fragment").commit();
            }
        });

        cardView_progress_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(40);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,new PiegraphFragment()).addToBackStack("my_fragment").commit();
            }
        });
        cardView_about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(40);
                Toast.makeText(getContext(), "Grade Tracker. version 1.0.1, Developed By CodeHunter", Toast.LENGTH_SHORT).show();
            }
        });



    }



}
