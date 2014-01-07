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

import cst.is.asn3.quiz.model.QuizTaken;
import cst.is.asn3.quiz.model.Quiz;
import cst.is.asn3.quiz.model.User;

/**
 * Backing bean for QuizTaken entities.
 * <p>
 * This class provides CRUD functionality for all QuizTaken entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class QuizTakenBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving QuizTaken entities
    */

   private Long id;

   public Long getId()
   {
      return this.id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   private QuizTaken quizTaken;

   public QuizTaken getQuizTaken()
   {
      return this.quizTaken;
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
         this.quizTaken = this.example;
      }
      else
      {
         this.quizTaken = findById(getId());
      }
   }

   public QuizTaken findById(Long id)
   {

      return this.entityManager.find(QuizTaken.class, id);
   }

   /*
    * Support updating and deleting QuizTaken entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.quizTaken);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.quizTaken);
            return "view?faces-redirect=true&id=" + this.quizTaken.getId();
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
         QuizTaken deletableEntity = findById(getId());
         User user = deletableEntity.getUser();
         user.getQuizTakens().remove(deletableEntity);
         deletableEntity.setUser(null);
         this.entityManager.merge(user);
         Quiz quiz = deletableEntity.getQuiz();
         quiz.getQuizTakens().remove(deletableEntity);
         deletableEntity.setQuiz(null);
         this.entityManager.merge(quiz);
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
    * Support searching QuizTaken entities with pagination
    */

   private int page;
   private long count;
   private List<QuizTaken> pageItems;

   private QuizTaken example = new QuizTaken();

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

   public QuizTaken getExample()
   {
      return this.example;
   }

   public void setExample(QuizTaken example)
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
      Root<QuizTaken> root = countCriteria.from(QuizTaken.class);
      countCriteria = countCriteria.select(builder.count(root)).where(
            getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria)
            .getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<QuizTaken> criteria = builder.createQuery(QuizTaken.class);
      root = criteria.from(QuizTaken.class);
      TypedQuery<QuizTaken> query = this.entityManager.createQuery(criteria
            .select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(
            getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<QuizTaken> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      User user = this.example.getUser();
      if (user != null)
      {
         predicatesList.add(builder.equal(root.get("user"), user));
      }
      Quiz quiz = this.example.getQuiz();
      if (quiz != null)
      {
         predicatesList.add(builder.equal(root.get("quiz"), quiz));
      }
      int score = this.example.getScore();
      if (score != 0)
      {
         predicatesList.add(builder.equal(root.get("score"), score));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<QuizTaken> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back QuizTaken entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<QuizTaken> getAll()
   {

      CriteriaQuery<QuizTaken> criteria = this.entityManager
            .getCriteriaBuilder().createQuery(QuizTaken.class);
      return this.entityManager.createQuery(
            criteria.select(criteria.from(QuizTaken.class))).getResultList();
   }

   @Resource
   private SessionContext sessionContext;

   public Converter getConverter()
   {

      final QuizTakenBean ejbProxy = this.sessionContext.getBusinessObject(QuizTakenBean.class);

      return new Converter()
      {

         @Override
         public Object getAsObject(FacesContext context,
               UIComponent component, String value)
         {

            return ejbProxy.findById(Long.valueOf(value));
         }

         @Override
         public String getAsString(FacesContext context,
               UIComponent component, Object value)
         {

            if (value == null)
            {
               return "";
            }

            return String.valueOf(((QuizTaken) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private QuizTaken add = new QuizTaken();

   public QuizTaken getAdd()
   {
      return this.add;
   }

   public QuizTaken getAdded()
   {
      QuizTaken added = this.add;
      this.add = new QuizTaken();
      return added;
   }
}