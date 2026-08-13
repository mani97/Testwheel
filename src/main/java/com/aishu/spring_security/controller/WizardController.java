package com.aishu.spring_security.controller;


import com.aishu.spring_security.Repository.WizardRepository;
import com.aishu.spring_security.model.WizardSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WizardController {

    @Autowired
    private WizardRepository wizardRepo;

    @PostMapping("/saveWizard")
    public WizardSetup saveWizard(@RequestBody WizardSetup wizardSetup) {
        return wizardRepo.save(wizardSetup);
    }
}
