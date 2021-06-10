package com.example.temanadaptersqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mysql.App.AppController;
import com.example.mysql.Database.EditTeman;
import com.example.mysql.Database.Teman;
import com.example.mysql.MainActivity;
import com.example.mysql.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class teman_adapter extends RecyclerView.Adapter<teman_adapter.TemanViewHolder> {
    private ArrayList<Teman> listData;

    public teman_adapter(ArrayList<Teman>listData){
        this.listData = listData;
    }
    @Override
    public TemanViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutinf = LayoutInflater.from(parent.getContext());
        View view = layoutinf.inflate(R.layout.row_data_teman,parent,false);
        return new TemanViewHolder(view);
    }

    @Override
    public void onBindViewHolder( TemanViewHolder holder, int position) {
        String id,nm, tlp;

        id = listData.get(position).getId();
        nm = listData.get(position).getNama();
        tlp = listData.get(position).getTelpon();

        holder.namatext.setTextColor(Color.BLUE);
        holder.namatext.setTextSize(20);
        holder.telpontext.setTextSize(15);
        holder.namatext.setText(nm);
        holder.telpontext.setText(tlp);
        holder.cardku.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu popupMenu = new PopupMenu(view.getContext(),view);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.edit:
                                Bundle bandel = new Bundle();
                                bandel.putString("kunci1",id);
                                bandel.putString("kunci2",nm);
                                bandel.putString("kunci3",tlp);

                                Intent intent = new Intent(view.getContext(), EditTeman.class);
                                intent.putExtras(bandel);
                                view.getContext().startActivity(intent);
                                break;

                            case R.id.hapus:
                                AlertDialog.Builder alertdb = new AlertDialog.Builder(view.getContext());
                                alertdb.setTitle("Yakin nih "+nm+" mau di hapus?");
                                alertdb.setMessage("Tekan YA untuk menghapus");
                                alertdb.setCancelable(false);
                                alertdb.setPositiveButton("ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        HapusData(id);
                                        Toast.makeText(view.getContext(),"Data "+id+" telah dihapus", Toast.LENGTH_LONG).show();
                                        Intent inten = new Intent(view.getContext(), MainActivity.class);
                                        view.getContext().startActivity(inten);
                                    }
                                });
                                alertdb.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog adlg = alertdb.create();
                                adlg.show();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            }
        });
    }

    private void HapusData(final String idx){
        String url_delete = "http://10.0.2.2:8080/umyTI/deletetm.php";
        final String TAG = MainActivity.class.getSimpleName();
        final String TAG_SUCESS = "success";
        final int[] sukses = new int[1];
        String tag_json_obj = "json_obj_req";

        StringRequest stringReq = new StringRequest(Request.Method.POST, url_delete, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Respon: " + response.toString());

                try {
                    JSONObject jobj = new JSONObject(response);
                    sukses[0] = jobj.getInt(TAG_SUCESS);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "error : "+error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();

                params.put("id",idx);

                return params;
            }
        };

    }
    @Override
    public int getItemCount() {
        return (listData != null)?listData.size(): 0;
    }
    public class TemanViewHolder extends RecyclerView.ViewHolder{
        private CardView cardku;
        private TextView namatext,telpontext;

        public TemanViewHolder(View view){
            super(view);
            cardku = (CardView)view.findViewById(R.id.kartuku);
            namatext = (TextView) view.findViewById(R.id.textnama);
            telpontext = (TextView) view.findViewById(R.id.texttelpon);
        }
    }


}