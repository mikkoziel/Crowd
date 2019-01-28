package interactor;

import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import entity.Game;
import entity.Question;

public class QuestionInteractor {
    private DataBaseConnector _dbConnector;
    private String _result;
    private Boolean _isSuccess;

    public QuestionInteractor()
    {
        this._dbConnector = new DataBaseConnector();
        this._result = null;
        this._isSuccess = false;
    }

    public void emptyQuestions(Game game){game.getQuestions().clear();}

    public void setQuestions(Game game) throws SQLException {
        ArrayList<Integer> ids = selectQuestionID(game);
        setRandomQuestions(ids, game);
    }

    private ArrayList<Integer> selectQuestionID(Game game) throws SQLException {
        String query = "select questionID from Question where gameID= " + game.getGameID();

        ResultSet res = _dbConnector.runQuery(query);
        ArrayList<Integer> ids = new ArrayList<>();

        while(res.next()) {
            ids.add(res.getInt("questionID"));
        }
        return ids;
    }

    private void setRandomQuestions(ArrayList<Integer> ids, Game game) throws SQLException {
        Random rand = new Random();
        int numberOfQuestions = (ids.size() < 10) ? ids.size() : 10;

        for(int i = 0; i < numberOfQuestions; i++){
            int random = rand.nextInt(ids.size());
            int randomElement = ids.get(random);
            ids.remove(random);
            getRandomQuestion(game, randomElement);
        }
        setSuccess("Game Starting");
    }

    private void getRandomQuestion(Game game, int randomIndex) throws SQLException {
        String query = "select * from Question where questionID= " + randomIndex;

        ResultSet resultSet = _dbConnector.runQuery(query);
        addPossibleQuestion(resultSet, game);
    }

    private void addPossibleQuestion(ResultSet resultSet, Game game) throws SQLException {
        if(resultSet.next()) {
            String content = resultSet.getString("questionText");
            int ID = resultSet.getInt("questionID");
            int type = resultSet.getInt("typeID");
            Boolean defaultAnswer = resultSet.getBoolean("defaultAnswer");

            Blob blobImage = resultSet.getBlob("questionImage");
            Question question;
            if (resultSet.wasNull())
                question = new Question(content, ID, type, defaultAnswer);

            else {
                byte[] byteImage = blobImage.getBytes(1, (int) blobImage.length());
                String path = null;
                try {
                    path = writeToFile(byteImage, ID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                question = new Question(content, ID, type, defaultAnswer, path);
            }
            game.addQuestion(question);
        }
    }

    private String writeToFile(byte[] image, int questionID) throws IOException {
        File dir = Environment.getExternalStorageDirectory();
        File root = new File(dir + "/Crowd/");
        if (!root.exists()) root.mkdirs();
        File file = new File(root, String.valueOf(questionID));
        if (!file.exists()) file.createNewFile();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(image);
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        }
        catch (IOException ioe) {
            System.out.println("Exception while writing file " + ioe);
        }
        finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            }
            catch (IOException ioe) {
                System.out.println("Error while closing stream: " + ioe);
            }
        }
        return file.getAbsolutePath();

    }

    public byte[] readFromFile(String path){
        File file = new File(path);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean deleted = file.delete();
        return bytes;
    }

    private void setSuccess(String message)
    {
        _result = message;
        _isSuccess = true;
    }

    private void setFailure(String message)
    {
        _result = message;
        _isSuccess = false;
    }

    public Boolean isSuccess(){return _isSuccess;}
    public String getResult(){return _result;}


    public void endWork()
    {
        try {
            _dbConnector.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
