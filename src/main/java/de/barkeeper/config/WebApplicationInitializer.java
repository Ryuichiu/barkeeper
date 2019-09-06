package de.barkeeper.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * WebApplicationInitializer to register DispatcherServlet and use Java-based Spring configuration.
 */
@Configuration
public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Create the root application context to be provided to the ContextLoaderListener. The returned context is
     *      delegated to 'ContextLoaderListener.ContextLoaderListener(WebApplicationContext)' and will be established
     *      as the parent context for any DispatcherServlet application contexts. As such, it typically contains middle-tier
     *      services, data source, etc.
     *
     * Returns the configuration for the root application context, or null if creation and registration of a root
     *      context is not desired.
     *
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {DataSourceConfig.class, MailConfig.class, WebSecurityConfig.class};
    }

    /**
     * Create a servlet context is delegated to 'DispatcherServlet.DispatcherServlet(WebApplicationContext)'. As such,
     *      it typically contains controllers, view resolvers, and other web-related beans.
     *
     * Returns the configuration for the Servlet application context, or null if all configuration is specified through
     *      root config classes.
     *
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {WebMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    /**
     * Returns mapping that are used during servlet registration. Override the method if it is need custom filters,
     *      because default implementation returns just empty array.
     *
     * @return
     */
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return new Filter[] {filter};
    }
}
