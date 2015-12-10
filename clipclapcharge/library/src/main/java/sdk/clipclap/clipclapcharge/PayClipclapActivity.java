package sdk.clipclap.clipclapcharge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import sdk.clipclap.payandgo.R;

/**
 * Created by Luis Guzman on 12/3/15.
 */
public class PayClipclapActivity extends Activity {

    public static final String URL="URL";
    public static final String RIMG="IMG";
    public static final String BUTTONCOLOR="BUTTONCOLOR";
    public static final String BACKGROUNDCOLOR="BACKGROUNDCOLOR";
    public static final String TEXTMESSAGECOLOR="TEXTMESSAGECOLOR";
    public static final String TEXTMESSAGE="TEXTMESSAGE";
    public static final String TEXTBUTTONCOLOR="TEXTBUTTONCOLOR";
    public static final String TEXTBUTTON="TEXTBUTTON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.pay_clipclap);

        if(getIntent().hasExtra(URL)) {
            final String url = getIntent().getStringExtra(URL);

            LinearLayout button = (LinearLayout)findViewById(R.id.clipclapPay);
            TextView txButton = (TextView)findViewById(R.id.txtPay);
            if(getIntent().hasExtra(RIMG)) {
                ImageView img = (ImageView)findViewById(R.id.clipclapImg);
                img.setImageResource(getIntent().getIntExtra(RIMG,R.mipmap.ic_launcher));

            }

            if(getIntent().hasExtra(BUTTONCOLOR))
                button.setBackgroundColor(Color.parseColor(getIntent().getStringExtra(BUTTONCOLOR)));
            if(getIntent().hasExtra(TEXTBUTTON))
                txButton.setText(getIntent().getStringExtra(TEXTBUTTON));
            if(getIntent().hasExtra(TEXTBUTTONCOLOR))
                txButton.setTextColor(Color.parseColor(getIntent().getStringExtra(TEXTBUTTONCOLOR)));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });

            if(getIntent().hasExtra(BACKGROUNDCOLOR))
                findViewById(R.id.background).setBackgroundColor(Color.parseColor(getIntent().getStringExtra(BACKGROUNDCOLOR)));

            if(getIntent().hasExtra(TEXTMESSAGE)) {
                TextView textMessage = (TextView) findViewById(R.id.textClipClap);
                textMessage.setText(getIntent().getStringExtra(TEXTMESSAGE));
                if(getIntent().hasExtra(TEXTMESSAGECOLOR))
                textMessage.setTextColor(Color.parseColor(getIntent().getStringExtra(TEXTMESSAGECOLOR)));
            }

        }else {
            finish();
        }
    }
}
