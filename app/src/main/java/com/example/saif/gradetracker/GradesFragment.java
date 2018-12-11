package com.example.saif.gradetracker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GradesFragment extends Fragment {


    private static final String TAG = "ListDataActivity" ;

    DatabaseHelper databaseHelper;
    private ListView listView;
    String gradeToValueConvert="";
    TextView emptyText;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_grades,container,false);

        listView = (ListView)view.findViewById(R.id.listView);
        emptyText = (TextView)view.findViewById(R.id.empty_text);

        databaseHelper = new DatabaseHelper(getContext());


        populateListView();


        return view;
    }


    public void populateListView(){
        Log.d(TAG,"populateListView: Displaying data in the ListView");

        //get data and append the list
        final Cursor data = databaseHelper.getData();

        final ArrayList<String> listData = new ArrayList<>();

        while(data.getCount()==0){
           // showMessageWithFragmentChange("Notice : ","No Course Added",new HomeFragment());
            emptyText.setText("No Course Added");
            return;
        }

        while (data.moveToNext()) {
            //listData.add("Course: "+data.getString(1)+"\t\t\t\t"+"Credit: "+ data.getString(2)+"\t\t\t\t"+"Grade: "+data.getString(3));
            listData.add(data.getString(1));
        }

        ListAdapter adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,listData);


        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String course_name = parent.getItemAtPosition(position).toString();
                Log.d(TAG,"onItemClick: You Clicked on "+course_name);

                Cursor data = databaseHelper.getAllItemOf(course_name);

                int itemID = -1;
                String courseName = "";
                String courseCredit= "";
                String courseGrade ="";

                StringBuffer buffer = new StringBuffer();
                while(data.moveToNext()){
                    Log.d(TAG,"onWhilelOOP: searching and retrieve data of  "+course_name);
                    itemID = data.getInt(0);
                    courseName = data.getString(1);
                    courseCredit = data.getString(2);
                    courseGrade = data.getString(3);

                    //convert from grade-value to grade-letter

                    switch (courseGrade){
                        case "4.0" :
                            gradeToValueConvert = "A";
                            break;
                        case "3.7" :
                            gradeToValueConvert = "A-";
                            break;
                        case "3.3" :
                            gradeToValueConvert = "B+";
                            break;
                        case "3.0" :
                            gradeToValueConvert = "B";
                            break;
                        case "2.7" :
                            gradeToValueConvert = "B-";
                            break;
                        case "2.3" :
                            gradeToValueConvert = "C+";
                            break;
                        case "2.0" :
                            gradeToValueConvert = "C";
                            break;
                        case "1.7" :
                            gradeToValueConvert = "C-";
                            break;
                        case "1.3" :
                            gradeToValueConvert = "D+";
                            break;
                        case "1.0" :
                            gradeToValueConvert = "D";
                            break;
                        case "0.0" :
                            gradeToValueConvert = "F";
                            break;
                    }

                    //append all data to a string buffer
                    buffer.append("Course : "+courseName+"\n");
                    buffer.append("Credit   : "+courseCredit+"\n");
                    buffer.append("Grade   : "+gradeToValueConvert+"\n");



                }

                if (itemID>-1){

                    showMessage("Course Details",buffer.toString());
                }
                else{
                    Log.d(TAG,"Associated data not found");
                }



            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String data_course_name = parent.getItemAtPosition(position).toString();
                Log.d(TAG,"onItemLongClick: You Clicked on "+data_course_name);

                Cursor data = databaseHelper.getAllItemOf(data_course_name);

                int dataId = -1;
                String dataCourseName = "";
                String dataCourseCredit = "";
                String dataCourseGrade = "";

                while(data.moveToNext()){
                    Log.d(TAG,"on While lOOP on long Press: searching and retrieve data of  "+data_course_name);
                    dataId = data.getInt(0);
                    dataCourseName = data.getString(1);
                    dataCourseCredit = data.getString(2);
                    dataCourseGrade = data.getString(3);


                    Log.d(TAG,"on While lOOP on Long Click ok  id : " +dataId);

                }

                Bundle b =new Bundle();

                b.putString("courseID",String.valueOf(dataId));
                Log.d(TAG,String.valueOf(dataId));
                b.putString("courseName",dataCourseName);
                Log.d(TAG,"ok :"+dataCourseName);
                b.putString("courseCredit",dataCourseCredit);
                b.putString("courseGrade",dataCourseGrade);

                UpdateFragment updateFragment = new UpdateFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,updateFragment).addToBackStack("my_fragment").commit();


                updateFragment.setArguments(b);

                return true;
            }
        });

    }

    //Customised toast
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

    private void showMessageWithFragmentChange(String title, String message,final Fragment neededClass){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,neededClass).commit();
            }
        });
        builder.show();
    }
}
