package it.ariannamondo.mag;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = " it.ariannamondo.mag")
public class MagServiceInitializer extends SpringBootServletInitializer {

    public static final String DATA_SOURCE  = "java/PostgresDS";
    public static final String PERSISTENCE_UNIT="mag-beckend";
    public static final String PACKAGE_MODEL  = "it.ariannamondo.mag.entity";
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MagServiceInitializer.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MagServiceInitializer.class, args);
    }

    

}
