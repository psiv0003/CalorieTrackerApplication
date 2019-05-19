/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calTrackerWebServices.service;

import calTrackerWebServices.Userdetails;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
@Path("caltrackerwebservices.userdetails")
public class UserdetailsFacadeREST extends AbstractFacade<Userdetails> {

    @PersistenceContext(unitName = "MobileAssignment1PU")
    private EntityManager em;

    public UserdetailsFacadeREST() {
        super(Userdetails.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Userdetails entity) {
        super.create(entity);
        
       // return "Success";
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Userdetails entity) {
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
    public Userdetails find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Userdetails> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Userdetails> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("getCountOfUsers")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCountOfUsers() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
         
      //GET method for getting based on name

    @GET
    @Path("findByName/{name}")
    @Produces({"application/json"})
    public List<Userdetails> findByName(@PathParam("name") String name) {
    Query query = em.createNamedQuery("Userdetails.findByName");
    query.setParameter("name", name);
    return query.getResultList();
    }
    
     //GET method for getting based on surname

    @GET
    @Path("findBySurname/{surname}")
    @Produces({"application/json"})
    public List<Userdetails> findBySurname(@PathParam("surname") String surname) {
    Query query = em.createNamedQuery("Userdetails.findBySurname");
    query.setParameter("surname", surname);
    return query.getResultList();
    }
//    
      //GET method for getting based on email

    @GET
    @Path("findByEmail/{email}")
    @Produces({"application/json"})
    public List<Userdetails> findByEmail(@PathParam("email") String email) {
    Query query = em.createNamedQuery("Userdetails.findByEmail");
    query.setParameter("email", email);
    return query.getResultList();
    }
//    
       //GET method for getting based on dob

    @GET
    @Path("findByDob/{dob}")
    @Produces({"application/json"})
    public List<Userdetails> findByDob(@PathParam("dob") String dob) throws ParseException {
    Query query = em.createNamedQuery("Userdetails.findByDob");
    Date convertedDate = new Date();
    convertedDate = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
    query.setParameter("dob", convertedDate);
    return query.getResultList();
    }
    
    
       //GET method for getting based on gender

    @GET
    @Path("findByGender/{gender}")
    @Produces({"application/json"})
    public List<Userdetails> findByGender(@PathParam("gender") String gender) {
    Query query = em.createNamedQuery("Userdetails.findByGender");
    query.setParameter("gender", gender);
    return query.getResultList();
    }
    
       //GET method for getting based on address

    @GET
    @Path("findByAddress/{address}")
    @Produces({"application/json"})
    public List<Userdetails> findByAddress(@PathParam("address") String address) {
    Query query = em.createNamedQuery("Userdetails.findByAddress");
    query.setParameter("address", address);
    return query.getResultList();
    }
    
    
        //GET method for getting based on postcode

    @GET
    @Path("findByPostcode/{postcode}")
    @Produces({"application/json"})
    public List<Userdetails> findByPostcode(@PathParam("postcode") Integer postcode) {
    Query query = em.createNamedQuery("Userdetails.findByPostcode");
    query.setParameter("postcode", postcode);
    return query.getResultList();
    }
    
    
       //GET method for getting based on activitylevel

    @GET
    @Path("findByActivitylevel/{activitylevel}")
    @Produces({"application/json"})
    public List<Userdetails> findByActivitylevel(@PathParam("activitylevel") Integer activitylevel) {
    Query query = em.createNamedQuery("Userdetails.findByActivitylevel");
    query.setParameter("activitylevel", activitylevel);
    return query.getResultList();
    }
    
       //GET method for getting based on stepspermile

    @GET
    @Path("findByStepspermile/{stepspermile}")
    @Produces({"application/json"})
    public List<Userdetails> findByStepspermile(@PathParam("stepspermile") Integer stepspermile) {
    Query query = em.createNamedQuery("Userdetails.findByStepspermile");
    query.setParameter("stepspermile", stepspermile);
    return query.getResultList();
    }
    
       //GET method for getting based on height

    @GET
    @Path("findByHeight/{height}")
    @Produces({"application/json"})
    public List<Userdetails> findByHeight(@PathParam("height") Double height) {
    Query query = em.createNamedQuery("Userdetails.findByHeight");
    query.setParameter("height", height);
    return query.getResultList();
    }
    
       //GET method for getting based on weight

    @GET
    @Path("findByWeight/{weight}")
    @Produces({"application/json"})
    public List<Userdetails> findByWeight(@PathParam("weight") Double weight) {
    Query query = em.createNamedQuery("Userdetails.findByWeight");
    query.setParameter("weight", weight);
    return query.getResultList();
    }
//    
//    //b) You need to create a new REST method that enables querying one table 
//    //using a combination of two attributes (any two attributes excluding the PK).
//    //This should be implemented as a DYNAMIC query. 
//    //You will decide which attributes to consider based on their usefulness in the query.
    //GET data based on Gender and Postcode
//    
    @GET
    @Path("findByGenderANDPostcode/{gender}/{postcode}")
    @Produces({"application/json"})
    public List<Userdetails> findByGenderANDPostcode(@PathParam("gender") String gender,@PathParam("postcode") Integer postcode ) {
    TypedQuery<Userdetails> q = em.createQuery("SELECT u FROM Userdetails u WHERE u.gender = :gender AND u.postcode = :postcode", Userdetails.class);
    q.setParameter("gender", gender);
    q.setParameter("postcode", postcode);

    return q.getResultList();
    }
    

    //You will write a REST method that calculates the calories burned per step for a user. 
    //The method will accept ONE parameter (user id) and will return the calories burned per step for that user.
    
    
    @GET
    @Path("findCalorieBurnedPerStep/{userid}")
    @Produces({"text/plain"})
    
    public  String findCalorieBurnedPerStep(@PathParam("userid") Integer userid) {
        Double caloriesBurnedEachStep=0.0;
        
        TypedQuery<Userdetails> q = em.createQuery("SELECT u FROM Userdetails u WHERE u.userid = :userid", Userdetails.class);  
        q.setParameter("userid", userid);
       
        List<Userdetails> user = q.getResultList();
        for(Userdetails u1: user){
            int stepsPerMile = u1.getStepspermile();
            Double weight = u1.getWeight().doubleValue();
            Double caloriesBurnedPerMile;
            Double wtInLbs = weight *2.2;
            caloriesBurnedPerMile = (0.49) * wtInLbs;

            caloriesBurnedEachStep = caloriesBurnedPerMile/stepsPerMile;  
        }
            
       return String.valueOf(caloriesBurnedEachStep);     
    }
//    
// 
//    //calculate the BMR
    
    @GET
    @Path("findtheBMR/{userid}")
    @Produces({"text/plain"})
    
    public  String findtheBMR(@PathParam("userid") Integer userid) throws ParseException {
        
        Double BMR=0.0;
        Date date = new Date();
        int year = 0;
        int age = 0;
        
        TypedQuery<Userdetails> q = em.createQuery("SELECT u FROM Userdetails u WHERE u.userid = :userid", Userdetails.class);  
        q.setParameter("userid", userid);
      
        List<Userdetails> user = q.getResultList();
        for(Userdetails u1: user){
           int stepsPerMile = u1.getStepspermile();
           Double weight = u1.getWeight().doubleValue();
           Double height = u1.getHeight().doubleValue();
           
           // get dob and calculate the age of the user
            Date dateOfBirth = u1.getDob(); 
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);         
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateOfBirth);
            year = cal.get(Calendar.YEAR);
            age = currentYear - year;

            String gender = u1.getGender();
            if(gender.equalsIgnoreCase("female")){

              BMR= (9.563 * weight) + (1.85  * height) - (4.676  * age) +  655.1 ;

            }
            else if(gender.equalsIgnoreCase("male")){

              BMR= (13.75 * weight) + (5.003 * height) - (6.755 * age) + 66.5;

            }     
        }
            
       return   String.valueOf(BMR);
    }

//    
     //calculate the totalCaloriesBurnedatRestAmount
    
