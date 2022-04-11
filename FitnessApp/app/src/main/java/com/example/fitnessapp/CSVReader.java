package com.example.fitnessapp;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    Context context;
    String fileName;
    List<String[]> rows = new ArrayList<>();

    public CSVReader(Context context, String fileName) {
        this.context = context;
        this.fileName = fileName;
    }

    public List<String[]> readCSV() {
        try {
            InputStream is = context.getAssets().open(fileName);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] row = line.split(",");
                    rows.add(row);
                }
            }
        }
        catch (IOException e){
            // pass
        }
        return rows;
    }
}