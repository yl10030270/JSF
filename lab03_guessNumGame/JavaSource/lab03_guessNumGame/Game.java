package lab03_guessNumGame;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
// or @ManagedBean
@ConversationScoped
public class Game implements Serializable {
    private int lowerBound = 1;
    private int upperBound = 100;
    private int numberOfTries = 0;
    private int answer;

    @Inject
    private Conversation conversation;

    @Inject
    @RandomNum
    private int randomNumber;

    public Game() {
    }

    // Control start and end of conversation
    public void start() {
        conversation.begin();
    }

    public void end() {
        conversation.end();
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }

    public int getNumberOfTries() {
        return numberOfTries;
    }
    
    public int getRandomNumber() {
        return randomNumber;        
    }
    
    public void setNumberOfTries(int numberOfTries) {
        this.numberOfTries = numberOfTries;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
    
    public String startGame(){
        start();
        return answerAction();
    }
    
    public String answerAction() {
        numberOfTries++;
        if (getAnswer() < randomNumber) {
            setLowerBound(getAnswer());
            return "testlow";
        } else if (getAnswer() > randomNumber) {
            setUpperBound(getAnswer());
            return "testhigh";
        } else {
            end();
            return "testcorrect";
        }
    }

}
