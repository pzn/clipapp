package ip.cl.clipapp.model;

import org.springframework.context.annotation.Profile;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import static ip.cl.clipapp.ClipAppProfile.DATABASE;

@Profile(DATABASE)
@Entity
@Table(name = "clipurl")
@Data
public class ClipUrl implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    private String longUrl;

    public ClipUrl() {

    }

    public ClipUrl(Integer id, String longUrl) {
        this.id = id;
        this.longUrl = longUrl;
    }
}
