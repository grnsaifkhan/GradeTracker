package com.example.saif.gradetracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    TextView total_cgpa,total_credit,total_completed_course;
    DatabaseHelper mDatabaseHelper;




    double grade;// = {3.3,3.7,2.3,2.0};
    double tempGpa = 0;
    double cgpa;
    double credit;// = {1,2,1.5,3};
    double tempCredit;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home,container,false);

        total_cgpa = (TextView)view.findViewById(R.id.home_total_cgpa);
        total_credit = (TextView)view.findViewById(R.id.home_total_credit);
        total_completed_course = (TextView)view.findViewById(R.id.home_completed_course);


        mDatabaseHelper = new DatabaseHelper(getContext());
        countCreditAndCGPA();
        //makeRemark(cgpa);


        return view;
    }



        //------------- Credit and CGPA counting method ----------//
    private void countCreditAndCGPA() {


       ArrayList<String> getCredit = new ArrayList<String>();
       ArrayList<String> getGrade  = new ArrayList<String>();



       Cursor cursor =  mDatabaseHelper.getData();

        int i=0;
       while(cursor.moveToNext()){
           getCredit.add(cursor.getString(2));
           getGrade.add(cursor.getString(3));

           credit = Double.parseDouble(getCredit.get(i));
           grade = Double.parseDouble(getGrade.get(i));

           tempGpa += credit*grade;
           tempCredit += credit;

           i++;
       }

      /* for (int i=0;i<getCredit.size();i++){



       }*/

       cgpa = tempGpa/tempCredit;
       float tcgpa = (float) Math.round(cgpa * 100) / 100;

       //Total cgpa print
       String tempCgpa = String.valueOf(tcgpa);
       total_cgpa.setText(tempCgpa);

       //Total course count print
       String completedCourse = String.valueOf(getCredit.size());
       total_completed_course.setText(completedCourse);
       //Total credit print
       String totalCredit = String.valueOf(tempCredit);
       total_credit.setText(totalCredit);
       tempCredit=0;
       tempGpa = 0;


       //creditData = cursor.getString(2);
       //creditDataAsDouble = Double.parseDouble(creditData);
       //gradeDataAsDouble = Double.parseDouble(gradeData);





        /*tempCredit=0;
        for (int i=0 ;i<grade.length;i++){
            tempGpa += credit[i]*grade[i];
            tempCredit += credit[i];
        }
        cgpa = tempGpa/tempCredit;
        float tcgpa = (float) Math.round(cgpa * 100) / 100;
        String tempCgpa = String.valueOf(tcgpa);
        total_cgpa.setText(tempCgpa);
        String totalCredit = String.valueOf(tempCredit);
        total_credit.setText(totalCredit);*/

   }

    //----------------- remark method -----------------//

   /* private void makeRemark(double cgpa){
        if (cgpa >= 3.0 && cgpa<3.5){
            Toast.makeText(getContext(), "result is pleasurable", Toast.LENGTH_LONG).show();
        }
        else if (cgpa >= 3.5 && cgpa<=4.0 ){
            Toast.makeText(getContext(), "result is extraordinary", Toast.LENGTH_LONG).show();
        }
        else if (cgpa >= 2.5 && cgpa<3.0 ){
            Toast.makeText(getContext(), "result is good", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getContext(), "try hard to do well", Toast.LENGTH_LONG).show();
        }
    }*/

    private void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }


}
