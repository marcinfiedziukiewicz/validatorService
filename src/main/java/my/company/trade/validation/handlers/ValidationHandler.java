package my.company.trade.validation.handlers;

import my.company.trade.validation.factory.ResponseFactory;
import my.company.trade.validation.model.Response;
import my.company.trade.validation.model.ValueRates;
import my.company.trade.validation.model.ValueTrade;
import my.company.trade.validation.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Main handler class for Validation service with one endpoint /validate.
 */
@RestController
public class ValidationHandler {

    @Autowired
    private ValidationService validationService;
    @Autowired
    private ResponseFactory responseFactory;

    /**
     * Endpoint responsible to validate RequestBody.
     * RequestBody is in JSON format of ValueRates model object.
     *
     * @param valueTrade RequestBody JSON as ValueRates model object
     *
     * @return Return Response object with information if input is valid|invalid.
     */
/*    @RequestMapping(value = "/validate1", method = POST, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response validate1(@RequestBody ValueRates valueRates) {
        return responseFactory.getResponse(validationService.validateFields(valueRates, new BeanPropertyBindingResult(valueRates, "valueRates")));
    }*/

    @RequestMapping(value = "/validate", method = POST, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public Response validate(@Valid @RequestBody ValueTrade valueTrade) {
        return responseFactory.getResponse(validationService.validateFields(valueTrade,  new BeanPropertyBindingResult(valueTrade,"valueTrade")));
    }
}
