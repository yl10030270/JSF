package cst.is.asn3.quiz.model;

// Generated 3-Dec-2013 8:32:49 PM by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * QuestionChoice generated by hbm2java
 */
@Entity
@Table(name = "QuestionChoice")
public class QuestionChoice implements java.io.Serializable {

    private int idQuestionChoice;
    private Question question;
    private String choiceIndex;
    private String choiceText;

    public QuestionChoice() {
    }

    public QuestionChoice(int idQuestionChoice, Question question,
            String choiceIndex, String choiceText) {
        this.idQuestionChoice = idQuestionChoice;
        this.question = question;
        this.choiceIndex = choiceIndex;
        this.choiceText = choiceText;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "idQuestionChoice", unique = true, nullable = false)
    public int getIdQuestionChoice() {
        return this.idQuestionChoice;
    }

    public void setIdQuestionChoice(int idQuestionChoice) {
        this.idQuestionChoice = idQuestionChoice;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idQuestion", nullable = false)
    public Question getQuestion() {
        return this.question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Column(name = "choiceIndex", nullable = false, length = 2)
    public String getChoiceIndex() {
        return this.choiceIndex;
    }

    public void setChoiceIndex(String choiceIndex) {
        this.choiceIndex = choiceIndex;
    }

    @Column(name = "choiceText", nullable = false, length = 225)
    public String getChoiceText() {
        return this.choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

}
