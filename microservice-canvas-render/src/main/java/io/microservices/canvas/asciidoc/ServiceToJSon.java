package io.microservices.canvas.asciidoc;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.dataformat.yaml.YAMLMapper;
import tools.jackson.module.kotlin.KotlinModule;
import io.microservices.canvas.model.OperationEndpoint;
import io.microservices.canvas.model.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class ServiceToJSon {

  private static final ObjectMapper jsonMapper = JsonMapper.builder()
          .addMixIn(OperationEndpoint.class, OperationEndpointMixin.class)
          .addModule(new KotlinModule.Builder().build())
          .build();

  private static final ObjectMapper yamlMapper = YAMLMapper.builder()
          .addMixIn(OperationEndpoint.class, OperationEndpointMixin.class)
          .addModule(new KotlinModule.Builder().build())
          .build();

  public static <T> String toJson(T service) {
    return jsonMapper.writeValueAsString(service);
  }

  public static String toYaml(Service service) {
    return yamlMapper.writeValueAsString(service);
  }

  public static <T> T fromYaml(String yaml, Class<T> targetType) {
    return yamlMapper.readValue(yaml, targetType);
  }

  public static <T> T fromJson(String json, Class<T> targetType) {
    return jsonMapper.readValue(json, targetType);
  }

  public static String toAsciidoc(Service service) {
    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setTemplateMode(TemplateMode.TEXT);
    templateResolver.setSuffix(".adoc");

    return processTemplate(service, templateResolver);
  }

  private static String processTemplate(Service service, ClassLoaderTemplateResolver templateResolver) {
    templateResolver.setPrefix("/templates/");
    templateResolver.setCacheable(true);
    TemplateEngine te = new SpringTemplateEngine();
    te.setTemplateResolver(templateResolver);
    Context context = new Context();
    context.setVariable("service", new Canvas(service));
    return te.process("/microservicecanvas/asciiDocCanvas", context);
  }

  public static String toHtml(Service service) {
    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setSuffix(".html");

    return processTemplate(service, templateResolver);
  }

}
