package com.example.zegeju.Domain.HomePage;
import lombok.Setter;
import lombok.Getter;
import org.springframework.stereotype.Repository;

@Setter
@Getter
@Repository
public class ButtonStatus {
    private String buttonStatusText="buttonStatus";

    final  String SEEPLANS="See plans";
    final  String START="Start";
    final  String UPDATEPACKAGE="Update Package";

    final  String STATUSTEXT="Status_Text";

}
