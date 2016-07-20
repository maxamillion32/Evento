package evento.com.evento.view.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import evento.com.evento.R;
import evento.com.evento.utils.Commons;
import evento.com.evento.utils.ImageUtils;

public class SignupTwoActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private final int DATE_DIALOG_ID = 0;
    private static final int CAMERA_REQUEST = 10;
    private static final int GALLERY_REQUEST = 11;
    private ImageView selectedImage;
    private Context context;
    private String imageURL;
    private RadioGroup radioGender;
    private RadioButton value;
    private Button continue2, interests;
    TextView date;
    EditText fname, lname;
    int year, month, day;
    String gender, governoratestr;
    Spinner location;
    ProgressBar imagebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_two);
        initView();
        imagebar.setVisibility(View.INVISIBLE);
        addListenerOnPicButton();
        addListenerOnContinueButton();
        addListenerOnDateButton();

    }

    public void initView (){
        day = 1;
        month = 1;
        year = 2000;
        imageURL = "";
        context = SignupTwoActivity.this;
        fname = (EditText) findViewById(R.id.ETfname);
        lname = (EditText) findViewById(R.id.ETlname);
        date = (TextView) findViewById(R.id.TVdate);
        selectedImage = (ImageView) findViewById(R.id.IVProfilePic);
        imagebar = (ProgressBar) findViewById(R.id.imagebar);
    }

    // Date
    private DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    SignupTwoActivity.this.year = year;
                    month = monthOfYear;
                    day = dayOfMonth;
                    updateDate();
                }
            };

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                DatePickerDialog dialog = new DatePickerDialog(this, pDateSetListener, year, month, day);
                dialog.getDatePicker().setMaxDate(new Date().getTime());
                return dialog;
        }
        return null;
    }

    private StringBuilder updateDate() {
        StringBuilder sb = new StringBuilder()
                .append(day).append("/")
                .append(month + 1).append("/")
                .append(year).append(" ");
        date.setText(sb);
        return sb;
    }

    public void addListenerOnDateButton() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
    }

    /////////////////// Image ////////////////
    public void addListenerOnPicButton() {
        selectedImage.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            public void onClick(View v) {
                android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(SignupTwoActivity.this, v);
                popupMenu.setOnMenuItemClickListener( SignupTwoActivity.this);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.show();
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        switch (item.getItemId()) {
            case R.id.galleryButton:
                return extractImageFromGallery();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST && data != null) {
                Bitmap cameraImage = (Bitmap) data.getExtras().get("data");
                selectedImage.setImageBitmap(cameraImage);
                Uri imageUri = ImageUtils.getImageUri(context, cameraImage);
                imageURL = imageUri.toString();

            } else if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST && data != null) {
                Uri imageUri = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(imageUri,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                selectedImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                imageURL = imageUri.toString();

            }
        } catch (Exception e) {
        }
    }

    private boolean extractImageFromGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST);

        /*Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_REQUEST);*/

        return true;
    }

    private boolean extractImageFromCamera(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
        return true;
    }

    // USER METHODS
    public String getName (){
        String name = fname.getText().toString().trim() +" "+
                lname.getText().toString().trim();
        return name;
    }

    public String getGender (){
        radioGender = (RadioGroup) findViewById(R.id.RGgender);
        int selectedId = radioGender.getCheckedRadioButtonId();
        value = (RadioButton) findViewById(selectedId);
        if (selectedId == R.id.RBmale) {
            gender = "Male";
        } else if (selectedId == R.id.RBfemale) {
            gender = "Female";
        } else {
            gender = "";
        }
        return gender;
    }

    public String getBirthdate (){
        String birthdate = "";
        birthdate=updateDate().toString();

        return birthdate;
    }

    public String getLocation (){
        location = (Spinner) findViewById(R.id.location);
        governoratestr = location.getSelectedItem().toString();
        return governoratestr;
    }

    public String getImage(){
        return imageURL;
    }

    public void addListenerOnContinueButton() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        continue2 = (Button) findViewById(R.id.BTcontinue2);
        continue2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if( fname.getText().toString().trim().equals("")){
                    fname.setError("First name is required!");
                }
                else if (lname.getText().toString().trim().equals("")) {
                    lname.setError("Last name is required");
                }
                else if (getGender() == "" ){
                    Toast.makeText(SignupTwoActivity.this, "Choose your gender" ,
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    Commons.currentActiveUser.setName(fname.getText().toString()+" "+lname.getText().toString());
                    Commons.currentActiveUser.setGender(gender);
                    Commons.currentActiveUser.setBirthdate(String.valueOf(day)+"/"
                            +String.valueOf(month)+"/"+String.valueOf(year));
                    Commons.currentActiveUser.setImageUrl(imageURL);
                    Commons.currentActiveUser.setLocation(governoratestr);
                    Intent i = new Intent(SignupTwoActivity.this , SignupThreeActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}
