package back;

import java.util.ArrayList;

public class ManyAnswer extends Question {

    public ArrayList<String> answer;
    public ArrayList<String> answers;

    public ArrayList<String> getAnswer(){
        return answer;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswer(ArrayList<String> answer) {
        this.answer = answer;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }
}
