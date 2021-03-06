package presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.AppContent;
import entity.Game;
import entity.Question;
import interactor.QuestionInteractor;
import tools.InternetChecker;

public class SetQuestionPresenter extends AsyncTask<Void, Void, Void> {

    @SuppressLint("StaticFieldLeak")
    private Activity _activity;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar _progress;
    private Intent _intent;

    private AppContent _appContent;
    private Game _game;

    private QuestionInteractor _questionInteractor;

    public SetQuestionPresenter(Game game, Activity activity, ProgressBar progress, Intent intent, AppContent appContent) {
        this._activity = activity;
        this._progress = progress;
        this._intent = intent;
        this._appContent = appContent;
        this._game = game;
        this._questionInteractor = new QuestionInteractor();
    }

    @Override
    protected void onPreExecute() {
        _progress.setVisibility(View.VISIBLE);
        InternetChecker internetChecker = new InternetChecker(_activity);
        if(!internetChecker.isOnline()){
            this.cancel(true);
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            _game.getQuestions().clear();
            _questionInteractor.setQuestions(_game, _activity, _appContent.getProfile());

            if(!_questionInteractor.getImage().isEmpty() && !isAllNulls(_questionInteractor.getImage())) {
                for (byte[] image : _questionInteractor.getImage()) {
                    if(image != null) {
                        int index = _questionInteractor.getImage().indexOf(image);
                        Question question = _game.getQuestions().get(index);
                        String path = writeToFile(image, question.getID());
                        question.setImage(path);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void voids) {
        _questionInteractor.endWork();
        if (_questionInteractor.isSuccess()) {
            LinearLayout.LayoutParams _lp  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            PossibleAnswerPresenter possibleAnswerPresenter = new PossibleAnswerPresenter(_activity, _intent, _progress, _lp, _appContent, _game);
            possibleAnswerPresenter.execute();
        }
    }

    private String writeToFile(byte[] image, int questionID) throws IOException {
        File dir = _activity.getFilesDir();
        File root = new File(dir + "/images/");
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

    public boolean isAllNulls(Iterable<?> array) {
        for (Object element : array)
            if (element != null) return false;
        return true;
    }
}