package ch.so.agi.avgbs.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "identnd")
public class IdentND {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    @NotEmpty
    private String identnd;
    
    @Column(nullable = false, unique = true)
    @NotEmpty
    private String municipality;
    
    @Column(nullable = false, unique = true)
    @NotEmpty
    private int fosnr;
    
    @Column(nullable = false)
    @NotEmpty
    private Boolean enabled;
  
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentnd() {
        return identnd;
    }

    public void setIdentnd(String identnd) {
        this.identnd = identnd;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public int getFosnr() {
        return fosnr;
    }

    public void setFosnr(int fosnr) {
        this.fosnr = fosnr;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
