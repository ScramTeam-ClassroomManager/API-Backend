package it.unical.classroommanager_api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@PropertySource(value = {"classpath:application.properties"})
@Component
public class DataSourceInitializer {

    @Autowired
    private DataSource dataSource;

    @Value("${sql.source}")
    private String dataSQLPath;

    @Value("${sql.insert}")
    private String isInsert;

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        if(isInsert.equals("true")) {
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource(dataSQLPath));
            resourceDatabasePopulator.execute(dataSource);
        }
    }



}