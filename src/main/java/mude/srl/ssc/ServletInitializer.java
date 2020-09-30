package mude.srl.ssc;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan(basePackages = "mude.srl.ssc")
public class ServletInitializer extends SpringBootServletInitializer {

    public static final String DATA_SOURCE = "java:/PostgresDS";
    //public static final String DATA_SOURCE = "jdbc:/ssc-backend";
    //public static final String PERSISTENCE_UNIT = "mag-beckend";
    public static final String PACKAGE_MODEL = "mude.srl.ssc.entity";

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SscApplication.class);
    }

}