    @GET
    @Path("totalCaloriesBurnedAtRest/{userid}")
    @Produces({"text/plain"})
    public  String totalCaloriesBurnedAtRest(@PathParam("userid") Integer userid) throws ParseException {
        
        int activityLevel = 0;
        Double BMR, totalCalories=0.0;
        
        //get the BMR from the findtheBMR func
        BMR = Double.parseDouble(findtheBMR(userid));
        
        
        TypedQuery<Userdetails> q = em.createQuery("SELECT u FROM Userdetails u WHERE u.userid = :userid", Userdetails.class);  
        q.setParameter("userid", userid);
        
        
        List<Userdetails> user = q.getResultList();
        for(Userdetails u1: user){
           activityLevel = u1.getActivitylevel();
                              
       }
       
           if(activityLevel == 1){
               totalCalories = BMR * 1.2;
           }

           else if(activityLevel == 2){
               totalCalories = BMR *  1.375 ;
           }

           else if(activityLevel == 3){
               totalCalories = BMR *  1.55 ;
           }

           else if(activityLevel == 4){
               totalCalories = BMR * 1.725 ;
           }

           else if(activityLevel == 5){
               totalCalories = BMR *  1.9 ;
           }

       
        
       
        return  String.valueOf(totalCalories);
        

    }
    
    
    

   
//    
}
