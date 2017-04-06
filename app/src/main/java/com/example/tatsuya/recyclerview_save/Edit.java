package com.example.tatsuya.recyclerview_save;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class Edit extends AppCompatActivity {
    String titleText;
    String remarkText;
    Boolean testCheckboolean;
    Boolean homeworkcheckboolean;

    Boolean sendHomeWork;
    Boolean sendTest;

    EditText titleEdit;
    EditText remarkEdit;


    Intent intent;

    Button testcheck;
    Button homeworkcheck;

    private static final int RESULTCODE=10;
    private static final int REQUESTEDITCODE=1000;
    private static final int REQUESTDETAILCODE=1001;
    private static  String intentTitle="title";
    private static  String intentCheckTest="test";
    private static  String intentCheckHomeWork="homework";
    private static  String intentRemark="remark";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleEdit=(EditText)findViewById(R.id.edit);

        intent=getIntent();
        titleText=intent.getStringExtra(intentTitle);
        remarkText=intent.getStringExtra(intentRemark);
        testCheckboolean=intent.getBooleanExtra(intentCheckTest,false);
        homeworkcheckboolean=intent.getBooleanExtra(intentCheckHomeWork,false);

        titleEdit.setText(titleText);

    }

    public void backJump(){
        intent.putExtra(intentTitle,titleEdit.getText().toString());
        intent.putExtra(intentCheckTest,sendTest);
        intent.putExtra(intentCheckHomeWork,sendHomeWork);
        intent.putExtra(intentRemark,remarkEdit.getText().toString());
        setResult(RESULTCODE,intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        backJump();
    }

}

