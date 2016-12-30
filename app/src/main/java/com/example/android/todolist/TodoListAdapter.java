package com.example.android.todolist;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.android.todolist.models.Todo;
import com.example.android.todolist.utils.UIUtils;

import java.util.List;

/**
 * Created by mitya on 12/13/2016.
 */

public class TodoListAdapter extends BaseAdapter {

    private MainActivity activity;
    //private Context context;
    private List<Todo> data;

    public TodoListAdapter ( MainActivity activity, List<Todo> data) {
        this.activity = activity;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }//得到信息知道整个list view有多少item

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }//要的是每个位置的数据，没什么用
    //此处若没有写成data.get(position)则会报错，object reference 为null

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //View view = LayoutInflater.from(context).inflate(R.layout.main_list_item, parent, false);
        //使用convertview进行优化,利用回收的view
        ViewHolder vh;
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.main_list_item, parent, false);
            //把ViewHolder的坑绑定到Textview上面，以便循环利用
            vh = new ViewHolder();
            vh.todoText = (TextView) convertView.findViewById(R.id.main_list_item_text);
            vh.doneCheckbox = (CheckBox) convertView.findViewById(R.id.main_list_item_check);
            convertView.setTag(vh);//把坑绑定到convertview上，只需最开始时候执行八次，之后显示时不需要每次都产生一个坑
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        
        final Todo todo = (Todo) getItem(position);
        vh.todoText.setText(todo.text);
        vh.doneCheckbox.setChecked(todo.done);
        UIUtils.setTextViewStrikeThrough(vh.todoText, todo.done);

        vh.doneCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                activity.updateTodo(position, isChecked);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TodoEditActivity.class);
                intent.putExtra(TodoEditActivity.KEY_TODO, todo);
                activity.startActivityForResult(intent, MainActivity.REQ_CODE_TODO_EDIT);
            }
        });
        //这是遍历的递归操作，layout越复杂找起来越慢
//        ((TextView) convertView.findViewById(R.id.main_list_item_text)).setText(todo.text);
        return convertView;
    }

//    private static class ViewHolder {
//        TextView todoText;
//        CheckBox doneCheckbox;
//    }
}
