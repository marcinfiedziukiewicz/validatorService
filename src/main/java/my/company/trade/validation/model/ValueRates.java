package my.company.trade.validation.model;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Request Body object model base on documentation from http://fixer.io/
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValueRates implements Serializable {

    @JsonProperty(required = true, value = "base")
    private String base;
    @JsonProperty(required = true, value = "date")
    private String date;
    @JsonProperty(required = true, value = "rates")
    private Map<String, Object> rates = new HashMap<String, Object>();

}
