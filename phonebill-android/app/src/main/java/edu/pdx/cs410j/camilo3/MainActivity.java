package edu.pdx.cs410j.camilo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // from the tutorial if I want to sent a message.
//    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    public void sayHello(View view) {
//        PhoneCall call = new PhoneCall();
//        Toast.makeText(this, "The call is:" + call, Toast.LENGTH_SHORT).show();
//    }

    /** this will go to our main menu... */
    public void goToMainMenu(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }
    /** this is called when the readme button is pressed. */
    public void goToReadMe(View view) {
        Intent intent = new Intent(this, DisplayReadMe.class);
        // from the tutorial if I want to send a message.
//        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}