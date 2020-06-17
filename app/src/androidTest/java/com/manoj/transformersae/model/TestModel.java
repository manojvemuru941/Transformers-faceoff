package com.manoj.transformersae.model;

import android.app.Activity;
import android.content.Context;

import com.manoj.transformersae.TestUtill.Utill;
import com.manoj.transformersae.ui.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

/**
 * Created by Manoj Vemuru on 2018-09-22.
 */
public class TestModel {
    private static TestModel TESTMODEL;
    private static final String ROOT_DIRECTORY = "responses/";
    private Context mContext;
//    private Model mModel;
    private TestModel(Context context) {
//        mModel = Model.Companion.getInstance(context);
    }

    public static TestModel getInstance(Context context) {
        if(TESTMODEL == null) {
            TESTMODEL = new TestModel(context);
        }
        return TESTMODEL;
    }

    public void loadMockDataSingleTransformer(String fileName, final Activity activity) {
        try {
            String jsonString = LoadMockData(fileName);
            if (jsonString != null) {
                BotModel transformer = Utill.Companion.parseSingleTransformerResponse(jsonString);
                final List<BotModel> list = new ArrayList<>();
                list.add(transformer);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //getMLiveData().setValue(list);
                        ((MainActivity)activity).updateList(list);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMockDataTransformers(String fileName, final Activity activity) {
        try {
            String jsonString = LoadMockData(fileName);
            if (jsonString != null) {
                final List<BotModel> list = Utill.Companion.parseTransformersResponseToList(jsonString);
//                mModel.insertTransformersDB(list);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //getMLiveData().setValue(list);
                        ((MainActivity)activity).updateList(list);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String LoadMockData(String fileName) throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(ROOT_DIRECTORY + fileName );
        if(is !=null)
            return IOUtils.toString(is, "UTF-8");
        else
            return null;
    }

    public void deleteAllTransformers() {
//        mModel.getDB().botDao().deleteAll();
    }

}
