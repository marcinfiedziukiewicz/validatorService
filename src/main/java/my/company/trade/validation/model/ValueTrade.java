package my.company.trade.validation.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValueTrade {

    private String customer;
    private String ccyPair;
    private String type;
    private String direction;
    private String tradeDate;
    private Double amount1;
    private Double amount2;
    private Double rate;
    private String valueDate;
    private String legalEntity;
    private String trader;
    private String style;
    private String strategy;
    private String deliveryDate;
    private String expiryDate;
    private String payCcy;
    private Double premium;
    private String premiumCcy;
    private String premiumType;
    private String premiumDate;
    private String excerciseStartDate;

}