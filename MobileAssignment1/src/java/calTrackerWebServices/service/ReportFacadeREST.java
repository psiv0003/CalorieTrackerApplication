/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calTrackerWebServices.service;

import calTrackerWebServices.Report;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Pori
 */
@Stateless
@Path("caltrackerwebservices.report")
public class ReportFacadeREST extends AbstractFacade<Report> {

    @PersistenceContext(unitName = "MobileAssignment1PU")
    private EntityManager em;

    public ReportFacadeREST() {
        super(Report.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Report entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Report entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Report find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Report> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
     
     //GET method for getting based on userId

    @GET
    @Path("findByUserId/{userid}")
    @Produces({"application/json"})
    public List<Report> findByUserId(@PathParam("userid") Integer userid) {
    Query query = em.createNamedQuery("Report.findByUserId");
    query.setParameter("userid", userid);
    return query.getResultList();
    }
    
     //GET method for getting based on date

    @GET
    @Path("findByDate/{date}")
    @Produces({"application/json"})
    public List<Report> findByDate(@PathParam("date") String date) throws ParseException {
    Query query = em.createNamedQuery("Report.findByDate"); 
    Date convertedDate = new Date();
    convertedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
    
    query.setParameter("date", convertedDate);
    return query.getResultList();
    }
    
    
      //GET method for getting based on total calConsumed

    @GET
    @Path("findByTotalcaloriesconsumed/{totalcaloriesconsumed}")
    @Produces({"application/json"})
    public List<Report> findByTotalcaloriesconsumed(@PathParam("totalcaloriesconsumed") Integer totalcaloriesconsumed) {
    Query query = em.createNamedQuery("Report.findByTotalcaloriesconsumed");
    query.setParameter("totalcaloriesconsumed", totalcaloriesconsumed);
    return query.getResultList();
    }
    
    
      //GET method for getting based on total calBurned

    @GET
    @Path("findByTotalcaloriesburned/{totalcaloriesburned}")
    @Produces({"application/json"})
    public List<Report> findByTotalcaloriesburned(@PathParam("totalcaloriesburned") Integer totalcaloriesburned) {
    Query query = em.createNamedQuery("Report.findByTotalcaloriesburned");
    query.setParameter("totalcaloriesburned", totalcaloriesburned);
    return query.getResultList();
    }
    
      //GET method for getting based on total steps

    @GET
    @Path("findByTotalsteps/{totalsteps}")
    @Produces({"application/json"})
    public List<Report> findByTotalsteps(@PathParam("totalsteps") Integer totalsteps) {
    Query query = em.createNamedQuery("Report.findByTotalsteps");
    query.setParameter("totalsteps", totalsteps);
    return query.getResultList();
    }
    
      //GET method for getting based on calorie goal

     @GET
    @Path("findByCaloriegoal/{caloriegoal}")
    @Produces({"application/json"})
    public List<Report> findByCaloriegoal(@PathParam("caloriegoal") Integer caloriegoal) {
    Query query = em.createNamedQuery("Report.findByCaloriegoal");
    query.setParameter("caloriegoal", caloriegoal);
    return query.getResultList();
    }
    
    //c) You will write a new REST method that enables querying two tables using a combination of two attributes
    //in the condition where each attribute is from a different table. The query should be a DYNAMIC query using an 
    //IMPLICIT join. 
    
    @GET
    @Path("findByActivityLevelAndCaloriesBurned/{activitylevel}/{totalcaloriesburned}")
    @Produces({"application/json"})
    public List<Report> findByActivityLevelAndCaloriesBurned(@PathParam("activitylevel") Integer
    activitylevel, @PathParam("totalcaloriesburned") Integer totalcaloriesburned) {
    TypedQuery<Report> q = em.createQuery("SELECT r FROM Report r WHERE r.userid.activitylevel = :activitylevel AND r.totalcaloriesburned = :totalcaloriesburned", Report.class);
    q.setParameter("activitylevel", activitylevel);
    q.setParameter("totalcaloriesburned", totalcaloriesburned);

    return q.getResultList();
    }
    
    //d) You will write a new REST method that enables querying two tables using a combination of two attributes in 
    //the condition where each attribute is from a different table. The query should be a STATIC query using an 
    //IMPLICIT JOIN. For this, you also need to show the code for the NamedQuery in the entity class. 
    
    
    @GET
    @Path("findByCalorieGoalAndGender/{caloriegoal}/{gender}")
    @Produces({"application/json"})
    public List<Report> findByCalorieGoalAndGender(@PathParam("caloriegoal") Integer caloriegoal, @PathParam("gender") String gender) {
    Query query = em.createNamedQuery("Report.findByCalorieGoalAndGender");
    query.setParameter("caloriegoal", caloriegoal);
    query.setParameter("gender", gender);
    return query.getResultList();
    }

    
    //Get the total calories consumed, total calories burned and remaining calorie 
   
    @GET
    @Path("findCalorieDetails/{userid}/{date}")
    @Produces({"application/json"})
    public String findCalorieDetails(@PathParam("userid") Integer userid, @PathParam("date") String date) throws ParseException {
    
        int totalCaloriesConsumed =0;
        int totalCaloriesBurned = 0;
        int remainingCalories = 0;
        int calorieGoal = 0;
        int totalSteps = 0;
        int reportId = 0;
        Date convertedDate = new Date();
        convertedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
    
        TypedQuery<Report> q = em.createQuery("SELECT r FROM Report r WHERE r.userid.userid = :userid AND r.date = :date ", Report.class);  
        q.setParameter("userid", userid);
        q.setParameter("date", convertedDate);
        
        List<Report> report = q.getResultList();
         for(Report r1: report){
             totalCaloriesConsumed = r1.getTotalcaloriesconsumed();
             totalCaloriesBurned = r1.getTotalcaloriesburned();
             calorieGoal = r1.getCaloriegoal();
             totalSteps = r1.getTotalsteps();
             reportId = r1.getReportid();
             
            }
         remainingCalories = calorieGoal - totalCaloriesConsumed - totalCaloriesBurned   ;


    return "{ \"Total Calories Consumed\" : \""+totalCaloriesConsumed+"\", \"Total Calories Burned\": \""+totalCaloriesBurned+"\" , \"Remainng Calories\" : \"" +String.valueOf(remainingCalories)+ "\", \"Calorie Goal\" : \""+ String.valueOf(calorieGoal)+"\" , \"Total Steps\" : \" "+ String.valueOf(totalSteps)+"\", \"id\": \"" + String.valueOf(reportId) + "\" }";
    }
    
        //Return report details for a particular date range
   
    @GET
    @Path("getDetailsForDateRange/{userid}/{startdate}/{enddate}")
    @Produces({"application/json"})
    public String getDetailsForDateRange(@PathParam("userid") Integer userid, @PathParam("startdate") String startdate, @PathParam("enddate") String enddate ) throws ParseException {
      
      Date startconvertedDate = new Date();
      startconvertedDate = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
      Date endconvertedDate = new Date();
      endconvertedDate = new SimpleDateFormat("yyyy-MM-dd").parse(enddate);
        
        int CaloriesBurned= 0;
        int totalCaloriesBurned = 0;
        int totalCaloriesConsumed = 0;
        int CaloriesConsumed = 0;
        int stepsTaken = 0;
        int totalStepstaken = 0;
       
        
        TypedQuery<Report> q = em.createQuery("SELECT r FROM Report r WHERE r.userid.userid = :userid AND r.date BETWEEN :startdate AND :enddate ", Report.class);  
        q.setParameter("userid", userid);
        q.setParameter("startdate", startconvertedDate);
        q.setParameter("enddate", endconvertedDate);

        
        List<Report> report = q.getResultList();
         for(Report r1: report){
             totalCaloriesConsumed = totalCaloriesConsumed + r1.getTotalcaloriesconsumed();
             totalCaloriesBurned = totalCaloriesBurned + r1.getTotalcaloriesburned();
             totalStepstaken = totalStepstaken + r1.getTotalsteps();
             
            }



    return "{ \"Total Calories Consumed\" : \""+totalCaloriesConsumed+"\", \"Total Calories Burned\": \""+totalCaloriesBurned+"\",\"Total Steps\" : \" "+totalStepstaken+ "\" }";
        }
    
}
