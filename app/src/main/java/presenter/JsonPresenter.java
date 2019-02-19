//package presenter;
//
//import android.app.Activity;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStream;
//
//import entity.AppContent;
//import interactor.JsonInteractor;
//
//public class JsonPresenter {
//    private Activity _activity;
//    private File _file;
//    private JsonInteractor _jsonInteractor;
//    public JsonPresenter(Activity activity) {
//        this._activity = activity;
//        File dir = _activity.getFilesDir();
//        this._file = new File(dir, "AppContent");
//        this._jsonInteractor = new JsonInteractor();
//    }
//
//    public void writeToJson(AppContent appContent, int mode) {
//        JSONObject jsonObject = _jsonInteractor.writeToJson(appContent, mode);
//        saveJSONToFile(jsonObject);
//    }
//
//    private void saveJSONToFile(JSONObject object) {
//        try {
//            if (!_file.exists()) _file.createNewFile();
//
////            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(_file.getAbsoluteFile()));
////            bufferedWriter.write(object.toString());
////            bufferedWriter.close();
//            FileOutputStream fos = null;
//
//            fos = new FileOutputStream(_file);
//            fos.write(object.toString().getBytes());
//            fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public AppContent getJSON(int mode){
//        JSONObject object = null;
//        object = readJSONFromFile();
//        return parseJSON(object, mode);
//    }
//
//    private JSONObject readJSONFromFile() {
//        String json = null;
//        try {
//            InputStream is = new FileInputStream(_file);
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//
//            return new JSONObject(json);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
//
//
//    private AppContent parseJSON(JSONObject object, int mode){
//        AppContent appContent = null;
//        try {
//            appContent = _jsonInteractor.parseJSON(object, mode);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return appContent;
//    }
//
//
//
//
//
//
//}
//
//
