package com.example.prapti.uni_res;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.layout.simple_list_item_1;

public class Registration_Page extends AppCompatActivity{

    private Vibrator vib;
    Animation animShake;
    Boolean reg;
    String inst , depart ;
    Integer div , sem;
    Button submit;
    EditText name,id;
    DatabaseReference reff;
    Student student;
    FirebaseAuth mAuth;
    ImageView logout_btn;
    GoogleSignInClient mGoogleSignInClient;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__page);

        final String institute[] = {"Choose_institute", "CSPIT" , "DEPSTAR"};
        final String department[] = {"Choose_Department" , "CE" , "IT"};
        final Integer division[] = {0, 1 , 2 };
        final Integer semester[] = {0, 1 , 2 , 3 , 4 ,5 , 6, 7, 8};


        submit = findViewById(R.id.submit);
        name = findViewById(R.id.signup_input_name);
        id = findViewById(R.id.signup_input_id);
        logout_btn = findViewById(R.id.logout_btn);

        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = mAuth.getCurrentUser();

        animShake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        final Spinner spinner = findViewById(R.id.spinner);
        final Spinner spinner1 = findViewById(R.id.spinner1);
        final Spinner spinner2 = findViewById(R.id.spinner2);
        final Spinner spinner3 = findViewById(R.id.spinner3);

        @SuppressLint("ResourceType")
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, simple_list_item_1, institute);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, simple_list_item_1, department);
        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(this, simple_list_item_1, semester);
        ArrayAdapter<Integer> adapter3 = new ArrayAdapter<Integer>(this, simple_list_item_1, division);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(adapter3);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                inst= institute[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                depart= department[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sem = semester[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                div = division[position];
               // Toast.makeText(Registration_Page.this , "Division"+ div  , Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        student = new Student();
        reff = FirebaseDatabase.getInstance().getReference();


        logout_btn.setOnClickListener(new  View.OnClickListener(){

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.logout_btn:
                        signOut();
                        break;
                    // ...
                }
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);






        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().toString().isEmpty()){
                    name.setError("Please enter email id");
                    name.requestFocus();
                }
                else if(id.getText().toString().isEmpty()){
                    id.setError("Please enter email id");
                    id.requestFocus();
                }


                else if(inst.equals("Choose_institute")){
                    ((TextView)spinner.getSelectedView()).setError("Please choose institute");
                    spinner.requestFocus();

                }
                else if(depart.equals("Choose_Department")){
                    ((TextView)spinner1.getSelectedView()).setError("Please choose department");
                    spinner1.requestFocus();

                }
                else if(sem.equals(0)){
                    ((TextView)spinner2.getSelectedView()).setError("Please choose correct semester");
                    spinner2.requestFocus();

                }
                else if(div.equals(0)){
                    ((TextView)spinner3.getSelectedView()).setError("Please choose correct division");
                    spinner3.requestFocus();

                }

                else {

                    student.setName(name.getText().toString().trim());
                    student.setDepartment(depart);
                    student.setEmail(user.getEmail());
                    student.setId(id.getText().toString().trim());
                    student.setInstitute(inst);
                    student.setRegistered(true);
                    student.setDiv(div);
                    student.setSemester(sem);
                    reff.child("Student").child("UserId").child(user.getUid()).setValue(student);

                    Toast.makeText(Registration_Page.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Registration_Page.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

    private void signOut() {

        mAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(Registration_Page.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //if the user is not logged in
        //opening the login activity
       /* if (mAuth.getCurrentUser() == null) {

            finish();
            startActivity(new Intent(this, MainActivity.class));
        }*/

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String userid=user.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Student");
        reference.child("UserId").child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 reg = dataSnapshot.getValue(Student.class).getRegistered();
               Toast.makeText(Registration_Page.this, reg.toString(), Toast.LENGTH_SHORT).show();

                if(reg.toString().equals("true")){
                    Intent intent = new Intent(Registration_Page.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }





}
