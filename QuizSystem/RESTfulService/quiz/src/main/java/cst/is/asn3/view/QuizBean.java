package cst.is.asn3.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import cst.is.asn3.quiz.model.Quiz;
import cst.is.asn3.quiz.model.Question;
import cst.is.asn3.quiz.model.QuizTaken;
import java.util.Iterator;

/**
 * Backing bean for Quiz entities.
 * <p>
 * This class provides CRUD functionality for all Quiz entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class QuizBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving Quiz entities
    */

   private Integer id;

   public Integer getId()
   {
      return this.id;
   }

   public void setId(Integer id)
   {
      this.id = id;
   }

   private Quiz quiz;

   public Quiz getQuiz()
   {
      return this.quiz;
   }

   @Inject
   private Conversation conversation;

   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   private EntityManager entityManager;

   public String create()
   {

      this.conversation.begin();
      return "create?faces-redirect=true";
   }

   public void retrieve()
   {

      if (FacesContext.getCurrentInstance().isPostback())
      {
         return;
      }

      if (this.conversation.isTransient())
      {
         this.conversation.begin();
      }

      if (this.id == null)
      {
         this.quiz = this.example;
      }
      else
      {
         this.quiz = findById(getId());
      }
   }

   public Quiz findById(Integer id)
   {

      return this.entityManager.find(Quiz.class, id);
   }

   /*
    * Support updating and deleting Quiz entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.quiz);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.quiz);
            return "view?faces-redirect=true&id=" + this.quiz.getIdQuiz();
         }
      }
      catch (Exception e)
      {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
         return null;
      }
   }

   public String delete()
   {
      this.conversation.end();

      try
      {
         Quiz deletableEntity = findById(getId());
         Iterator<QuizTaken> iterQuizTakens = deletableEntity.getQuizTakens().iterator();
         for (; iterQuizTakens.hasNext();)
         {
            QuizTaken nextInQuizTakens = iterQuizTakens.next();
            nextInQuizTakens.setQuiz(null);
            iterQuizTakens.remove();
            this.entityManager.merge(nextInQuizTakens);
         }
         Iterator<Question> iterQuestions = deletableEntity.getQuestions().iterator();
         for (; iterQuestions.hasNext();)
         {
            Question nextInQuestions = iterQuestions.next();
            nextInQuestions.setQuiz(null);
            iterQuestions.remove();
            this.entityManager.merge(nextInQuestions);
         }
         this.entityManager.remove(deletableEntity);
         this.entityManager.flush();
         return "search?faces-redirect=true";
      }
      catch (Exception e)
      {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
         return null;
      }
   }

   /*
    * Support searching Quiz entities with pagination
    */

   private int page;
   private long count;
   private List<Quiz> pageItems;

   private Quiz example = new Quiz();

   public int getPage()
   {
      return this.page;
   }

   public void setPage(int page)
   {
      this.page = page;
   }

   public int getPageSize()
   {
      return 10;
   }

   public Quiz getExample()
   {
      return this.example;
   }

   public void setExample(Quiz example)
   {
      this.example = example;
   }

   public void search()
   {
      this.page = 0;
   }

   public void paginate()
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

      // Populate this.count

      CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
      Root<Quiz> root = countCriteria.from(Quiz.class);
      countCriteria = countCriteria.select(builder.count(root)).where(
            getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria)
            .getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<Quiz> criteria = builder.createQuery(Quiz.class);
      root = criteria.from(Quiz.class);
      TypedQuery<Quiz> query = this.entityManager.createQuery(criteria
            .select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(
            getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<Quiz> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      int week = this.example.getWeek();
      if (week != 0)
      {
         predicatesList.add(builder.equal(root.get("week"), week));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<Quiz> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back Quiz entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<Quiz> getAll()
   {

      CriteriaQuery<Quiz> criteria = this.entityManager
            .getCriteriaBuilder().createQuery(Quiz.class);
      return this.entityManager.createQuery(
            criteria.select(criteria.from(Quiz.class))).getResultList();
   }

   @Resource
   private SessionContext sessionContext;

   public Converter getConverter()
   {

      final QuizBean ejbProxy = this.sessionContext.getBusinessObject(QuizBean.class);

      return new Converter()
      {

         @Override
         public Object getAsObject(FacesContext context,
               UIComponent component, String value)
         {

            return ejbProxy.findById(Integer.valueOf(value));
         }

         @Override
         public String getAsString(FacesContext context,
               UIComponent component, Object value)
         {

            if (value == null)
            {
               return "";
            }

            return String.valueOf(((Quiz) value).getIdQuiz());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private Quiz add = new Quiz();

   public Quiz getAdd()
   {
      return this.add;
   }

   public Quiz getAdded()
   {
      Quiz added = this.add;
      this.add = new Quiz();
      return added;
   }
}