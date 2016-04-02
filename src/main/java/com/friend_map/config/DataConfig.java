package com.friend_map.config;


import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource(value = {"classpath:data.properties"})
public class DataConfig {

    @Autowired
    Environment env;

    private static final String DIALECT = "hibernate.dialect";
    private static final String SHOW_SQL = "hibernate.show_sql";
    private static final String HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String POOL_SIZE = "connection.pool_size";
    private static final String USE_REFLECTION_OPTIMIZER = "hibernate.bytecode.use_reflection_optimizer";
    private static final String USE_SECOND_LEVEL_CACHE = "hibernate.cache.use_second_level_cache";
    private static final String CACHE_PROVIDER_CLASS = "hibernate.cache.region.factory_class";
    private static final String EHCACHE_CONFIG = "net.sf.ehcache.configurationResourceName";
    private static final String USE_QUERY_CACHE = "hibernate.cache.use_query_cache";
    private static final String CURRENT_SESSION_CONTEXT_CLASS = "hibernate.current_session_context_class";
    private static final String BATCH_SIZE = "hibernate.jdbc.batch_size";
    private static final String DEFAULT_INDEX_BASE = "hibernate.search.default.indexBase";
    private static final String DEFAULT_DIRECTORY_PROVIDER = "hibernate.search.default.directory_provider";

    @Bean(name = "sessionFactory")
    @Autowired
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setHibernateProperties(properties());
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setPackagesToScan("com.friend_map.persistence_layer.entities");
        sessionFactoryBean.setDataSource(dataSource());
        return sessionFactoryBean;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driver"));
        dataSource.setUrl(env.getRequiredProperty("sql.server.host"));
        dataSource.setUsername(env.getRequiredProperty("sql.server.username"));
        dataSource.setPassword(env.getRequiredProperty("sql.server.password"));
        return dataSource;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    private Properties properties() {
        Properties properties = new Properties();
        properties.put(DIALECT, env.getRequiredProperty(DIALECT));
        properties.put(SHOW_SQL, env.getRequiredProperty(SHOW_SQL));
        properties.put(HBM2DDL_AUTO, env.getRequiredProperty(HBM2DDL_AUTO));
        properties.put(POOL_SIZE, env.getRequiredProperty(POOL_SIZE));
        properties.put(USE_REFLECTION_OPTIMIZER, env.getRequiredProperty(USE_REFLECTION_OPTIMIZER));
        properties.put(USE_SECOND_LEVEL_CACHE, env.getRequiredProperty(USE_SECOND_LEVEL_CACHE));
        properties.put(CACHE_PROVIDER_CLASS, env.getRequiredProperty(CACHE_PROVIDER_CLASS));
        properties.put(EHCACHE_CONFIG, env.getRequiredProperty(EHCACHE_CONFIG));
        properties.put(USE_QUERY_CACHE, env.getRequiredProperty(USE_QUERY_CACHE));
        properties.put(CURRENT_SESSION_CONTEXT_CLASS, env.getRequiredProperty(CURRENT_SESSION_CONTEXT_CLASS));
        properties.put(DEFAULT_INDEX_BASE, env.getRequiredProperty(DEFAULT_INDEX_BASE));
        properties.put(DEFAULT_DIRECTORY_PROVIDER, env.getRequiredProperty(DEFAULT_DIRECTORY_PROVIDER));
        properties.put(BATCH_SIZE, env.getRequiredProperty(BATCH_SIZE));
        return properties;
    }
}
