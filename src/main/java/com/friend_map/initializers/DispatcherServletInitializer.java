package com.friend_map.initializers;

import com.friend_map.config.SitemeshFilterConfig;
import com.friend_map.config.WebConfig;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import java.io.InputStream;
import java.util.Properties;


public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    InputStream inputStream;
    Properties property;
    ClassLoader loader;

    long MAX_FILE_SIZE; // 5MB
    long MAX_REQUEST_SIZE; // 20MB
    int FILE_SIZE_THRESHOLD; // 0
    String LOCATION;

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {WebConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        SitemeshFilterConfig sitemeshFilterConfig = new SitemeshFilterConfig();
        OpenSessionInViewFilter openSessionInViewFilter = new OpenSessionInViewFilter();
        openSessionInViewFilter.setSessionFactoryBeanName("sessionFactory");
        return new Filter[]{ characterEncodingFilter, sitemeshFilterConfig, openSessionInViewFilter};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(getMultipartConfigElement());
        registration.setAsyncSupported(isAsyncSupported());
    }


    private MultipartConfigElement getMultipartConfigElement() {
        propertyInitializer();
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
                LOCATION,
                MAX_FILE_SIZE,
                MAX_REQUEST_SIZE,
                FILE_SIZE_THRESHOLD);
        return multipartConfigElement;
    }

    /** ИНИЦИАЛИЗИРЕУТ ПЕРЕМЕННЫХ ДЛЯ НАСТРОЙКИ */
    public void propertyInitializer() {
        try {
            property = new Properties();
            loader = Thread.currentThread().getContextClassLoader();
            inputStream = loader.getResourceAsStream("/app.properties");
            property.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MAX_FILE_SIZE = Long.parseLong(property.getProperty("max.file.size"));
        LOCATION = property.getProperty("image.directory");
        MAX_REQUEST_SIZE = Long.parseLong(property.getProperty("max.request.size"));
        FILE_SIZE_THRESHOLD = Integer.parseInt(property.getProperty("file.size.threshold"));
    }
}