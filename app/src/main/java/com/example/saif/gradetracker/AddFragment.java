package com.example.saif.gradetracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddFragment extends Fragment {

    DatabaseHelper mDatabaseHelper;
    Vibrator vibrator;

    Button btn_add ;
    EditText edit_course;
    Spinner spinner_credit, spinner_grade;
    String valueOfGrade = "";
    String[] credits = new String[]{
            "credit",
            "0",
            "1",
            "1.5",
            "2",
            "2.5",
            "3",
    };
    String[] grades = new String[]{
            "grade",
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
        View view =  inflater.inflate(R.layout.fragment_add,container,false);

        edit_course = (EditText)view.findViewById(R.id.edit_course_id);
        spinner_credit = (Spinner)view.findViewById(R.id.spinner_credit);
        spinner_grade = (Spinner)view.findViewById(R.id.spinner_grade);
        btn_add = (Button)view.findViewById(R.id.btn_add);

        mDatabaseHelper = new DatabaseHelper(getContext());

        vibrator = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        //Adapter for course credit spinner
        ArrayAdapter<String> creditAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item
                ,credits);
        creditAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_credit.setAdapter(creditAdapter);

        //Adapter for course grade spinner
        ArrayAdapter<String> gradeAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item
                ,grades);
        gradeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_grade.setAdapter(gradeAdapter);


       addData();


        return view;
    }




    public void addData(){

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vibrator.vibrate(40);
                if (edit_course.length() == 0 || spinner_credit.getSelectedItem().equals("credit") ||spinner_grade.getSelectedItem().equals("grade")){
                    showMessage("Warning!", "Please fill up every text field properly!");
                    warningTone();
                }
                else{

                    String gradeConvert = spinner_grade.getSelectedItem().toString();
                    switch (gradeConvert){
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

                    boolean inputData = mDatabaseHelper.insertData(edit_course.getText().toString(),spinner_credit.getSelectedItem().toString(),valueOfGrade);
                    if (inputData == true){
                        showMessage("Notice : ","Course added successfully");
                        edit_course.setText("");
                        spinner_credit.setSelection(0);
                        spinner_grade.setSelection(0);
                    }
                    else {
                        showMessage("Warning","Data not added");
                    }
                }


            }
        });
    }

    private void toastData(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
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

    public void warningTone(){
        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 60);
        toneGen1.startTone(ToneGenerator.TONE_CDMA_SOFT_ERROR_LITE,150);
    }
}
