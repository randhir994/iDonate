package com.example.yusuf.idonate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.HashMap;
import java.util.Map;

public class AddMember extends AppCompatActivity {
    EditText name,phone,email,age,city,pin;
    Spinner btype;
    Button add;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    String[] bloods={"AB+","A+","A-","O+","O-","AB-","B+","B-"};
    Boolean mal;
    private ProgressDialog pDialog;

    public void toMain1(View v){
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_member);
        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        radioSexButton = (RadioButton) findViewById(radioSexGroup.getCheckedRadioButtonId());
        add=(Button)findViewById(R.id.add) ;
        name=(EditText)findViewById(R.id.aname);
        phone=(EditText)findViewById(R.id.aphone);
        email=(EditText)findViewById(R.id.aemail);
        age=(EditText)findViewById(R.id.aage);
        city=(EditText)findViewById(R.id.acity);
        pin=(EditText)findViewById(R.id.apin);
        btype=(Spinner)findViewById(R.id.ablood);
        pDialog=new ProgressDialog(AddMember.this);
        ArrayAdapter arrayAdapter=new ArrayAdapter(AddMember.this, android.R.layout.simple_dropdown_item_1line, bloods);
        btype.setAdapter(arrayAdapter);

//        Toast.makeText(AddMember.this,
//                radioSexButton.getText(), Toast.LENGTH_SHORT).show();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pDialog.setMessage("Submit..");
                pDialog.show();
                HashMap contact = new HashMap();
                contact.put( "name", name.getText().toString() );
                contact.put( "age", age.getText().toString() );
                contact.put( "phone", phone.getText().toString() );
                contact.put( "email", email.getText().toString() );
                contact.put( "phone", phone.getText().toString() );
                if(radioSexButton.getText().toString().equals("Male")){
                    mal=true;
                }else {
                    mal=false;
                }
                contact.put( "male",mal  );
                contact.put( "city", city.getText().toString() );
                contact.put( "pin", pin.getText().toString() );
                contact.put( "blood", btype.getSelectedItem().toString() );
                contact.put( "userType", "donor" );
                // save object asynchronously

                Backendless.Persistence.of( "user" ).save( contact, new AsyncCallback<Map>() {
                    public void handleResponse( Map response )
                    {
                        // new Contact instance has been saved
                        //Toast.makeText(getApplicationContext(),"Successful", Toast.LENGTH_SHORT).show();
                            pDialog.dismiss();
                        startActivity(new Intent(AddMember.this,MainMenu.class));
                    }

                    public void handleFault( BackendlessFault fault )
                    {
                        // an error has occurred, the error code can be retrieved with fault.getCode()
                        Toast.makeText(getApplicationContext(),fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
