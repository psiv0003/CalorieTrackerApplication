/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calTrackerWebServices;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pori
 */
@Entity
@Table(name = "USERCREDENTIAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usercredential.findAll", query = "SELECT u FROM Usercredential u")
    , @NamedQuery(name = "Usercredential.findByUsername", query = "SELECT u FROM Usercredential u WHERE u.username = :username")
    , @NamedQuery(name = "Usercredential.findByPasswordhash", query = "SELECT u FROM Usercredential u WHERE u.passwordhash = :passwordhash")
    , @NamedQuery(name = "Usercredential.findByUserId", query = "SELECT  u FROM Usercredential u WHERE u.userid.userid = :userid")

    , @NamedQuery(name = "Usercredential.findBySignupdate", query = "SELECT u FROM Usercredential u WHERE u.signupdate = :signupdate")})

public class Usercredential implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PASSWORDHASH")
    private String passwordhash;
    @Column(name = "SIGNUPDATE")
    @Temporal(TemporalType.DATE)
    private Date signupdate;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Userdetails userid;

    public Usercredential() {
    }

    public Usercredential(String username) {
        this.username = username;
    }

    public Usercredential(String username, String passwordhash) {
        this.username = username;
        this.passwordhash = passwordhash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public Date getSignupdate() {
        return signupdate;
    }

    public void setSignupdate(Date signupdate) {
        this.signupdate = signupdate;
    }

    public Userdetails getUserid() {
        return userid;
    }

    public void setUserid(Userdetails userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usercredential)) {
            return false;
        }
        Usercredential other = (Usercredential) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "calTrackerWebServices.Usercredential[ username=" + username + " ]";
    }
    
}
