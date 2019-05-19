/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calTrackerWebServices;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Pori
 */
@Entity
@Table(name = "USERDETAILS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userdetails.findAll", query = "SELECT u FROM Userdetails u")
    , @NamedQuery(name = "Userdetails.findByUserid", query = "SELECT u FROM Userdetails u WHERE u.userid = :userid")
    , @NamedQuery(name = "Userdetails.findByName", query = "SELECT u FROM Userdetails u WHERE u.name = :name")
    , @NamedQuery(name = "Userdetails.findBySurname", query = "SELECT u FROM Userdetails u WHERE u.surname = :surname")
    , @NamedQuery(name = "Userdetails.findByEmail", query = "SELECT u FROM Userdetails u WHERE u.email = :email")
    , @NamedQuery(name = "Userdetails.findByGender", query = "SELECT u FROM Userdetails u WHERE u.gender = :gender")
    , @NamedQuery(name = "Userdetails.findByAddress", query = "SELECT u FROM Userdetails u WHERE u.address = :address")
    , @NamedQuery(name = "Userdetails.findByPostcode", query = "SELECT u FROM Userdetails u WHERE u.postcode = :postcode")
    , @NamedQuery(name = "Userdetails.findByActivitylevel", query = "SELECT u FROM Userdetails u WHERE u.activitylevel = :activitylevel")
    , @NamedQuery(name = "Userdetails.findByDob", query = "SELECT u FROM Userdetails u WHERE u.dob = :dob")
    , @NamedQuery(name = "Userdetails.findByHeight", query = "SELECT u FROM Userdetails u WHERE u.height = :height")
    , @NamedQuery(name = "Userdetails.findByWeight", query = "SELECT u FROM Userdetails u WHERE u.weight = :weight")
    , @NamedQuery(name = "Userdetails.findByStepspermile", query = "SELECT u FROM Userdetails u WHERE u.stepspermile = :stepspermile")})
public class Userdetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "USERID")
    private Integer userid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "SURNAME")
    private String surname;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "GENDER")
    private String gender;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "ADDRESS")
    private String address;
    @Basic(optional = false)
    @NotNull
    @Column(name = "POSTCODE")
    private int postcode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACTIVITYLEVEL")
    private int activitylevel;
    @Column(name = "DOB")
    @Temporal(TemporalType.DATE)
    private Date dob;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "HEIGHT")
    private BigDecimal height;
    @Column(name = "WEIGHT")
    private BigDecimal weight;
    @Column(name = "STEPSPERMILE")
    private Integer stepspermile;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private Collection<Report> reportCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private Collection<Usercredential> usercredentialCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userid")
    private Collection<Consumption> consumptionCollection;

    public Userdetails() {
    }

    public Userdetails(Integer userid) {
        this.userid = userid;
    }

    public Userdetails(Integer userid, String name, String surname, String email, String gender, String address, int postcode, int activitylevel) {
        this.userid = userid;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.postcode = postcode;
        this.activitylevel = activitylevel;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public int getActivitylevel() {
        return activitylevel;
    }

    public void setActivitylevel(int activitylevel) {
        this.activitylevel = activitylevel;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getStepspermile() {
        return stepspermile;
    }

    public void setStepspermile(Integer stepspermile) {
        this.stepspermile = stepspermile;
    }

    @XmlTransient
    public Collection<Report> getReportCollection() {
        return reportCollection;
    }

    public void setReportCollection(Collection<Report> reportCollection) {
        this.reportCollection = reportCollection;
    }

    @XmlTransient
    public Collection<Usercredential> getUsercredentialCollection() {
        return usercredentialCollection;
    }

    public void setUsercredentialCollection(Collection<Usercredential> usercredentialCollection) {
        this.usercredentialCollection = usercredentialCollection;
    }

    @XmlTransient
    public Collection<Consumption> getConsumptionCollection() {
        return consumptionCollection;
    }

    public void setConsumptionCollection(Collection<Consumption> consumptionCollection) {
        this.consumptionCollection = consumptionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userdetails)) {
            return false;
        }
        Userdetails other = (Userdetails) object;
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "calTrackerWebServices.Userdetails[ userid=" + userid + " ]";
    }
    
}
