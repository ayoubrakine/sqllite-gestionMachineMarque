package ayoub.rakine.gestionmachinemarque.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ayoub.rakine.gestionmachinemarque.R;
import ayoub.rakine.gestionmachinemarque.beans.User;
import ayoub.rakine.gestionmachinemarque.services.UserService;

public class Register extends AppCompatActivity {

    EditText firstname,lastname,email,password;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstname=findViewById(R.id.nom);
        lastname=findViewById(R.id.prenom);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        add=findViewById(R.id.register);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserService sv = new UserService(Register.this);
                User user=new User(firstname.getText().toString(),lastname.getText().toString(),email.getText().toString(),password.getText().toString());
                sv.create(user);
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

    }
    public void onRegisterClick(View View){
        startActivity(new Intent(this, Login.class));
    }
}