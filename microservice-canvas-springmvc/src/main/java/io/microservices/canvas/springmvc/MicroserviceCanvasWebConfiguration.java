package io.microservices.canvas.springmvc;

import io.microservices.canvas.extractor.spring.SpringContextExtractorConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

@ComponentScan
@Import(SpringContextExtractorConfiguration.class)
public class MicroserviceCanvasWebConfiguration {

  @Autowired
  private ApplicationContext applicationContext;

  @Bean
  public SpringResourceTemplateResolver templateResolver(){
    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    templateResolver.setPrefix("classpath:/templates");
    templateResolver.setSuffix(".adoc");
    templateResolver.setTemplateMode(TemplateMode.TEXT);
    templateResolver.setCacheable(true);    templateResolver.setApplicationContext(this.applicationContext);
    return templateResolver;
  }

  @Bean
  public SpringTemplateEngine templateEngine(){
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver());
    templateEngine.setEnableSpringELCompiler(true);
    return templateEngine;
  }

  @Bean
  public ThymeleafViewResolver viewResolver(){
    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    viewResolver.setTemplateEngine(templateEngine());
    viewResolver.setOrder(1);
    viewResolver.setViewNames(new String[] {".adoc"});
    return viewResolver;
  }

}
