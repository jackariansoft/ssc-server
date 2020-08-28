/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.ariannamondo.mag.config;

import it.ariannamondo.mag.MagServiceInitializer;
import static it.ariannamondo.mag.MagServiceInitializer.DATA_SOURCE;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "it.ariannamondo.mag", 
    entityManagerFactoryRef = "magEntityManager", 
    transactionManagerRef = "magTransactionManager"
)
public class EclipseLinkJpaConfiguration  {

//    public EclipseLinkJpaConfiguration(DataSource dataSource, JpaProperties properties, ObjectProvider<JtaTransactionManager> jtaTransactionManager) {
//        super(dataSource, properties, jtaTransactionManager);
//    }
    //public LocalContainerEntityManagerFactoryBean entityManagerFactory(final EntityManagerFactoryBuilder builder) {
    @Primary
    @Bean    
    public LocalContainerEntityManagerFactoryBean magEntityManager() {
              
        LocalContainerEntityManagerFactoryBean etm = null;
        etm=  new LocalContainerEntityManagerFactoryBean();
        try {
            etm.setPersistenceProviderClass(org.eclipse.persistence.jpa.PersistenceProvider.class);
            etm.setDataSource(magDataSource());
            etm.setPackagesToScan(new String[]{MagServiceInitializer.PACKAGE_MODEL});
            //etm.setPersistenceUnitName(MagServiceInitializer.PERSISTENCE_UNIT);
            etm.setJpaPropertyMap(initJpaProperties());
            
//              ret = builder
//                    .dataSource(magDataSource())
//                    .packages("it.ariannamondo.mag.entity")
//                    .persistenceUnit("mag-beckend")                   
//                    .jta(true)
//                    .properties(initJpaProperties()).build();
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(EclipseLinkJpaConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(EclipseLinkJpaConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return etm;
    }

    /**
     *
     * @return
     * @throws IllegalArgumentException
     * @throws NamingException
     */
    @Primary
    @Bean(name = "dataSource")
    public DataSource magDataSource() throws IllegalArgumentException,
            NamingException {
        
         DataSource dataSource = null;
        JndiTemplate jndi = new JndiTemplate();
        try {
            dataSource = jndi.lookup(DATA_SOURCE, DataSource.class);
        } catch (NamingException e) {
          throw e;
        }
        return dataSource;
//        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();           // create JNDI data source
//        bean.setJndiName(DATA_SOURCE);  // spring.datasource.jndi-name=java:/comp/env/java:/PostgresDS        
//        bean.setProxyInterface(javax.sql.DataSource.class);
//        bean.setLookupOnStartup(true);
//        bean.afterPropertiesSet();
//        return  (DataSource) bean.getObject();
    }

    @Bean
    public JpaTransactionManager magTransactionManager(EntityManagerFactory emf) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
    @Primary
    @Bean(name = "userTransactionManager",autowireCandidate = true)
    public JpaTransactionManager userTransactionManager() {
  
        JpaTransactionManager transactionManager
          = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                magEntityManager().getObject());
        return transactionManager;
    }

    //@Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        return new EclipseLinkJpaVendorAdapter();
    }

    //@Override
    protected Map<String, Object> getVendorProperties() {
        final Map<String, Object> map = new HashMap<>();
        map.put(PersistenceUnitProperties.WEAVING, "false");
        return map;
    }

    private Map<String, ?> initJpaProperties() {
        final Map<String, Object> ret = new HashMap<>();
        // Add any JpaProperty you are interested in and is supported by your Database and JPA implementation
        ret.put("eclipselink.jdbc.batch-writing", "JDBC");
        ret.put("eclipselink.jdbc.batch-writing.size", "500");
        ret.put("eclipselink.persistence-context.flush-mode", "commit");
        ret.put("eclipselink.weaving", "false");

        return ret;
    }

   

}
