package bill.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import bill.example.service.ImportantBusinessService;
import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 *
 */
@Slf4j
@SpringBootApplication
public class AnnotationDemo
{
    public static void main ( String [ ] argv ) throws Throwable
    {
		ConfigurableApplicationContext app = SpringApplication.run ( AnnotationDemo.class ) ;

		ImportantBusinessService svc = app.getBean ( ImportantBusinessService.class ) ;
		String result = svc.goForthAndDoThus ( ) ;
		log.info ( "Result {}", result ) ;
	}
}
