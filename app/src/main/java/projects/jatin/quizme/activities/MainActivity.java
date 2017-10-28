package projects.jatin.quizme.activities;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;
import projects.jatin.quizme.R;
import projects.jatin.quizme.model.User;

public class MainActivity extends AppCompatActivity {

    MaterialEditText edtNewUser, edtNewPassword, edtNewConfirmPassword, edtNewEmail; //forSignUp
    MaterialEditText edtUser,edtPassword;

    Button btnSignup,btnSignIn;
    
    //signup Dialog Button
   Button btnDialogSignup;

    FirebaseDatabase database;
    DatabaseReference users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Firebase
        database=FirebaseDatabase.getInstance();
        users=database.getReference("Users");

        edtUser= (MaterialEditText) findViewById(R.id.edtUserName);
        edtPassword= (MaterialEditText) findViewById(R.id.edtPassword);

        btnSignIn= (Button) findViewById(R.id.btnSignIn);
        btnSignup= (Button) findViewById(R.id.btnSignUp);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignUpDialog();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(edtUser.getText().toString(),edtPassword.getText().toString());
            }
        });

    }

    private void signIn(final String username, final String pwd) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(username).exists()) {
//                    Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                    if(!username.isEmpty())
                    {
                        User login=dataSnapshot.child(username).getValue(User.class);
                        if(login.getPassword().equals(pwd))
                            Toasty.success(MainActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
                        else
                            Toasty.error(MainActivity.this, "Wrong password.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toasty.info(MainActivity.this, "Please enter your username.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toasty.error(MainActivity.this, "User does not exist.", Toast.LENGTH_SHORT).show();
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void showSignUpDialog() {

        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Sign Up");
        alertDialog.setMessage("Please fill the information.");

        LayoutInflater inflater=this.getLayoutInflater();
        View signupLayout=inflater.inflate(R.layout.sign_up,null);

        edtNewUser = signupLayout.findViewById(R.id.edtNewUserName);
        edtNewPassword = signupLayout.findViewById(R.id.edtNewPassword);
        edtNewConfirmPassword=signupLayout.findViewById(R.id.edtNewConfirmPassword);
        edtNewEmail = signupLayout.findViewById(R.id.edtNewEmail);
        btnDialogSignup=signupLayout.findViewById(R.id.btnSignUp);


        alertDialog.setView(signupLayout);
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);
        alertDialog.setCancelable(true);

        final AlertDialog dialog=alertDialog.show();
        
        btnDialogSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValid())
                {

                    final User user= new User(edtNewUser.getText().toString(),
                            edtNewPassword.getText().toString(),
                            edtNewEmail.getText().toString());

                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(user.getUsername()).exists())
                                Toasty.info(MainActivity.this, "Username already exists!", Toast.LENGTH_SHORT).show();
                            else
                            {
                                users.child(user.getUsername()).setValue(user);
                                Toasty.success(MainActivity.this, "User registration successful.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    dialog.dismiss();

                    
                }
            }

            private boolean isValid() {


                final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
                        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                                "\\@" +
                                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                                "(" +
                                "\\." +
                                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                                ")+");

                if(!EMAIL_ADDRESS_PATTERN.matcher(edtNewEmail.getText().toString()).matches()){

                    edtNewEmail.setError("Please enter a valid Email address.");
                    return false;
                }
                else if(edtNewUser.getText().toString().isEmpty()) {
                    edtNewUser.setError("Empty Field!");
                    return false;
                }
                else if(edtNewEmail.getText().toString().isEmpty()) {
                    edtNewEmail.setError("Empty Field!");
                    return false;
                }
             else   if(edtNewPassword.getText().toString().isEmpty()) {
                    edtNewPassword.setError("Empty Field!");
                    return false;
                }
               else if(edtNewConfirmPassword.getText().toString().isEmpty()) {
                    edtNewConfirmPassword.setError("Empty Field!");
                    return false;
                }
                else if(!edtNewPassword.getText().toString().equals(edtNewConfirmPassword.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Password does not match.", Toast.LENGTH_SHORT).show();
                    edtNewPassword.setText("");
                    edtNewConfirmPassword.setText("");
                    edtNewPassword.setError("Password does not match");
                    return false;
                }

                return true;
            }
        });




    }
}
