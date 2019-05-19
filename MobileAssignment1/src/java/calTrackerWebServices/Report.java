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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pori
 */
@Entity
@Table(name = "REPORT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r")
    , @NamedQuery(name = "Report.findByReportid", query = "SELECT r FROM Report r WHERE r.reportid = :reportid")
    , @NamedQuery(name = "Report.findByTotalcaloriesconsumed", query = "SELECT r FROM Report r WHERE r.totalcaloriesconsumed = :totalcaloriesconsumed")
    , @NamedQuery(name = "Report.findByTotalcaloriesburned", query = "SELECT r FROM Report r WHERE r.totalcaloriesburned = :totalcaloriesburned")
    , @NamedQuery(name = "Report.findByTotalsteps", query = "SELECT r FROM Report r WHERE r.totalsteps = :totalsteps")
    , @NamedQuery(name = "Report.findByCaloriegoal", query = "SELECT r FROM Report r WHERE r.caloriegoal = :caloriegoal")
    , @NamedQuery(name = "Report.findByDate", query = "SELECT r FROM Report r WHERE r.date = :date")
    , @NamedQuery(name = "Report.findByCalorieGoalAndGender", query = "SELECT r FROM Report r WHERE r.caloriegoal = :caloriegoal AND r.userid.gender= :gender")
    , @NamedQuery(name = "Report.findByUserId", query = "SELECT  r FROM Report r WHERE r.userid.userid = :userid")

    , @NamedQuery(name = "Report.findByCaloriegoal", query = "SELECT r FROM Report r WHERE r.caloriegoal = :caloriegoal")})
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "REPORTID")
    private Integer reportid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTALCALORIESCONSUMED")
    private int totalcaloriesconsumed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTALCALORIESBURNED")
    private int totalcaloriesburned;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TOTALSTEPS")
    private int totalsteps;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CALORIEGOAL")
    private int caloriegoal;
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Userdetails userid;

    public Report() {
    }

    public Report(Integer reportid) {
        this.reportid = reportid;
    }

    public Report(Integer reportid, int totalcaloriesconsumed, int totalcaloriesburned, int totalsteps, int caloriegoal) {
        this.reportid = reportid;
        this.totalcaloriesconsumed = totalcaloriesconsumed;
        this.totalcaloriesburned = totalcaloriesburned;
        this.totalsteps = totalsteps;
        this.caloriegoal = caloriegoal;
    }

    public Integer getReportid() {
        return reportid;
    }

    public void setReportid(Integer reportid) {
        this.reportid = reportid;
    }

    public int getTotalcaloriesconsumed() {
        return totalcaloriesconsumed;
    }

    public void setTotalcaloriesconsumed(int totalcaloriesconsumed) {
        this.totalcaloriesconsumed = totalcaloriesconsumed;
    }

    public int getTotalcaloriesburned() {
        return totalcaloriesburned;
    }

    public void setTotalcaloriesburned(int totalcaloriesburned) {
        this.totalcaloriesburned = totalcaloriesburned;
    }

    public int getTotalsteps() {
        return totalsteps;
    }

    public void setTotalsteps(int totalsteps) {
        this.totalsteps = totalsteps;
    }

    public int getCaloriegoal() {
        return caloriegoal;
    }

    public void setCaloriegoal(int caloriegoal) {
        this.caloriegoal = caloriegoal;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        hash += (reportid != null ? reportid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Report)) {
            return false;
        }
        Report other = (Report) object;
        if ((this.reportid == null && other.reportid != null) || (this.reportid != null && !this.reportid.equals(other.reportid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "calTrackerWebServices.Report[ reportid=" + reportid + " ]";
    }
    
}
