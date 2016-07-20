package evento.com.evento.view.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import evento.com.evento.R;
import evento.com.evento.controllers.UserController;
import evento.com.evento.utils.Category;
import evento.com.evento.utils.Commons;

public class SignupThreeActivity extends AppCompatActivity {

    InterestsAdapter dataAdapter = null;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_three);

        displayListView();
        addListenerOnSignupClick();
    }

    private void displayListView() {

        ArrayList<Category> categoryList = new ArrayList<Category>();
        Category category = new Category("Health",false);
        categoryList.add(category);
        category = new Category("Technology",false);
        categoryList.add(category);
        category = new Category("Environment",false);
        categoryList.add(category);
        category = new Category("Entertainment",false);
        categoryList.add(category);
        category = new Category("Cooking",false);
        categoryList.add(category);
        category = new Category("Community",false);
        categoryList.add(category);
        category = new Category("Other",false);
        categoryList.add(category);

        //create an ArrayAdaptar from the String Array
        dataAdapter = new InterestsAdapter(this,
                R.layout.item_category, categoryList);
        ListView listView = (ListView) findViewById(R.id.LVinterests);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Category category = (Category) parent.getItemAtPosition(position);;
            }
        });

    }

    /////////// Adapter
    private class InterestsAdapter extends ArrayAdapter<Category> {

        private ArrayList<Category> categoryList;
        public InterestsAdapter(Context context, int textViewResourceId,
                                ArrayList<Category> categoryList) {
            super(context, textViewResourceId, categoryList);
            this.categoryList = new ArrayList<Category>();
            this.categoryList.addAll(categoryList);
        }

        private class ViewHolder {
            TextView name;
            CheckBox cbox;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.item_category, null);

                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.cbox = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.cbox.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Category category = (Category) cb.getTag();
                        category.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Category category = categoryList.get(position);
            holder.cbox.setText(category.getName());
            holder.cbox.setChecked(category.isSelected());
            holder.cbox.setTag(category);

            return convertView;
        }
    }
    //////////////////

    public void addListenerOnSignupClick (){
        signup = (Button) findViewById(R.id.BTsignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> savedCategories = new ArrayList<String>();
                ArrayList<Category> categoryList = dataAdapter.categoryList;

                for (int i = 0; i < categoryList.size(); i++) {
                    Category category = categoryList.get(i);
                    if (category.isSelected())
                    {
                        savedCategories.add(category.getName());
                        evento.com.evento.model.beans.Category cat = new evento.com.evento.model.beans.Category();
                        cat.setName(category.getName());
                        Commons.currentActiveUser.addInterest(cat);
                    }
                }

                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                UserController.signUp(SignupThreeActivity.this, MainActivity.class);
            }
        });
    }
}
