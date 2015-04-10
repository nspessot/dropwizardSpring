/************************************************************************
 * FileName - ExpenseService.java
 * 
 * $Revision: 12 $
 * $Author: Nicolas $
 * $Date: Apr 9, 2015 $
 ************************************************************************/
package com.toptal.expense;

import io.dropwizard.Application;
import io.dropwizard.servlets.tasks.Task;
import io.dropwizard.setup.Environment;

import java.util.Map;

import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.codahale.metrics.health.HealthCheck;

public class ExpenseBackendApp extends Application<ExpenseBackendConfiguration> {

    public static void main(String[] args) throws Exception {
        new ExpenseBackendApp().run(args);
    }
    
    @Override
    public void run(ExpenseBackendConfiguration configuration, Environment environment)
            throws Exception {

      //init Spring context
        //before we init the app context, we have to create a parent context with all the config objects others rely on to get initialized
        AnnotationConfigWebApplicationContext parent = new AnnotationConfigWebApplicationContext();
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();

        parent.refresh();
        parent.getBeanFactory().registerSingleton("configuration",configuration);
        parent.registerShutdownHook();
        parent.start();

        //the real main app context has a link to the parent context
        ctx.setParent(parent);
        ctx.register(MyAppSpringConfiguration.class, MongoConfig.class);
        ctx.refresh();
        ctx.registerShutdownHook();
        ctx.start();

        //now that Spring is started, let's get all the beans that matter into DropWizard

        //health checks
        Map<String, HealthCheck> healthChecks = ctx.getBeansOfType(HealthCheck.class);
        for(Map.Entry<String,HealthCheck> entry : healthChecks.entrySet()) {
            environment.healthChecks().register(entry.getKey(), entry.getValue());
            
        }

        //resources
        Map<String, Object> resources = ctx.getBeansWithAnnotation(Path.class);
        for(Map.Entry<String,Object> entry : resources.entrySet()) {
            environment.jersey().register(entry.getValue());
        }

        //tasks
        Map<String, Task> tasks = ctx.getBeansOfType(Task.class);
        for(Map.Entry<String,Task> entry : tasks.entrySet()) {
            environment.admin().addTask(entry.getValue());
        }

        //JAX-RS providers
        Map<String, Object> providers = ctx.getBeansWithAnnotation(Provider.class);
        for(Map.Entry<String,Object> entry : providers.entrySet()) {
            environment.jersey().register(entry.getValue());
        }

        //last, but not least, let's link Spring to the embedded Jetty in Dropwizard
        environment.servlets().addServletListeners(new SpringContextLoaderListener(ctx));

        //activate Spring Security filter
//        environment.addFilter(DelegatingFilterProxy.class,"/*").setName("springSecurityFilterChain");
    }

}
