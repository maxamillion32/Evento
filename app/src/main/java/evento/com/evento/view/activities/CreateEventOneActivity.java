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
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;

import evento.com.evento.R;
import evento.com.evento.utils.Commons;
import evento.com.evento.utils.ImageUtils;

public class CreateEventOneActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private TextView sdate;
    private TextView edate;
    Context context;
    EditText eventname;
    private final int SDATE_DIALOG_ID = 0;
    private final int EDATE_DIALOG_ID = 1;
    int sday , smonth, syear , eday, emonth, eyear;
    private static final int CAMERA_REQUEST = 10;
    private static final int GALLERY_REQUEST = 11;
    private ImageView selectedImage;
    private String imageURL , locationstr;
    ProgressBar imagebar;
    Spinner eventLocation;
    EditText eventDescription;
    Button continuebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_one);
        InitView();
        addListenerOnStartDateButton();
        addListenerOnEndDateButton();
        addListenerOnPicButton();
        addListenerOnContinueButton();
    }

    private void InitView() {
        eventname = (EditText)findViewById(R.id.ETeventname);
        sdate = (TextView) findViewById(R.id.TVsdate);
        edate = (TextView) findViewById(R.id.TVedate);
        edate.setVisibility(View.INVISIBLE);
        imagebar = (ProgressBar) findViewById(R.id.imagebar1);
        imagebar.setVisibility(View.INVISIBLE);
        imageURL = "path";
        selectedImage = (ImageView) findViewById(R.id.IVcover);
        sday = 1;
        smonth = 1;
        syear = 2000;
        context = CreateEventOneActivity.this;
        eventDescription = (EditText) findViewById(R.id.ETeventdesc);
        continuebtn = (Button)findViewById(R.id.BTeventcontinue);
    }

    private DatePickerDialog.OnDateSetListener startDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    syear = year;
                    smonth = monthOfYear+1;
                    sday = dayOfMonth;
                    updateStartDate();
                }
            };
    private DatePickerDialog.OnDateSetListener endDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    eyear = year;
                    emonth = monthOfYear+1;
                    eday = dayOfMonth;
                    updateEndDate();
                }
            };



    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case SDATE_DIALOG_ID:
                DatePickerDialog sdialog = new DatePickerDialog(this, startDateSetListener, syear,
                        smonth, sday);
                sdialog.getDatePicker().setMinDate(new Date().getTime());
                return sdialog;

            case EDATE_DIALOG_ID:
                Toast.makeText(context, syear+" "+smonth+" "+sday,Toast.LENGTH_SHORT).show();
                DatePickerDialog edialog = new DatePickerDialog(this, endDateSetListener, eyear, emonth, eday);
                Calendar date = Calendar.getInstance();
                date.set(syear,smonth,sday);
                date.add (Calendar.DATE,0);
                edialog.getDatePicker().setMinDate(date.getTimeInMillis());
                return edialog;
        }
        return null;
    }

    private StringBuilder updateStartDate() {
        StringBuilder startDate = new StringBuilder().append(sday).append("/")
                .append(smonth + 1).append("/")
                .append(syear).append(" ");
        sdate.setText(startDate);
        return startDate;
    }

    private StringBuilder updateEndDate() {
        StringBuilder endDate = new StringBuilder()
                .append(eday).append("/")
                .append(emonth + 1).append("/")
                .append(eyear).append(" ");
        edate.setText(endDate);
        return endDate;
    }

    public void addListenerOnStartDateButton() {
        sdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(SDATE_DIALOG_ID);
                edate.setVisibility(View.VISIBLE);
            }
        });
    }

    public void addListenerOnEndDateButton(){
        edate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(EDATE_DIALOG_ID);
            }
        });
    }

    /// IMAGE //////

    public void addListenerOnPicButton (){
        selectedImage.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(CreateEventOneActivity.this, v);
                popupMenu.setOnMenuItemClickListener( CreateEventOneActivity.this);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.show();
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
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
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_REQUEST);

        return true;
    }

    private boolean extractImageFromCamera(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
        return true;
    }

    public String getEventLocation(){
        eventLocation = (Spinner) findViewById(R.id.eventlocation);
        locationstr = eventLocation.getSelectedItem().toString();
        Toast.makeText(context, locationstr , Toast.LENGTH_LONG).show();
        return locationstr;
    }

    public String getEventDesc(){
        return eventDescription.getText().toString();
    }

    public void addListenerOnContinueButton() {
        continuebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if( eventname.getText().toString().trim().equals("")){
                    eventname.setError("Enter Event Name");
                }
                else if (eventDescription.getText().toString().trim().equals("")) {
                    eventDescription.setError("Enter Event Description");
                }
                else {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                    Commons.startActiveEvent();
                    Commons.activeEvent.setName(eventname.getText().toString());
                    Commons.activeEvent.setStartDate(String.valueOf(sday)+"/"
                            +String.valueOf(smonth)+"/"+String.valueOf(syear));
                    Commons.activeEvent.setEndDate(String.valueOf(eday)+"/"
                            +String.valueOf(emonth)+"/"+String.valueOf(eyear));
                    Commons.activeEvent.setDescription(eventDescription.getText().toString());
                    Commons.activeEvent.setLocation(locationstr);
                    Commons.activeEvent.setImageUrl(imageURL);
                    Commons.activeEvent.setCreatorId(Commons.currentActiveUser.getId());
                    Commons.activeEvent.setCreatorName(Commons.currentActiveUser.getName());


                    Intent i = new Intent(CreateEventOneActivity.this , CreateEventTwoActivity.class);
                    startActivity(i);
                }
            }
        });
    }

}
