package com.donga.caloriemaster_android;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    private EditText mEmailView;
    private EditText mPasswordView;
    private FirebaseAuth mAuth;
    private Button signinbutton;
    private Button signupbutton;
    private Button forgot;
    public LoginFragment() {}
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        forgot = (Button) v.findViewById(R.id.forgot_id);
        signinbutton = (Button) v.findViewById(R.id.sign_in);
        mEmailView = (EditText) v.findViewById(R.id.email_entry);
        mPasswordView = (EditText) v.findViewById(R.id.password_entry);
        signupbutton = (Button) v.findViewById(R.id.sign_up);
        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userID = mEmailView.getText().toString();
                final String userPassword = mPasswordView.getText().toString();
                if(userID.equals("")||userPassword.equals("")){
                    Toast.makeText(getContext(),"put valid info!",Toast.LENGTH_LONG).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(userID, userPassword)
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(requireActivity(), "Authentication failed."+task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }

                            }
                        });
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"관리팀에 문의하세요.",Toast.LENGTH_SHORT).show();
            }
        });
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).loadFragment(SignupFragment.newInstance());
            }
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void updateUI(FirebaseUser User){
        if(User != null){
            //   Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
            ((MainActivity)getActivity()).loadFragment(ProfileFragment.newInstance());
        }
        else{
        }
    }//try to fix
}