package ayoub.rakine.gestionmachinemarque.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ayoub.rakine.gestionmachinemarque.MainActivity;
import ayoub.rakine.gestionmachinemarque.R;
import ayoub.rakine.gestionmachinemarque.beans.User;
import ayoub.rakine.gestionmachinemarque.services.UserService;
import ayoub.rakine.gestionmachinemarque.ui.home.HomeFragment;
import ayoub.rakine.gestionmachinemarque.ui.marque.AddMarqueDialogFragment;

public class Login extends AppCompatActivity {
    EditText email,password;
    Button login;
    TextView register;

    List<User> users;

    boolean b=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        register=findViewById(R.id.signupText);

        register.setClickable(true);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserService sv = new UserService(Login.this);
                users=sv.findAll();
                for(User user:users){
                    if(user.getEmail().equals(email.getText().toString()) && user.getPassword().equals(password.getText().toString())){

                        b=true;
                        break;


                    }else {

                        b=false;
                    }


                }

                if(b){
                    Toast.makeText(getApplicationContext(), "Login succesfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    //intent.putExtra("loadFragment", "home"); // Utilisez une clé pour identifier le fragment à charger
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Password or Email incorrect", Toast.LENGTH_SHORT).show();

                }



            }
        });

    }


}