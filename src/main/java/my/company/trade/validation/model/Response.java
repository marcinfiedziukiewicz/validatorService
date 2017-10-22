package my.company.trade.validation.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * Response object model.
 */
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response implements Serializable {

   @JsonProperty(required = true, value = "status")
    private ResponseStatus status;
    @JsonProperty(value = "errors")
    private List errors;
}
