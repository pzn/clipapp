package ip.cl.clipapp.model.web;

import lombok.Data;

@Data
public class UnclippedResponse {

    private String url;

    public UnclippedResponse(String url) {
        this.url = url;
    }
}
