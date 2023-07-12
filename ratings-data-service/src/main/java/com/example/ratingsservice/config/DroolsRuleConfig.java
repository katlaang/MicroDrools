package com.example.ratingsservice.config;

import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsRuleConfig {
    private static final String RATING_RULES_XLS = "drools/rules/rating-rules.drl.xls";
    private static final String USER_RULES_XLS = "drools/rules/user-rules.drl.xls";

    private KieServices kieServices = KieServices.Factory.get();


    private void getKieRepository() {
        final KieRepository kieRepository = kieServices.getRepository();
        kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
    }

    public KieSession getRatingKieSession() {

        getKieRepository();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        kieFileSystem.write(ResourceFactory.newClassPathResource(RATING_RULES_XLS));
        kieFileSystem.write(ResourceFactory.newClassPathResource(USER_RULES_XLS));

        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
        kb.buildAll();
        KieModule kieModule = kb.getKieModule();
        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());

        return kieContainer.newKieSession();
    }
}