package java.com.example.protocols_androidclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SelectPlayer extends Activity implements Runnable {
    protected String otherClientNames;
    protected String [] clients;
    protected String client1;
    protected ListView list;
    protected ArrayAdapter<String> listAdapter;
    protected Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_clients);
        handler = new Handler();
        otherClientNames = getIntent().getExtras().getString("otherClientNames");
        client1 = getIntent().getExtras().getString("client1");
        if (! otherClientNames.equals("empty")) {
            clients = otherClientNames.split("-");
            list = (ListView) findViewById(R.id.listview1);
            listAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, clients);
            handler.post(new UpdateListOfClients());
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View arg1, int position,
                                        long arg3) {
                    // TODO Auto-generated method stub
                    String selectedPlayer = (String) parent.getItemAtPosition(position);
                    GlobalAccess.outToServer.println("playRequest," + client1 + "," + selectedPlayer + ",initial");
                }
            });
        }
    }

    private class UpdateListOfClients implements Runnable {
        public void run() {
            list.setAdapter(listAdapter);
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

        while (true) {
            String incomingMessage;
            try {
                incomingMessage = GlobalAccess.inFromServer.readLine();
                Log.d("String", incomingMessage);
                if (incomingMessage != null) {
                    if (incomingMessage.split(",")[0].equals("playRequest")) {
                        switch (incomingMessage.split(",")[3]) {
                            case "request": {
                                Intent i = new Intent(SelectPlayer.this, playerResponse.class);
                                Bundle b = new Bundle();
                                b.putString("client", client1);
                                b.putString("otherClientNames", otherClientNames);
                                i.putExtras(b);
                                finish();
                                startActivity(i);
                                break;
                            }
                            case "decline": {
                                handler.post(new ViewToastPlayerDeclined());
                                break;
                            }
                            case "accept": {
                                handler.post(new ViewToastPlayerAccepted());
                                Intent i = new Intent(SelectPlayer.this, GameBoard.class);
                                finish();
                                startActivity(i);
                                break;
                            }
                        }
                    }
                }
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    private class ViewToastPlayerDeclined implements Runnable {
        public void run() {
            Toast.makeText(SelectPlayer.this, "Play invitation declined. Please select another opponent!"
                    , Toast.LENGTH_LONG).show();
        }
    }

    private class ViewToastPlayerAccepted implements Runnable {
        public void run() {
            Toast.makeText(SelectPlayer.this, "Play invitation accepted. Let's play!"
                    , Toast.LENGTH_LONG).show();
        }
    }
}
