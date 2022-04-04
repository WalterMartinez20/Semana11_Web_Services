package com.example.uso_de_web_services;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.AsynchronousChannel;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    ProgressDialog progreso;
    JSONArray datosJSON;
    JSONObject jsonObject;
    Bundle parametros = new Bundle();
    int posicion=0;

    InputStreamReader isReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        obtenerDatos myAsync = new obtenerDatos;
        myAsync.execute();

        FloatingActionButton btn = (FloatingActionButton) findView(R.id.btnAgregar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parametros.putString("accion", "nuevo");
                nueva_agenda();

            }
        });
    }
      public void nueva_agenda(){
        Intent agregar_agenda = new Intent(PackageContextMainActivity.this, agregar_agenda.class);
        agregar_agenda.putExtras(parametros);
        startActivity(agregar_agenda);
      }

      public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v,menuInfo);
        getMenuInflater inflate= getMenuInflater();
        inflate.inflate(R.menu.mimenu, menu);

          AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)menuInfo;
          try {
              datosJSON.getJSONobject(info.position);
              menu.setHeaderTitle(datosJSON.getJSONobject(info.position).getJSONobject("value")get.String(name:"nombre").toString());
              posicion= info.position;
          }
          catch (Exception ex){
              //error...
          }
      }

      @Override
    public boolean onContextItemSelect(MenuItem item){
        switch (item.getItemId()) {
            case   R.id.mnxAgregar:
                parametros.putString("accion","nuevo");
                nuevo_agenda();
                return true;
              case  R.id.mnxModificar:
                  parametros.putString("accion","modificar");
                  try {
                      parametros.putString("valores", datosJSON.getJSONobject(posicion).getJSONobject("value").toString());
                      nueva_agenda();
                  }catch (Exception ex){
                      //error
                  }
                  return  true;
            case R.id.mnxEliminar:
                JSONObject miData = new JSONObject();
                try {
                    miData.put(name:"_id", datosJSON.getJSONobject(posicion).getJSONobject("value").getString(name:"_id"));
                } catch
                (Exception ex) {
                //error
            }
                eliminarDatos objEliminar =new eliminarDatos();
                objEliminar.execute (miData.toString());
                return  true;
        }return super.onContextItemSelected(item);
    }

    private class ObtenerDatos extends AsyncTask<Void,Void,String> {
        HttpsURLConnection urlConnection;
        @Override
        protected  String doInBackground(Void...params) {
            StringBuilder result =new StringBuilder();

            try {
                //conexion al servidor//
                URL url new  URL(spec:"http://10.0.2.2:5984/db_agenda/design/agenda/_view/mi-agenda");
                urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                inputStream in =new  bufferedInputStream(urlConnection.getInputStream());
                bufferedReader reader = new  bufferedReader (new inputStreamReader(in));
                string line;
                while ((line = reader.readLine()) !=null{
                    result.append(line);
                }
            }catch (exeption ex){
                Log.e(tag:"mi error", msg:"Error ",ex);
                ex.printStackTrace();

            }finally {
                urlConnection.disconnect();
            }return  result.toString();

        }

    }

}