package edu.lehigh.cse216.mib222.phase0;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    interface ClickListener {
        void onClick(Datum d);
    }

    //Button like;
    private ClickListener mClickListener;
    private RequestQueue queue;

    /**
     *
     * @return the value of clickListener
     */
    ClickListener getClickListener() {
        return mClickListener;
    }

    /**
     *
     * @param c for setting the clicklistener
     */

    void setClickListener(ClickListener c) {
        mClickListener = c;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;  //Textview holding string for title
        TextView mContent; //Textview holding string for content
        TextView mCreated; //Textview holding string for time of the post
        Button like;    //Button for the like button
        Button dislike;  //Button for the dislike button
        ViewHolder(View itemView) {
            super(itemView);
            //this.mIndex = (TextView) itemView.findViewById(R.id.listItemIndex);
            this.mTitle = (TextView) itemView.findViewById(R.id.listItemTitle);  //attaches the id from the list_item.xml to the assigned Textview
            this.mContent = (TextView) itemView.findViewById(R.id.listItemContent);
            this.mCreated = (TextView) itemView.findViewById(R.id.listItemCreated);
            this.like = (Button) itemView.findViewById(R.id.like);  //id like button from list_item.xml to Like button here
            this.dislike = (Button) itemView.findViewById(R.id.dislike); //id dislike button from the list_item.xml to dislike button here
            //this.countLikes = 0;

        }
    }

    private ArrayList<Datum> mData; //holds all the parameters declared in Datum
    private LayoutInflater mLayoutInflater;

    ItemListAdapter(Context context, ArrayList<Datum> data) {
        mData = data;
        queue = Volley.newRequestQueue(context);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    } //return's the toal number of messages

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Datum d = mData.get(position);
        final int id = d.mIndex;
        holder.mTitle.setText(d.mTitle);  //gets the title
        holder.mContent.setText(d.mContent); //gets the content
        holder.mCreated.setText(d.mCreated); //gets the time it was created
        // Attach a click listener to the view we are configuring
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onClick(d);
                //countLikes++;
            }
        };
        holder.mTitle.setOnClickListener(listener);  //sets the click listener to the assigned TextView (mTitle)
        holder.mContent.setOnClickListener(listener); //the same here for mContent
        holder.mCreated.setOnClickListener(listener); //the same here for the textview
        /*Attaches a click listener when the like button is clicked. Theoretically, when clicked, it should increase the number of likes
        in the backend, but since the backend's likeUpdate doesn't work, it can't communicate with the backend. However, it shows
        the number of likes */
        holder.like.setOnClickListener(new View.OnClickListener(){
            int count = 0; //to hold track of the number of upvotes

            @Override
            public void onClick(View v) { //when clicked
                Button b = (Button) v; //we do this so we can get the like button in this separate inner class (by casting View v into button)
                count++; //increments when the like button is clicked
                b.setText("Upvotes: " + String.valueOf(count)); //shows Upvote's total num
                JSONObject object = new JSONObject(); //initializes a json object
                try {
                    //input your API parameters
                    object.put("mLikes", count);
                } catch (JSONException e) {//if error
                    e.printStackTrace();
                }
                String url = "https://super-deuper-epic-app.herokuapp.com/messages/like/" + id;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, object,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                //successful
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
                queue.add(jsonObjectRequest); //adds the the request on RequestQueue

            }
        });
        //Attaches a click listener when the downvote button is clicked
        holder.dislike.setOnClickListener(new View.OnClickListener(){
            int discount = 0; //to hold track of the number of downvotes
            @Override
            public void onClick(View v){ //when clicked

                Button b = (Button)v; //we do this so we can get the like button in this separate inner class
                discount++; //increments when the dislike button is clicked
                b.setText("Downvotes: " + String.valueOf(discount));  //shows Downvote's total num
            }
        });
    }
}