package com.example.tatsuya.recyclerview_save;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ArrayList Data;
    private RecyclerView saveRecycleView;
    private ListAdapter adapter;
    private boolean checkTest;
    private boolean checkHomeWork;
    private String title;
    private String remark;
    private String list;
    FloatingActionButton addButton;
    SharedPreferences saveData;
    SharedPreferences.Editor saveEditor;
    JSONArray saveListData;
    HashMap<String,JSONArray> map;
    JSONObject json;

    private static final int RESULTCODE=10;
    private static final int REQUESTEDITCODE=1000;
    private static final int REQUESTDETAILCODE=1001;
    private static  String intentTitle="title";
    private static  String intentCheckTest="test";
    private static  String intentCheckHomeWork="homework";
    private static  String intentRemark="remark";
    private static  String saveTitle="SaveTitle";
    private static  String saveRemark="SaveRemark";
    private static  String saveCheckTest="SaveCheckTest";
    private static  String saveCheckHomeWork="SaveCheckHomeWork";
    private static  String saveList="SaveList";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Data = new ArrayList<ListItem>();
        saveRecycleView = (RecyclerView) findViewById(R.id.oneday_list);

        adapter = new ListAdapter(this, Data);
        saveRecycleView.setAdapter(adapter);
        addButton = (FloatingActionButton) findViewById(R.id.addbutton);
        saveData =getSharedPreferences("Save", Context.MODE_PRIVATE);
        addButton.setOnClickListener(clickAddButton);
        adapter.setItemListClickListener(clickList);
        saveData= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        saveEditor=saveData.edit();






        getDataLoad();

        new ItemTouchHelper(listAction).attachToRecyclerView(saveRecycleView);

    }

    public void getDataLoad(){
        //saveData=getPreferences(MODE_PRIVATE);
        list=saveData.getString(saveList,"");
        Toast.makeText(MainActivity.this,"a"+list,Toast.LENGTH_LONG).show();
        if(!list.equals("")){
            try{

                saveListData=new JSONArray(list);
                for (int i=0;i<saveListData.length();i++){
                    Data.add(saveListData.getString(i));
                }
            }catch (Exception ex){

                ex.printStackTrace();
            }
        }
        title=saveData.getString(saveTitle,"");
        remark=saveData.getString(saveRemark,"");
        checkHomeWork=saveData.getBoolean(saveCheckTest,false);
        checkTest=saveData.getBoolean(saveCheckHomeWork,false);
    }

    public ItemTouchHelper.SimpleCallback listAction = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            adapter.move(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            adapter.removeAtPosition(viewHolder.getAdapterPosition());
        }
    };

    public View.OnClickListener clickList = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = saveRecycleView.getChildAdapterPosition(v);
           // startEdit(position);
        }
    };

    public View.OnClickListener clickAddButton=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //startEdit();
            SaveList();
            SaveData();
        }
    };

    public void showOneDayList(ArrayList<ListItem> oneDayPlanList) {
        saveRecycleView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    public void startEdit(){
        Intent editIntent=new Intent(getApplicationContext(),Edit.class);
        editIntent.putExtra(intentTitle,title);
        editIntent.putExtra(intentCheckTest,checkTest);
        editIntent.putExtra(intentCheckHomeWork,checkHomeWork);
        editIntent.putExtra(intentRemark,remark);
        startActivityForResult(editIntent,REQUESTEDITCODE);

    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);

        saveEditor=saveData.edit();

        switch (requestCode){
//            case REQUESTDETAILCODE:
//                if (resultCode==RESULTCODE){
//                    Toast.makeText(MainActivity.this,String.valueOf(requestCode),Toast.LENGTH_LONG).show();
//                    title=intent.getStringExtra(intentTitle);
//                    checkTest=intent.getBooleanExtra(intentCheckTest,false);
//                    checkHomeWork=intent.getBooleanExtra(intentCheckHomeWork,false);
//                    remark=intent.getStringExtra(intentRemark);
//                    SaveData();
//
//                }
//                break;

            case REQUESTEDITCODE:
                if (resultCode==RESULTCODE){
//                    Toast.makeText(MainActivity.this,String.valueOf(resultCode),Toast.LENGTH_LONG).show();
                    title=intent.getStringExtra(intentTitle);
                    checkTest=intent.getBooleanExtra(intentCheckTest,false);
                    checkHomeWork=intent.getBooleanExtra(intentCheckHomeWork,false);
                    remark=intent.getStringExtra(intentRemark);
                    SaveList();
                    SaveData();

                }
                break;
        }

    }

    public void SaveData(){
        saveListData=new JSONArray();
        for (int i=0;i<Data.size();i++){
            saveListData.put(Data.get(i));
        }
        map=new HashMap<String, JSONArray>();
        map.put("reg",saveListData);
        json=new JSONObject(map);

        Log.d("hoge", saveListData.toString());
        //Log.d("hoge", (saveListData == null) +"");



        Toast.makeText(MainActivity.this,"a"+json.toString(),Toast.LENGTH_LONG).show();

//        for(int i=0;i<Data.size();i++){
//            Toast.makeText(MainActivity.this,saveListData.toString(),Toast.LENGTH_LONG).show();
//            saveListData.put(Data.get(i));
//        }
        saveEditor.putString(saveList,json.toString()).commit();
//        saveEditor.putString(saveTitle,title).commit();
//        saveEditor.putString(saveRemark,remark).commit();
//        saveEditor.putBoolean(saveCheckHomeWork,checkHomeWork).commit();
//        saveEditor.putBoolean(saveCheckTest,checkTest).commit();
        saveEditor.apply();
    }

    public void SaveList(){
        title="aaa";
        ListItem listItem = new ListItem();
        listItem.setTitle(title);
        Data.add(listItem);
       // Log.d("hoge", Data.toString());
        showOneDayList(Data);
    }
}
