package com.example.employeeportal;

import com.flipkart.utils.dwone.commons.annotations.Modules;
import com.flipkart.utils.dwone.dropwizard.AbstractDropwizardApplication;

@Modules({EmployeeModule.class})
// Above Guice module is optional
public class EmployeeApplication extends AbstractDropwizardApplication<EmployeeConfiguration> {

    public EmployeeApplication() {
        /*
         Pass package under which *all* of your classes exist. In this sample application,
         this class is in the top most package. Hence doing getPackage on this class:
         */
        super(EmployeeApplication.class.getPackage());
    }

    /**
     * To run this application, pass these program args:<br>
     * <code>server ./config/config.yml</code>
     */
    public static void main(String[] args) throws Exception {
        new EmployeeApplication().run(args);
    }
}
