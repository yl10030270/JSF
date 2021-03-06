package cst.is.asn3.quiz.model;
// Generated 3-Dec-2013 8:32:49 PM by Hibernate Tools 3.4.0.CR1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Question generated by hbm2java
 */
@Entity
@Table(name="Question")
public class Question  implements java.io.Serializable {


     private Integer idQuestion;
     private Quiz quiz;
     private String questionText;
     private String answer;
     private Set<QuestionChoice> questionChoices = new HashSet<QuestionChoice>(0);

    public Question() {
    }

	
    public Question(Quiz quiz, String questionText, String answer) {
        this.quiz = quiz;
        this.questionText = questionText;
        this.answer = answer;
    }
    public Question(Quiz quiz, String questionText, String answer, Set<QuestionChoice> questionChoices) {
       this.quiz = quiz;
       this.questionText = questionText;
       this.answer = answer;
       this.questionChoices = questionChoices;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="idQuestion", unique=true, nullable=false)
    public Integer getIdQuestion() {
        return this.idQuestion;
    }
    
    public void setIdQuestion(Integer idQuestion) {
        this.idQuestion = idQuestion;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idQuiz", nullable=false)
    public Quiz getQuiz() {
        return this.quiz;
    }
    
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    
    @Column(name="questionText", nullable=false, length=225)
    public String getQuestionText() {
        return this.questionText;
    }
    
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    
    @Column(name="answer", nullable=false, length=225)
    public String getAnswer() {
        return this.answer;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
    }

@OneToMany(fetch=FetchType.EAGER, mappedBy="question")
    public Set<QuestionChoice> getQuestionChoices() {
        return this.questionChoices;
    }
    
    public void setQuestionChoices(Set<QuestionChoice> questionChoices) {
        this.questionChoices = questionChoices;
    }




}


