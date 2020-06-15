package com.example.hearthstonelist;

import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.List;

public class hearthAdapter extends RecyclerView.Adapter<hearthAdapter.hearthViewHolder> {
    private Context context;
    private RequestQueue queue;


    public hearthAdapter(Context context) {

        this.context = context;


        queue = Volley.newRequestQueue(context);
        loadCards();
    }


    public static class hearthViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "hey";
        public LinearLayout row;
        public TextView text;



        public hearthViewHolder(View view) {
            super(view);

            row = view.findViewById(R.id.row);
            text = view.findViewById(R.id.rowtext);


            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Card current = (Card) row.getTag();

                    Intent intent = new Intent(v.getContext(), rowActivity.class);


                    intent.putExtra("name", current.getName());
                    intent.putExtra("text", current.getCardText());
                    intent.putExtra("attack", current.getAttack());
                    intent.putExtra("health", current.getHealth());
                    intent.putExtra("id", current.getCardID());
                    v.getContext().startActivity(intent);
                }
            });

        }

    }

    private List<Card> cards = new ArrayList<>();

    @NonNull
    @Override
    public hearthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hearthrow, parent, false);
        return new hearthViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull hearthAdapter.hearthViewHolder holder, int position) {
        Card card = cards.get(position);

        holder.text.setText(card.getName());

        holder.row.setTag(card);


    }

    public void loadCards() {
        String url = "https://api.hearthstonejson.com/v1/25770/enUS/cards.json";


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject card = response.getJSONObject(i);


                        if (card.has("text") && card.has("attack") && card.has("name") && card.has("health") && card.has("id")) {


                            cards.add(new Card(
                                    card.getString("name"), card.getString("text"), card.getInt("attack"), card.getInt("health"), card.getString("id")
                            ));
                        }
                    }

                    Log.d("Gergef", "onResponse: this ran" + cards);
                    notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("response", "onResponse: Response error", e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("onerrorresponse", "onErrorResponse: what???", error);

            }
        });
        queue.add(request);

    }

    @Override
    public int getItemCount() {
        return cards.size();
    }


}
