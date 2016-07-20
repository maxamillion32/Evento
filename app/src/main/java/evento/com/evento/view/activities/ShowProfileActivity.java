package evento.com.evento.view.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;

import evento.com.evento.R;
import evento.com.evento.view.adpters.InterstedAdapter;
import evento.com.evento.controllers.UserController;
import evento.com.evento.utils.Commons;
import evento.com.evento.utils.Constants;

public class
ShowProfileActivity extends AppCompatActivity {

    ImageView mIMGprofile;
    TextView mTVname;
    TextView mTVbirthday;
    TextView mTVlocation;
    TextView mTVgender;

    Button mBTfollow ;

    InterstedAdapter listAdapter;
    ExpandableListView mexpListView;
    ArrayList<String> listDataHeader;
    HashMap<String, ArrayList<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);
        mIMGprofile = (ImageView) findViewById(R.id.IMGshowProfile);
        mTVname= (TextView) findViewById(R.id.TVnameShowProfile);
        mTVbirthday= (TextView) findViewById(R.id.TVshowProfileBirthdata);
        mTVlocation= (TextView) findViewById(R.id.TVshowProfileLocation);
        mTVgender= (TextView) findViewById(R.id.TVshowProfileGender);
        mexpListView = (ExpandableListView) findViewById(R.id.LVshowIntersted);
        mBTfollow = (Button) findViewById(R.id.BTfollow);

        mTVname.setText(Commons.userToShowProfile.getName());
        mTVlocation.setText(Commons.userToShowProfile.getLocation());
        mTVgender.setText(Commons.userToShowProfile.getGender());
        mTVbirthday.setText(Commons.userToShowProfile.getBirthdate());

        Commons.firebaseStorageReference.child(Constants.IMAGES+"/"+Commons.userToShowProfile.getId())
                .getBytes(Long.MAX_VALUE).
                addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // Use the bytes to display the image
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        mIMGprofile.setImageBitmap(bmp);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


        // preparing list data
        prepareListData();

        listAdapter = new InterstedAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        mexpListView.setAdapter(listAdapter);

        if (Commons.isCurrentUserFollowThisUser())
            mBTfollow.setText("Unfollow");
        else
            mBTfollow.setText("Follow");

        mBTfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBTfollow.getText()== "follow") {
                    UserController.follow(Commons.userToShowProfile);
                    mBTfollow.setText("unfollow");
                }else{
                    UserController.unfollow(Commons.userToShowProfile.getId());
                    mBTfollow.setText("follow");
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Commons.changeActivity(ShowProfileActivity.this, MainActivity.class);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, ArrayList<String>>();

        // Adding child data
        listDataHeader.add("Interstes");

        // Adding child data
        ArrayList<String> top250 = new ArrayList<String>();
        /*top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");*/

        for (int i=0; i<Commons.userToShowProfile.getInterests().size(); i++)
            top250.add(Commons.userToShowProfile.getInterests().get(i).getName());

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
    }
}
