/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calTrackerWebServices.service;

import calTrackerWebServices.Consumption;
import java.math.BigDecimal;
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
@Path("caltrackerwebservices.consumption")
public class ConsumptionFacadeREST extends AbstractFacade<Consumption> {

    @PersistenceContext(unitName = "MobileAssignment1PU")
    private EntityManager em;

    public ConsumptionFacadeREST() {
        super(Consumption.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Consumption entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Consumption entity) {
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
    public Consumption find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumption> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Consumption> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
     @GET
    @Path("findByUserId/{userid}")
    @Produces({"application/json"})
    public List<Consumption> findByUserId(@PathParam("userid") Integer userid) {
    Query query = em.createNamedQuery("Consumption.findByUserId");
    query.setParameter("userid", userid);
    return query.getResultList();
    }

    
    //GET method for getting based on date

    @GET
    @Path("findByDate/{date}")
    @Produces({"application/json"})
    public List<Consumption> findByDate(@PathParam("date") String date) throws ParseException  {
    
    Date convertedDate = new Date();
    convertedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
    Query query = em.createNamedQuery("Consumption.findByDate");

    query.setParameter("date", convertedDate);
    
    return query.getResultList();
    }

    
    //GET method for getting based foodid

    @GET
    @Path("findByFoodId/{foodid}")
    @Produces({"application/json"})
    public List<Consumption> findByFoodId(@PathParam("foodid") Integer foodid) {
    Query query = em.createNamedQuery("Consumption.findByFoodId");
    query.setParameter("foodid", foodid);
    return query.getResultList();
    }
    
    //GET method for getting based foodid

    @GET
    @Path("findByQtyamount/{qtyamount}")
    @Produces({"application/json"})
    public List<Consumption> findByQtyamount(@PathParam("qtyamount") Double qtyamount) {
        BigDecimal qty = new BigDecimal(0);
        qty = BigDecimal.valueOf(qtyamount);
    Query query = em.createNamedQuery("Consumption.findByQtyamount");
    query.setParameter("qtyamount", qty);
    return query.getResultList();
    }
    
     //return the total calories consumed
    
    @GET
    @Path("findTotalCaloriesForDay/{userid}/{date}")
   @Produces({"text/plain"})
    public String findTotalCaloriesForDay(@PathParam("userid") Integer userid, @PathParam("date") String date ) throws ParseException {
        
        BigDecimal toatlCaloriesConsumed = new BigDecimal(0);
        BigDecimal caloriesConsumed ;
        BigDecimal calorieForFoodItem ;
        BigDecimal qty ;
        
        Date convertedDate = new Date();
        convertedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
    
        TypedQuery<Consumption> q = em.createQuery("SELECT c FROM Consumption c WHERE c.userid.userid = :userid AND c.date = :date ", Consumption.class);  
        q.setParameter("userid", userid);
        q.setParameter("date", convertedDate);
        
        List<Consumption> consumption = q.getResultList();
        for(Consumption c1: consumption){
           calorieForFoodItem = c1.getFoodid().getCalorieamount();
           qty = c1.getQtyamount();
           
           caloriesConsumed = calorieForFoodItem.multiply(qty);
           toatlCaloriesConsumed = toatlCaloriesConsumed.add(caloriesConsumed);
        }

    return String.valueOf(toatlCaloriesConsumed) ;
    }
}
