package com.friend_map.config;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class SitemeshFilterConfig extends ConfigurableSiteMeshFilter {

    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder.addDecoratorPath("/*", "/layouts/decorator.jsp");
        builder.addDecoratorPath("/auth", "/layouts/auth_decorator.jsp");
        builder.addDecoratorPath("/register", "/layouts/auth_decorator.jsp");
    }
}
