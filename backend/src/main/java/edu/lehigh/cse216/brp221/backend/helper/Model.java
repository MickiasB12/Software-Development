package edu.lehigh.cse216.brp221.backend.helper;

import edu.lehigh.cse216.brp221.backend.Database;

import java.util.ArrayList;

public interface Model {

    int insertRow(String subject, String message, int likes);

    ArrayList<Database.RowData> selectAll();

    Database.RowData selectOne(int id);

    boolean deleteRow(int id);

    boolean updateOne(String subject, String message, int id);

    boolean likeOne(int id);
}
