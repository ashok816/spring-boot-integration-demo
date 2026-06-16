package com.domain.featureone;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("prod")   // Spring boot loads this Bean only when the Profile is prod
@Component
public class ProdProfileBean {

    public String getProfile() {
        return "Prod profile is Activated";
    }
}
