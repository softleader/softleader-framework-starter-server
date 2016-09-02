package {pkg}.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebApplicationInitializer
    extends AbstractAnnotationConfigDispatcherServletInitializer {

  @Override
  protected Class<?>[] getRootConfigClasses() {
    return new Class<?>[] {rootConfigClasses};
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    return new Class<?>[] {servletConfigClasses};
  }

  @Override
  protected Filter[] getServletFilters() {
    return new Filter[] {servletFilters};
  }
  
  @Override
  protected String[] getServletMappings() {
    return new String[] {"/"};
  }
  
  @Override
  protected void customizeRegistration(Dynamic registration) {
    super.customizeRegistration(registration);

    String location = ""; // the directory location where files will be stored
    long maxFileSize = -1; // the maximum size allowed for uploaded files
    long maxRequestSize = -1; // the maximum size allowed for multipart/form-data requests
    int fileSizeThreshold = 0; // the size threshold after which files will be written to disk
    registration.setMultipartConfig(
        new MultipartConfigElement(location, maxFileSize, maxRequestSize, fileSizeThreshold));
  }
  
}
