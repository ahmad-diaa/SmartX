package java.com.example.protocols_androidclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListOfClientsActivity extends Activity {
	protected String otherClientNames;
	protected String [] clients;
	protected ListView list;
	protected ArrayAdapter<String> listAdapter;
	protected Handler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_of_clients);
		handler = new Handler();
		otherClientNames = getIntent().getExtras().getString("otherClientNames");
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
					String chatPartnerName = (String) parent.getItemAtPosition(position);
					Intent i = new Intent(ListOfClientsActivity.this, ChatActivity.class);
					Bundle b = new Bundle();
					b.putString("chatPartnerName", chatPartnerName);
					i.putExtras(b);
					finish();
					startActivity(i);
				}
			});
		}
	}
	
	private class UpdateListOfClients implements Runnable {
		public void run() {
			list.setAdapter(listAdapter);
		}
	}
}
