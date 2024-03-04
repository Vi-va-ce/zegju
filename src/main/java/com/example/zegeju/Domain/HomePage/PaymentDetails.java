package com.example.zegeju.Domain.HomePage;

import java.util.HashMap;
import lombok.Setter;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

@Setter
@Getter
@Repository
public class PaymentDetails {
    private boolean trialPackage;
    private boolean basicPackage;
    private boolean premiumPackage;
}
