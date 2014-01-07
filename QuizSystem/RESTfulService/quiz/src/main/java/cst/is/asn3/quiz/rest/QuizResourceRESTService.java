package cst.is.asn3.quiz.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import cst.is.asn3.quiz.model.Question;
import cst.is.asn3.quiz.model.QuestionChoice;
import cst.is.asn3.quiz.model.Quiz;
import cst.is.asn3.quiz.model.QuizTaken;
import cst.is.asn3.quiz.model.User;

@Path("/quiz")
@RequestScoped
public class QuizResourceRESTService {

    @Inject
    private EntityManager em;

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public String GetQuestionByQuizId(@PathParam("id") int id) {
        Quiz quiz = em.find(Quiz.class, id);
        if (quiz == null) {
            return null;
        }
        JSONArray jsonArr = new JSONArray();
        for (Question q : quiz.getQuestions()) {
            JSONObject qObj = new JSONObject();
            qObj.put("text", q.getQuestionText());
            List<QuestionChoice> choices = findQuestionChoiceOrderedByIndex(q);
            JSONArray choiceJArr = new JSONArray();
            for (QuestionChoice qc : choices) {
                JSONObject qcObj = new JSONObject();
                qcObj.put("choiceIndex", qc.getChoiceIndex());
                qcObj.put("choiceText", qc.getChoiceText());
                choiceJArr.put(qcObj);
            }
            qObj.put("choices", choiceJArr);
            jsonArr.put(qObj);
        }
        return jsonArr.toString();
    }

    @GET
    @Path("/user/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public String GetQuizByUserId(@PathParam("id") int id) {
        JSONArray taken = GetQuizTakenByUserId(id);
        JSONArray nonTaken = GetNonTakenQuizByUserId(id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("taken", taken);
        jsonObject.put("nonTaken", nonTaken);
        return jsonObject.toString();
    }

    public JSONArray GetQuizTakenByUserId(@PathParam("id") int id) {
        User user = em.find(User.class, id);
        if (user == null) {
            return null;
        }
        TreeMap<Integer, QuizTaken> qtTreeMap = new TreeMap<Integer, QuizTaken>();
        for (QuizTaken x : user.getQuizTakens()) {
            qtTreeMap.put(x.getQuiz().getIdQuiz(), x);
        }

        JSONArray jsonArr = new JSONArray();
        for (Integer x : qtTreeMap.keySet()) {
            JSONObject pnObj = new JSONObject();
            pnObj.put("id", x);
            pnObj.put("Week", qtTreeMap.get(x).getQuiz().getWeek());
            pnObj.put("Score", qtTreeMap.get(x).getScore());
            jsonArr.put(pnObj);
        }
        return jsonArr;
    }

    public JSONArray GetNonTakenQuizByUserId(@PathParam("id") int id) {
        User user = em.find(User.class, id);
        if (user == null) {
            return null;
        }
        List<Quiz> nonTaken = findNonTakenQuizOrderedByWeek(user);

        JSONArray jsonArr = new JSONArray();
        for (Quiz x : nonTaken) {
            JSONObject pnObj = new JSONObject();
            pnObj.put("id", x.getIdQuiz());
            pnObj.put("Week", x.getWeek());
            jsonArr.put(pnObj);
        }
        return jsonArr;
    }

    public List<Quiz> findNonTakenQuizOrderedByWeek(User user) {
        // get all the IDs of taken quizs.
        List<Integer> quizId = new ArrayList<Integer>();
        for (QuizTaken x : user.getQuizTakens()) {
            quizId.add(x.getQuiz().getIdQuiz());
        }
        // get all non-taken quizs.
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Quiz> criteria = cb.createQuery(Quiz.class);
        Root<Quiz> quiz = criteria.from(Quiz.class);
        criteria.select(quiz).where(cb.not(quiz.get("idQuiz").in(quizId)))
                .orderBy(cb.asc(quiz.get("week")));
        return em.createQuery(criteria).getResultList();
    }

    public List<QuestionChoice> findQuestionChoiceOrderedByIndex(Question q) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<QuestionChoice> criteria = cb
                .createQuery(QuestionChoice.class);
        Root<QuestionChoice> questionChoice = criteria
                .from(QuestionChoice.class);
        criteria.select(questionChoice)
                .where(cb.equal(questionChoice.get("question"), q))
                .orderBy(cb.asc(questionChoice.get("choiceIndex")));
        return em.createQuery(criteria).getResultList();
    }
}
