package emsi.saas.pfa.pfa_saas.services;

import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import emsi.saas.pfa.pfa_saas.AllAssembles;
import emsi.saas.pfa.pfa_saas.R;
import emsi.saas.pfa.pfa_saas.adapters.AssembleAdapter;
import emsi.saas.pfa.pfa_saas.model.Assemble;

public class AssembleServices {
    private List<Assemble> assembles = new ArrayList<>();
    private RecyclerView recyclerView;
    private AssembleAdapter mAdapter;

    public void Assembles() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            LinearLayout linlaHeaderProgress = (LinearLayout) AllAssembles.getContext().findViewById(R.id.linlaHeaderProgress);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                linlaHeaderProgress.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
             //   Toast.makeText(AllAssembles.getContext(), "Loadin", Toast.LENGTH_SHORT).show();
                linlaHeaderProgress.setVisibility(View.GONE);
                try {
                    loadIntoListView(s);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //in this method we are fetching the json string
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL("http://cf553cf4.ngrok.io/pfa_saas/index.php");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }

        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();

    }


    private void loadIntoListView(String json) throws JSONException {
        JSONObject str = new JSONObject(json);
        JSONArray data = str.getJSONArray("data"); // this is the "items: [ ] part
        for (int i = 0; i < data.length(); i++) {
            JSONObject obj = data.getJSONObject(i);
            assembles.add(new Assemble(Integer.parseInt(obj.getString("id")), obj.getString("nomPropriete"), obj.getString("dateAssemble"), Integer.parseInt(obj.getString("dureeAssemble"))));
        }
        recyclerView = (RecyclerView) AllAssembles.getContext().findViewById(R.id.recyclerView);

        //  recyclerView.setLayoutManager(new LinearLayoutManager(AllAssembles.getContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(AllAssembles.getContext(), 1));
        mAdapter = new AssembleAdapter(assembles);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AssembleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //  Toast.makeText(AllAssembles.getContext(), Integer.toString(position), Toast.LENGTH_SHORT).show();
                showPopup("Assemble de la propriete: " + assembles.get(position).getNom_propriete() + "\nLe :" + assembles.get(position).getDate_assemble() + "\nDuree: " + assembles.get(position).getDuree_assemble()+" min");
            }
        });
    }

    private PopupWindow pw;

    private void showPopup(String str) {
        Button accept;
        Button Close;
        Button reject;
        TextView tx;
        try {

            LayoutInflater inflater = (LayoutInflater) AllAssembles.getContext().getSystemService(AllAssembles.getContext().LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup, (ViewGroup) AllAssembles.getContext().findViewById(R.id.popup_1));
            pw = new PopupWindow(layout, 500, 370, true);
            pw.setWindowLayoutMode(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
            tx = (TextView) layout.findViewById(R.id.txtView);
            tx.setText(str);
            Close = (Button) layout.findViewById(R.id.close_popup);
            accept = (Button) layout.findViewById(R.id.accept_popup);
            reject = (Button) layout.findViewById(R.id.reject_popup);
            Close.setOnClickListener(cancel_button);
            accept.setOnClickListener(accept_button);
            reject.setOnClickListener(reject_button);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cancel_button = new View.OnClickListener() {
        public void onClick(View v) {
            pw.dismiss();
        }
    };
    private View.OnClickListener reject_button = new View.OnClickListener() {
        public void onClick(View v) {
            InsertData(5, 2, false);

        }
    };
    private View.OnClickListener accept_button = new View.OnClickListener() {
        public void onClick(View v) {
            InsertData(5, 3, true);

            // Toast.makeText(AllAssembles.getContext(), "add", Toast.LENGTH_SHORT).show();
        }
    };

    public void InsertData(final int iduser, final int idassemble, final boolean vote) {

        class addData extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(AllAssembles.getContext(), s, Toast.LENGTH_SHORT).show();
                pw.dismiss();

            }

            //in this method we are fetching the json string
            @Override
            protected String doInBackground(Void... voids) {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("iduser", Integer.toString(iduser)));
                nameValuePairs.add(new BasicNameValuePair("idassemble", Integer.toString(idassemble)));
                nameValuePairs.add(new BasicNameValuePair("vote", Boolean.toString(vote)));


                StringBuilder str = new StringBuilder();
                HttpClient client = new DefaultHttpClient();
                final HttpPost httpPost = new HttpPost("http://cf553cf4.ngrok.io/pfa_saas/addVote.php");
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = client.execute(httpPost);
                    StatusLine statusLine = response.getStatusLine();
                    int statusCode = statusLine.getStatusCode();
                    if (statusCode == 200) { // Status OK
                        HttpEntity entity = response.getEntity();
                        InputStream content = entity.getContent();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            str.append(line);
                        }
                    } else {
                        Log.e("Log", "Failed to download result..");
                    }
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return str.toString();
            }

        }
        addData adddata = new addData();
        adddata.execute();

    }
}
