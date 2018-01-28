package ip.cl.clipapp.model.entity;

import org.springframework.context.annotation.Profile;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import static ip.cl.clipapp.ClipAppProfile.DATABASE;
import static javax.persistence.GenerationType.IDENTITY;

@Profile(DATABASE)
@Entity
@Table(name = "clipurl")
@Data
public class ClipUrl implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private String longUrl;

    public ClipUrl() {

    }

    public ClipUrl(Integer id, String longUrl) {
        this.id = id;
        this.longUrl = longUrl;
    }
}
