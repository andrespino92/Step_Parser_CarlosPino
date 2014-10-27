package com.example.step_parce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class MainActivity extends ActionBarActivity 
{
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
			
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}
		
		private ArrayList<String>nombre = new ArrayList<String>();
		private ArrayList<String>color = new ArrayList<String>();
		private ArrayList<String>imagen = new ArrayList<String>();
		private ArrayList<String>comentario = new ArrayList<String>();
		act act;
		static ListView lista_act;
		View rootView;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
		{
			rootView = inflater.inflate(R.layout.fragment_main, container,false);
			
			CargarDatos cd = new CargarDatos();
			cd.execute("http://stepparsermovil.hol.es/json_movil.php");
			
			nombre.clear();
			color.clear();
			imagen.clear();
			comentario.clear();
			
			lista_act = (ListView)rootView.findViewById(R.id.listView1);
			lista_act.setOnItemClickListener(new OnItemClickListener()
	    	{			 
			    @Override
			    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) 
			    {
			    	act = new act();
			        Bundle args = new Bundle();
					args.putString("n", nombre.get(position));
					args.putString("c", color.get(position));
					args.putString("i", imagen.get(position));
					args.putString("com", comentario.get(position));
					act.setArguments(args);	    
					
			    	FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
					ft.replace(R.id.container, act).addToBackStack("paciente").commit();
			    }
			 
			});
			
			return rootView;
		}
		
		public class CargarDatos extends AsyncTask<String, Void, String>
		{
	        /*Atributos para petición http*/
	        private HttpClient httpclient;
	        private HttpPost httppost;
	        private HttpResponse response;
	        private JSONObject obj=null;
			@Override
			protected String doInBackground(String... parameters) 
			{
				StringBuilder answer = new StringBuilder();
				try
				{
					InputStream is;
					String line ="";
					BufferedReader rd;
						httpclient = new DefaultHttpClient();
		                httppost = new HttpPost(parameters[0]);
		                response = httpclient.execute(httppost);
		                is = response.getEntity().getContent();		                
		                rd = new BufferedReader(new InputStreamReader(is));
		                while((line = rd.readLine())!=null){
		                    answer.append(line);
		                }
	            }catch (ClientProtocolException ex){
	                ex.printStackTrace();
	            }catch (IOException ex){
	                ex.printStackTrace();
	            }
				//Log.d("Respuesta",answer.toString());
				return answer.toString();
				
			}
			@Override
			protected void onPostExecute(String data)
			{					
				 try
				 {
					String status="";
							
					obj = new JSONObject(data);
			        status = obj.getString("exito");			                
			        JSONArray jsonArray = obj.getJSONArray("datos");
				    if(status.equals("1"))
				    {				            	 
				    	int sizemateria = jsonArray.length()-1;
				    	JSONObject record;
				        for (int i=0;i<sizemateria;i++) 
				        {					 
				        	 record = jsonArray.getJSONObject(i);
		                	 
		                	 String nombre = record.getString("nombre");
		                	 PlaceholderFragment.this.nombre.add(nombre);
		                	 
		                	 String color = record.getString("color");
		                	 PlaceholderFragment.this.color.add(color);
		                	 
		                	 String imagen = record.getString("imagen");
		                	 PlaceholderFragment.this.imagen.add(imagen);
		                	 
		                	 String comentario = record.getString("comentario");
		                	 PlaceholderFragment.this.comentario.add(comentario);
				        }
				        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_activated_1, nombre);
						lista_act.setAdapter(adaptador);
				    }
				    else
				    {			            	 
				    	Toast.makeText(rootView.getContext(),"No hay Actividad", Toast.LENGTH_LONG).show();
				    }
					 
				 }
				 catch (JSONException ex)
				 {
					 ex.printStackTrace();
				 }
			}
		}
	}


}
