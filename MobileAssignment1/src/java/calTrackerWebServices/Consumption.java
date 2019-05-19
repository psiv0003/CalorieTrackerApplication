/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calTrackerWebServices;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "CONSUMPTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consumption.findAll", query = "SELECT c FROM Consumption c")
    , @NamedQuery(name = "Consumption.findByConsumptionid", query = "SELECT c FROM Consumption c WHERE c.consumptionid = :consumptionid")
    , @NamedQuery(name = "Consumption.findByQtyamount", query = "SELECT c FROM Consumption c WHERE c.qtyamount = :qtyamount")
    , @NamedQuery(name = "Consumption.findByDate", query = "SELECT c FROM Consumption c WHERE c.date = :date")
    , @NamedQuery(name = "Consumption.findByUserId", query = "SELECT c FROM Consumption c WHERE c.userid.userid = :userid")
    , @NamedQuery(name = "Consumption.findByFoodId", query = "SELECT c FROM Consumption c WHERE c.foodid.foodid = :foodid")

})
public class Consumption implements Serializable {

    private static final long serialVersionUID = 1L; 
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CONSUMPTIONID")
    private Integer consumptionid;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "QTYAMOUNT")
    private BigDecimal qtyamount;
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn(name = "FOODID", referencedColumnName = "FOODID")
    @ManyToOne(optional = false)
    private Food foodid;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Userdetails userid;

    public Consumption() {
    }

    public Consumption(Integer consumptionid) {
        this.consumptionid = consumptionid;
    }

    public Consumption(Integer consumptionid, BigDecimal qtyamount) {
        this.consumptionid = consumptionid;
        this.qtyamount = qtyamount;
    }

    public Integer getConsumptionid() {
        return consumptionid;
    }

    public void setConsumptionid(Integer consumptionid) {
        this.consumptionid = consumptionid;
    }

    public BigDecimal getQtyamount() {
        return qtyamount;
    }

    public void setQtyamount(BigDecimal qtyamount) {
        this.qtyamount = qtyamount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Food getFoodid() {
        return foodid;
    }

    public void setFoodid(Food foodid) {
        this.foodid = foodid;
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
        hash += (consumptionid != null ? consumptionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consumption)) {
            return false;
        }
        Consumption other = (Consumption) object;
        if ((this.consumptionid == null && other.consumptionid != null) || (this.consumptionid != null && !this.consumptionid.equals(other.consumptionid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "calTrackerWebServices.Consumption[ consumptionid=" + consumptionid + " ]";
    }
    
}
