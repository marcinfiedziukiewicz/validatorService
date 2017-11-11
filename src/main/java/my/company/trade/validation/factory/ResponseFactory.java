package my.company.trade.validation.factory;


import my.company.trade.validation.model.Response;
import my.company.trade.validation.model.ResponseStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Factory class responsible to build Response object
 * base on error list provided as argument.
 */
@Service
public class ResponseFactory {

    /**
     * Method return Response object base on error list provided as argument.
     *
     * @param errors Error list
     * @return Response object with Valid|Invalid state.
     */
    public Response getResponse(List errors) {
        if (errors == null || errors.isEmpty()) {
            return Response.builder().status(ResponseStatus.Valid).build();
        } else {
            return Response.builder().status(ResponseStatus.Invalid).errors(errors).build();
        }
    }
}
