package com.myapp.azmartialarts.cucumber.stepdefs;

import com.myapp.azmartialarts.AzmartialartsApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = AzmartialartsApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
