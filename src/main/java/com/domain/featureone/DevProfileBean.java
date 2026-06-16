package com.domain.featureone;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")   // Spring boot loads this Bean only when the Profile is dev
@Component
public class DevProfileBean {

    public String getProfile() {
        return "Dev profile is Activated";
    }
}
