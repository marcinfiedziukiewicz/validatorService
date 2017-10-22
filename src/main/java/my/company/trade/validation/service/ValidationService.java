package my.company.trade.validation.service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import my.company.trade.validation.model.ValueTrade;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Validation service responsible for validation input fields from request body object.
 * Validation is done against data model from http://fixer.io/
 */
@Slf4j
@Service
public class ValidationService {

    private final String currentDate = "2016-10-09";
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Value("${customers}")
    private String[] customers;

    /**
     * Method is responsible for validation of files.
     *
     * @param valueTrade input request body object
     * @return List of errors that not meet validation rules.
     */
    public List validateFields(ValueTrade valueTrade, Errors errors) {
        validateDateBaseOnTypeField(valueTrade, errors);
        validateCurrencyField(valueTrade, errors);
        validateDateBaseOnStyleField(valueTrade,errors);

        log.info("Validation error count: " + errors.getErrorCount());
        errors.getAllErrors().stream().forEach(e -> log.error(e.toString()));
        return errors.getAllErrors();
    }

    /**
     * Method responsible for currency code validation against ISO 4217 standard.
     *
     * @param currencyCode currency code as String
     * @return Currency object for valid currency code, null for invalid one.
     */
    public Currency validateCurrencyCode(String currencyCode) {
        try {
            return Currency.getInstance(currencyCode);
        } catch (Exception e) {
            return null;
        }
    }


   private void validateValueDate(String valueDate, String tradeDate, Errors errors) {
        Calendar calendarValueDate = Calendar.getInstance();
        Calendar calendarTradeDate = Calendar.getInstance();
        try {
            calendarValueDate.setTime(dateFormat.parse(valueDate));
        } catch (ParseException e) {
            errors.rejectValue("valueDate","Invalid format of value Date", "Valid format of value date yyyy-MM-dd");
            log.error("Invalid format of value Date");
            e.printStackTrace();
            return;
        }
       try {
           calendarTradeDate.setTime(dateFormat.parse(tradeDate));
       } catch (ParseException e) {
           errors.rejectValue("tradeDate","Invalid format of trade Date", "Valid format of trade date yyyy-MM-dd");
           log.error("Invalid format of trade Date");
           e.printStackTrace();
           return;
       }
        if (calendarValueDate.getTime().before(calendarTradeDate.getTime())) {
            errors.rejectValue("valueDate","Value date " + valueDate + " cannot be Before trade date " + tradeDate, "Value date " + valueDate + " cannot be Before trade date " + tradeDate);
            log.error("Value date " + valueDate + " cannot be Before trade date " + tradeDate);
        }

        if (calendarValueDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                calendarValueDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            errors.rejectValue("valueDate","Value date cannot fall on Weekend","Value date cannot fall on Weekend");
            log.error("Value date cannot fall on Weekend");
        }
    }


    private void validateExpiryAndPremiumDate(String expiryDate,String premiumDate,String deliveryDate, Errors errors){
        Calendar calendarExpiryDate = Calendar.getInstance();
        Calendar calendarPremiumDate = Calendar.getInstance();
        Calendar calendarDeliveryDate = Calendar.getInstance();
        try {
            calendarExpiryDate.setTime(dateFormat.parse(expiryDate));
        } catch (ParseException e) {
            errors.rejectValue("expiryDate","Invalid format of expiry Date", "Valid format of expiry date yyyy-MM-dd");
            log.error("Invalid format of expiry Date");
            e.printStackTrace();
            return;
        }
        try {
            calendarPremiumDate.setTime(dateFormat.parse(premiumDate));
        } catch (ParseException e) {
            errors.rejectValue("premiumDate","Invalid format of premium Date", "Valid format of premium date yyyy-MM-dd");
            log.error("Invalid format of premium  Date");
            e.printStackTrace();
            return;
        }
        try {
            calendarDeliveryDate.setTime(dateFormat.parse(deliveryDate));
        } catch (ParseException e) {
            errors.rejectValue("deliveryDate","Invalid format of delivery Date", "Valid format of delivery date yyyy-MM-dd");
            log.error("Invalid format of delivery Date");
            e.printStackTrace();
            return;
        }

        if (calendarExpiryDate.getTime().after(calendarDeliveryDate.getTime())) {
            errors.rejectValue("expiryDate","Expiry date " + expiryDate + " cannot be After delivery date " + deliveryDate, "Expiry date " + expiryDate + " cannot be After delivery date " + deliveryDate);
            log.error("Expiry  date " + expiryDate + " cannot be After delivery date " + deliveryDate);
        }

        if (calendarPremiumDate.getTime().after(calendarDeliveryDate.getTime())) {
            errors.rejectValue("premiumDate","Premium date " + premiumDate + " cannot be After delivery date " + deliveryDate, "Premium date " + premiumDate + " cannot be After delivery date " + deliveryDate);
            log.error("Premium date " + premiumDate + " cannot be After delivery date " + deliveryDate);
        }
    }

