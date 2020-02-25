package edu.lehigh.cse216.mib222.phase0;

import android.widget.Button;

public class Datum {
    /**
     * An integer index for this piece of data
     */

    int mIndex;  //id number for each messa
    Button like;  //Like button that tracks with each message
    //int numLikes;
    Button dislike; //dislike button that tracks with each message
    /**
     * The string contents that comprise this piece of data
     */
    String mTitle;  //Title of the post

    String mContent; //Body of the post

    String mCreated; //date and time it was created


    /**
     * Construct a Datum by setting its index and text
     *
     * @param idx The index of this piece of data
     * @param txt The string contents for this piece of data
     * @param num for the content of the psot
     * @param c for the time of the post
     * @param x for the like button
     * @param y for the dislike button
     */
    Datum(int idx, String txt, String num, String c, Button x, Button y) {
        mIndex = idx;
        mTitle = txt;
        mContent = num;
        mCreated = c;
        like = x;
        dislike = y;
    }
}
