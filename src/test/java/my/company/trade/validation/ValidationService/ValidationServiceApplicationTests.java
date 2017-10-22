package my.company.trade.validation.ValidationService;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.company.trade.validation.model.ValueRates;
import my.company.trade.validation.model.ValueTrade;
import my.company.trade.validation.service.ValidationService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValidationServiceApplicationTests {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static ValueTrade valid;
    private static ValueTrade invalid;


    @Autowired
    private ValidationService validationService;

    @BeforeClass
    public static void init(){
        try {
            valid = objectMapper.readValue(new File("src/test/resources/valid.json"), ValueTrade.class);
            invalid = objectMapper.readValue(new File("src/test/resources/invalid.json"), ValueTrade.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Test
	public void validInputTest() {
        assertTrue(validationService.validateFields(valid, new BeanPropertyBindingResult(valid, "valid")).isEmpty());
	}


    @Test
    public void invalidInputTest() {
        assertTrue(!validationService.validateFields(invalid, new BeanPropertyBindingResult(invalid, "invalid")).isEmpty());
    }

}
