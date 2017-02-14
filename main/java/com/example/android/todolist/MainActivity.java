package com.example.android.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.android.todolist.models.Todo;
import com.example.android.todolist.utils.ModelUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int REQ_CODE_TODO_EDIT = 100;

    private static final String TODOS = "todos";

    private TodoListAdapter adapter;
    private List<Todo> todos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("yao", "clicked");
                Intent intent = new Intent(MainActivity.this, TodoEditActivity.class);
                startActivityForResult(intent, REQ_CODE_TODO_EDIT);
            }
        });
        //setupUI(mockData());//这一步把view同步显示到界面上
        loadData();

        adapter = new TodoListAdapter(this, todos);
        ((ListView) findViewById(R.id.main_list_view)).setAdapter(adapter);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_TODO_EDIT && resultCode == Activity.RESULT_OK) {
            String todoId = data.getStringExtra(TodoEditActivity.KEY_TODO_ID);
            if (todoId != null) {
                deleteTodo(todoId);
            } else {
                Todo todo = data.getParcelableExtra(TodoEditActivity.KEY_TODO);
                updateTodo(todo);
            }
        }
    }

    private void updateTodo(Todo todo) {
        boolean found = false;
        for (int i = 0; i < todos.size(); i++) {
            Todo item = todos.get(i);
            if (TextUtils.equals(item.id, todo.id)) {
                found = true;
                todos.set(i, todo);
                break;
            }
        }

        if (!found) {
            todos.add(todo);
        }

        adapter.notifyDataSetChanged();
        ModelUtils.save(this, TODOS, todos);
    }
    //在TodoListAdapter中被调用，更新checkbox状态，public是为了可以被调用
    public void updateTodo(int index, boolean done) {
        todos.get(index).done = done;

        adapter.notifyDataSetChanged();//更新listview
        ModelUtils.save(this, TODOS, todos);
    }
    //-------------------------------------------------------------------

    public void deleteTodo(String todoId) {
        for (int i = 0; i < todos.size(); i++) {
            Todo item = todos.get(i);
            if (TextUtils.equals(item.id, todoId)) {
                todos.remove(i);
                break;
            }
        }

        adapter.notifyDataSetChanged();
        ModelUtils.save(this, TODOS, todos);
    }

    private void loadData() {
        todos = ModelUtils.read(this, TODOS, new TypeToken<List<Todo>>(){});
        if (todos == null) {
            todos = new ArrayList<>();
        }
    }
    //把数据转化为view------------------------------------------------------------------------------
//    private void setupUI(List<Todo> todos) {

//        ListView listView = (ListView) findViewById(R.id.main_list_view);
//        listView.setAdapter(new TodoListAdapter(this, todos));
//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.crappy_list);
//        linearLayout.removeAllViews();//删除已经显示过的内容

        //调用已经创建好的converter把todos转换成view，一个list本身就是context，所以用this就行
//        TodoListConverter converter = new TodoListConverter(this, todos);
//        for (int i = 0; i <todos.size(); i++) {
//            linearLayout.addView(converter.getView(i));
//        }
}
    //传统方法，耗时间，耗空间，因为UI为单线程，负责显示界面
//    private View getListItemView(Todo todo) {
//        View view = getLayoutInflater().inflate(R.layout.main_list_item, null);
//        ((TextView)view.findViewById(R.id.main_list_item_text)).setText(todo.text);
//        return view;
//    }
    //----------------------------------------------------------------------------------------------
    //
//    private List<Todo> mockData() {
//        List<Todo> list = new ArrayList<>();
//        for (int i = 0; i < 2000; ++i) {
//            list.add(new Todo("todo" + i, DateUtils.stringToDate("2015 7 29 0:00")));
//            //可能需要先创建一个DateUtils,以便此处导入
//        }
//        return list;
//    }

