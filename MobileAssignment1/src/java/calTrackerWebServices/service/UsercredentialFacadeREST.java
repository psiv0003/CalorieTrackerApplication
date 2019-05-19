/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calTrackerWebServices.service;

import calTrackerWebServices.Usercredential;
import calTrackerWebServices.Userdetails;
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
@Path("caltrackerwebservices.usercredential")
public class UsercredentialFacadeREST extends AbstractFacade<Usercredential> {

    @PersistenceContext(unitName = "MobileAssignment1PU")
    private EntityManager em;

    public UsercredentialFacadeREST() {
        super(Usercredential.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Usercredential entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Usercredential entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Usercredential find(@PathParam("id") String id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usercredential> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usercredential> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
     //GET method for getting based on password hash

     @GET
    @Path("findByPasswordhash/{passwordhash}")
    @Produces({"application/json"})
    public List<Usercredential> findByPasswordhash(@PathParam("passwordhash") String passwordhash) {
    Query query = em.createNamedQuery("Usercredential.findByPasswordhash");
    query.setParameter("passwordhash", passwordhash);
    return query.getResultList();
    }
    
    
     
      //GET method for getting based on signup date

     @GET
    @Path("findBySignupdate/{signupdate}")
    @Produces({"application/json"})
    public List<Usercredential> findBySignupdate(@PathParam("signupdate") String signupdate) throws ParseException {
    Query query = em.createNamedQuery("Usercredential.findBySignupdate");
     Date convertedDate = new Date();
    convertedDate = new SimpleDateFormat("yyyy-MM-dd").parse(signupdate);
    query.setParameter("signupdate", convertedDate);
    return query.getResultList();
    }
    
     
      //GET method for getting based on signup date

     @GET
    @Path("findByUserId/{userid}")
    @Produces({"application/json"})
    public List<Usercredential> findByUserId(@PathParam("userid") Integer userid) {
    Query query = em.createNamedQuery("Usercredential.findByUserId");
    query.setParameter("userid", userid);
    return query.getResultList();
    }
    
    //find by username
      @GET
    @Path("findByUsername/{username}")
    @Produces({"application/json"})
    public List<Usercredential> findByUserName(@PathParam("username") String username) {
    Query query = em.createNamedQuery("Usercredential.findByUsername");
    query.setParameter("username", username);
    return query.getResultList();
    }
    
 //login method
    
    @GET
    @Path("userlogin/{username}/{passwordhash}")
    @Produces({"application/json"})
    public List<Usercredential> userLogin(@PathParam("username") String username, @PathParam("passwordhash") String passwordhash ) throws ParseException {
    TypedQuery<Usercredential> q = em.createQuery("SELECT u FROM Usercredential u WHERE u.username = :username AND u.passwordhash = :passwordhash", Usercredential.class);
    q.setParameter("username", username);
    q.setParameter("passwordhash", passwordhash);

    //q.getResultList();
   
    return q.getResultList();
    
    }
    
}
