package com.example.saif.gradetracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UpdateFragment extends Fragment {

    String TAG = "UpdateFragment";
    String valueOfGrade = "";

    int positionOfCredit,positionOfGrade;

    EditText edit_update_course,edit_update_credit,edit_update_grade;

    Spinner spinner_update_credit , spinner_update_grade;

    Button btn_update,btn_delete;

    DatabaseHelper mDatabaseHelper;
    Vibrator vibrator;


    String[] updatedCreditsArray =  new String[]{
            "0",
            "1",
            "1.5",
            "2",
            "2.5",
            "3",
    };

    String[] updatedGradesArray = new String[]{
            "A",
            "A-",
            "B+",
            "B",
            "B-",
            "C+",
            "C",
            "C-",
            "D+",
            "D",
            "F"
    };




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update,container,false);

        mDatabaseHelper = new DatabaseHelper(getContext());

        edit_update_course=(EditText)view.findViewById(R.id.course_name);

        spinner_update_credit = (Spinner)view.findViewById(R.id.spinner_update_credit);
        spinner_update_grade = (Spinner)view.findViewById(R.id.spinner_update_grade);

        btn_update = (Button)view.findViewById(R.id.btn_update);
        btn_delete = (Button)view.findViewById(R.id.btn_delete);




        //Adapter for updated course credit spinner
        ArrayAdapter<String> creditAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item
                ,updatedCreditsArray);
        creditAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_update_credit.setAdapter(creditAdapter);




        //Adapter for updated course grade spinner
        ArrayAdapter<String> gradeAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item
                ,updatedGradesArray);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_update_grade.setAdapter(gradeAdapter);

        vibrator = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);





        showData();
        updateData();
        deleteData();

        return view;
    }

    public void showData(){

        Bundle bundle = getArguments();

        String dataId ="";
        int courseId = -1;
        String courseName = "";
        String courseCredit = "";
        String courseGrade = "";







        if(bundle != null){
            dataId = bundle.getString("courseID");
            courseId = Integer.valueOf(dataId);
            courseName = bundle.getString("courseName");
            Log.d(TAG,"updateFragment: course name:"+courseName);
            courseCredit = bundle.getString("courseCredit");
            courseGrade = bundle.getString("courseGrade");
        }


        edit_update_course.setText(courseName);


        switch (courseCredit){
            case "0":
                positionOfCredit=0;
                break;
            case "1":
                positionOfCredit=1;
                break;
            case "1.5":
                positionOfCredit=2;
                break;
            case "2":
                positionOfCredit=3;
                break;
            case "2.5":
                positionOfCredit=4;
                break;
            case "3":
                positionOfCredit=5;
                break;
        }
        spinner_update_credit.setSelection(positionOfCredit);


        switch (courseGrade){
            case "4.0":
                positionOfGrade=0;
                break;
            case "3.7":
                positionOfGrade=1;
                break;
            case "3.3":
                positionOfGrade=2;
                break;
            case "3.0":
                positionOfGrade=3;
                break;
            case "2.7":
                positionOfGrade=4;
                break;
            case "2.3":
                positionOfGrade=5;
                break;
            case "2.0":
                positionOfGrade=6;
                break;
            case "1.7":
                positionOfGrade=7;
                break;
            case "1.3":
                positionOfGrade=8;
                break;
            case "1.0":
                positionOfGrade=9;
                break;
            case "0.0":
                positionOfGrade=10;
                break;

        }

        spinner_update_grade.setSelection(positionOfGrade);


    }

    public void updateData(){


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vibrator.vibrate(40);
                String updatedSpinnerGrade = spinner_update_grade.getSelectedItem().toString();
                String tempUpdatedSinnerGrade = spinner_update_grade.getSelectedItem().toString();

                switch (updatedSpinnerGrade){
                    case "A" :
                        valueOfGrade = "4.0";
                        break;
                    case "A-" :
                        valueOfGrade = "3.7";
                        break;
                    case "B+" :
                        valueOfGrade = "3.3";
                        break;
                    case "B" :
                        valueOfGrade = "3.0";
                        break;
                    case "B-" :
                        valueOfGrade = "2.7";
                        break;
                    case "C+" :
                        valueOfGrade = "2.3";
                        break;
                    case "C" :
                        valueOfGrade = "2.0";
                        break;
                    case "C-" :
                        valueOfGrade = "1.7";
                        break;
                    case "D+" :
                        valueOfGrade = "1.3";
                        break;
                    case "D" :
                        valueOfGrade = "1.0";
                        break;
                    case "F" :
                        valueOfGrade = "0.0";
                        break;
                }

               final String updatedCourseName = edit_update_course.getText().toString();
               final String updatedCourseCredit = spinner_update_credit.getSelectedItem().toString();
               final String updatedCourseGrade = valueOfGrade;

                if (updatedCourseName.equals("")){

                    warningTone();
                    showMessage("Warning!","Text Field should not be empty!");

                }
                else{






                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Course : "+updatedCourseName+"\n");
                    stringBuffer.append("Credit   : "+updatedCourseCredit+"\n");
                    stringBuffer.append("Grade   : "+tempUpdatedSinnerGrade+"\n");

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setCancelable(true);
                    builder.setTitle("Check Updated Data");
                    builder.setMessage(stringBuffer);
                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Bundle bundle = getArguments();

                            String dataId ="";
                            int courseId = -1;
                            if(bundle != null) {
                                dataId = bundle.getString("courseID");
                                Log.d(TAG,dataId);
                                courseId = Integer.valueOf(dataId);
                            }
                            mDatabaseHelper.updateData(courseId,updatedCourseName,updatedCourseCredit,updatedCourseGrade);
                            showMessageWithFragmentChange("Notice : ","Data Updated Successfully.",new GradesFragment());
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();

                }
            }
        });



    }

    public void deleteData(){

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vibrator.vibrate(40);
                String deleteCourseName = edit_update_course.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete "+deleteCourseName+"?");
                builder.setMessage("Once deleted, data cannot be backed again!");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bundle bundle = getArguments();

                        String dataId ="";
                        int courseId = -1;
                        if(bundle != null) {
                            dataId = bundle.getString("courseID");
                            Log.d(TAG,dataId);
                            courseId = Integer.valueOf(dataId);
                        }
                        mDatabaseHelper.deleteData(courseId);
                        showMessageWithFragmentChange("Notice : ","Data deleted successfully.",new GradesFragment());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });


    }

    //Customised toast
    private void toastData(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

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

    private void showMessageWithFragmentChange(String title, String message,final Fragment neededClass){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,neededClass).addToBackStack("my_fragment").commit();
            }
        });
        builder.show();
    }

    public void warningTone(){
        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 60);
        toneGen1.startTone(ToneGenerator.TONE_CDMA_SOFT_ERROR_LITE,150);
    }



}
