package devnitish.com.simplytext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import devnitish.com.simplytext2.SimplyText;

public class MainActivity extends AppCompatActivity {

    SimplyText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);

        text.changeText("fuck off. This is working");
        text.setBackground(0,2,R.color.colorPrimary);
        text.setForeground(2,4,R.color.colorAccent);
        text.setStrike(4,6);
        text.setUnderLine(6,8);
        text.setSubScript(8,10);
        text.setSubScript(0,2);
        text.setSuperScript(10,12);
        text.setAbsoluteSize(12,14,25);
        text.setRelativeSize(14,17,2);
        text.setStyle(17,19,SimplyText.Style.BOLD);
        text.setStyle(19,21,SimplyText.Style.ITALIC);

        text.setSmartClickable(0, 5, new SimplyText.SmartClickListener() {
            @Override
            public void onSmartClick() {

                Toast.makeText(MainActivity.this,"first",Toast.LENGTH_SHORT).show();
            }
        });

       int formatingId =  text.setSmartClickable(10, 20, new SimplyText.SmartClickListener() {
            @Override
            public void onSmartClick() {

                Toast.makeText(MainActivity.this,"second",Toast.LENGTH_SHORT).show();
            }
        });

       text.removeFormating(formatingId);

       text.removeAllFormating();
    }
}
