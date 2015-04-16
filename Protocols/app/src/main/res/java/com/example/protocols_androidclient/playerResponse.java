package java.com.example.protocols_androidclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class playerResponse extends Activity implements OnClickListener {

    protected Button btnAccept, btnReject;
    protected String client1, client2;
    protected TextView clientName;
    protected String otherClientNames;
    protected Thread thread;
    protected Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_response);

        otherClientNames = getIntent().getExtras().getString("otherClientNames");
        client1 = getIntent().getExtras().getString("client1");
        client2 = getIntent().getExtras().getString("client2");

        clientName = (TextView) findViewById(R.id.textView2);
        clientName.setText(client1);

        btnAccept = (Button) findViewById(R.id.button1);
        btnAccept.setOnClickListener(this);

        btnReject = (Button) findViewById(R.id.button2);
        btnReject.setOnClickListener(this);
    }

    @Override
    public void onClick(View btn) {
        // TODO Auto-generated method stub
        if (btn == btnAccept) {
            Intent i = new Intent(playerResponse.this, GameBoard.class);
            finish();
            startActivity(i);

        } else if (btn == btnReject){
            GlobalAccess.outToServer.println("playRequest," + client1 + "," + client2 + ",reject");
            Intent i = new Intent(playerResponse.this, SelectPlayer.class);
            Bundle b = new Bundle();
            b.putString("otherClientNames", otherClientNames);
            i.putExtras(b);
            finish();
            startActivity(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player_response, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
