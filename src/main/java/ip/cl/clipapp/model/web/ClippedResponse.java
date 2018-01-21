package ip.cl.clipapp.model.web;

import lombok.Data;

@Data
public class ClippedResponse {

    private String value;

    public ClippedResponse(String value) {
        this.value = value;
    }
}
