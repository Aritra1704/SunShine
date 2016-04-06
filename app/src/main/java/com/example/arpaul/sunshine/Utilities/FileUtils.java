package com.example.arpaul.sunshine.Utilities;

import android.content.Context;


import java.io.File;
import java.io.FileWriter;

/**
 * Created by ARPaul on 19-03-2016.
 */
public class FileUtils {
    public void writeToFileOnInternalStorage(Context mContext, String directory, String sFileName, String sBody){
        File file = new File(directory,sFileName);
        if(!file.exists()){
            file.mkdir();
        }

        try{
            File gpxfile = new File(file, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();

        }catch (Exception e){

        }
    }
}
