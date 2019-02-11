package presenter;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import entity.AppContent;
import interactor.JsonInteractor;

public class JsonPresenter {
    private Activity _activity;
    private File _file;
    private JsonInteractor _jsonInteractor;

    public JsonPresenter(Activity activity) {
        this._activity = activity;
        File dir = _activity.getFilesDir();
        this._file = new File(dir, "AppContent");
        this._jsonInteractor = new JsonInteractor();

    }

    public void writeToJson(AppContent appContent) {
        JSONObject jsonObject = _jsonInteractor.writeToJson(appContent);
        try {
            saveJSONToFile(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AppContent parseJSON(JSONObject object){
        AppContent appContent = null;
        try {
            appContent = _jsonInteractor.parseJSON(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return appContent;
    }

    private void saveJSONToFile(JSONObject object) throws IOException {
        if (!_file.exists()) _file.createNewFile();

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(_file.getAbsoluteFile()));
        bufferedWriter.write(object.toString());
        bufferedWriter.close();

    }

    public JSONObject readJSONFromFile() throws JSONException {
        String json = null;
        try {
            InputStream is = new FileInputStream(_file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return new JSONObject(json);
    }





}