    private void validateExcerciseStartDate(String excerciseStartDate,String expiryDate,String tradeDate, Errors errors){

        Calendar calendarExcerciseStartDate = Calendar.getInstance();
        Calendar calendarExpiryDate = Calendar.getInstance();
        Calendar calendarTradeDate = Calendar.getInstance();

        try {
            calendarExcerciseStartDate.setTime(dateFormat.parse(excerciseStartDate));
        } catch (ParseException e) {
            errors.rejectValue("excerciseStartDate","Invalid format of excercise Start Date", "Valid format of excercise Start Date yyyy-MM-dd");
            log.error("Invalid format of excercise Start Date");
            e.printStackTrace();
            return;
        }

        try {
            calendarExpiryDate.setTime(dateFormat.parse(expiryDate));
        } catch (ParseException e) {
            errors.rejectValue("expiryDate","Invalid format of expiry Date", "Valid format of expiry date yyyy-MM-dd");
            log.error("Invalid format of expiry Date");
            e.printStackTrace();
            return;
        }

        try {
            calendarTradeDate.setTime(dateFormat.parse(tradeDate));
        } catch (ParseException e) {
            errors.rejectValue("tradeDate","Invalid format of trade Date", "Valid format of trade date yyyy-MM-dd");
            log.error("Invalid format of trade Date");
            e.printStackTrace();
            return;
        }

        if (calendarExcerciseStartDate.getTime().before(calendarTradeDate.getTime())) {
            errors.rejectValue("tradeDate","Excercise Start date " + excerciseStartDate + " cannot be Before trade date " + tradeDate, "Excercise Start date " + excerciseStartDate + " cannot be Before trade date " + tradeDate);
            log.error("Excercise Start date " + excerciseStartDate + " cannot be Before trade date " + tradeDate);
        }

        if (calendarExcerciseStartDate.getTime().after(calendarExpiryDate.getTime())) {
            errors.rejectValue("tradeDate","Excercise Start date " + excerciseStartDate + " cannot be After expiry date " + expiryDate, "Excercise Start date " + excerciseStartDate + " cannot be After expiry date " + expiryDate);
            log.error("Excercise Start date " + excerciseStartDate + " cannot be After expiry date " + expiryDate);
        }
    }

    private void validateDateBaseOnStyleField(ValueTrade valueTrade, Errors errors) {
        if ("AMERICAN".equals(valueTrade.getStyle().toUpperCase())) {
            validateExcerciseStartDate(valueTrade.getExcerciseStartDate(),valueTrade.getExpiryDate(),valueTrade.getTradeDate(),errors);
        }
        validateExpiryAndPremiumDate(valueTrade.getExpiryDate(),valueTrade.getPremiumDate(),valueTrade.getDeliveryDate(),errors);
    }

    private void validateDateBaseOnTypeField(ValueTrade valueTrade, Errors errors) {
        if ("spot".equals(valueTrade.getType().toLowerCase()) || "forward".equals(valueTrade.getType().toLowerCase())) {
           validateValueDate(valueTrade.getValueDate(), valueTrade.getTradeDate(), errors);
        }
    }

    private void validateCurrencyField(ValueTrade valueTrade, Errors errors) {
        if (customers != null && valueTrade.getCustomer() != null &&
                Arrays.asList(customers).contains(valueTrade.getCustomer()) && valueTrade.getCcyPair() != null) {

            List<String> currencyList = Lists.newArrayList(Splitter.fixedLength(3).split(valueTrade.getCcyPair()).iterator());
            currencyList.stream().filter(key -> null == validateCurrencyCode(key))
                    .forEach(invalidCurrencyCode ->
                                    errors.rejectValue("ccyPair", "ccyPair field has invalid currency code: " + invalidCurrencyCode, invalidCurrencyCode + " is invalid ISO 4217 currency code")
                    );
        }

    }

    private Locale getLocale(String currencyCode) {
        for (Locale locale : NumberFormat.getAvailableLocales()) {
            String code = NumberFormat.getCurrencyInstance(locale).getCurrency().getCurrencyCode();
            if (currencyCode.equals(code)) {
                return locale;
            }
        }
        return null;
    }
}