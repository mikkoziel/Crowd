package presenter;

import java.io.IOException;

import interactor.QuestionInteractor;

public class FilePresenter {

    private QuestionInteractor _questionInteractor;

    public FilePresenter(){
        this._questionInteractor = new QuestionInteractor();
    }

    public byte[] readFromFile(String path){
        return _questionInteractor.readFromFile(path);
    }

    public void saveToFile(byte[] array, int ID){
        try {
            _questionInteractor.writeToFile(array, ID);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
