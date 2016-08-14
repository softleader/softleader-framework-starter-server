package tw.com.softleader.starter.server.io;

import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import tw.com.softleader.starter.server.pojo.Starter;

@RequiredArgsConstructor
public class WebApplicationInitializer implements UnaryOperator<String> {

  private final Starter starter;

  @Override
  public String apply(String source) {
    String roots =
        starter.getProject().getRootConfigs().stream().collect(Collectors.joining(", ", "{", "}"));
    source = source.replace("{rootConfigClasses}", roots);
    String servlets = starter.getProject().getServletConfigs().stream()
        .collect(Collectors.joining(", ", "{", "}"));
    source = source.replace("{servletConfigClasses}", servlets);
    String filters = starter.getProject().getServletFilters().stream()
        .collect(Collectors.joining(", ", "{", "}"));
    source = source.replace("{servletFilters}", filters);
    return source;
  }

}
