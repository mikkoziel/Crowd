package back;

import java.util.ArrayList;

public class OneAnswer extends Question{

    public String answer;
    public ArrayList<String> answers;

    public OneAnswer(String question, ArrayList<String> answers){
        this.question = question;
        this.answers = answers;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
