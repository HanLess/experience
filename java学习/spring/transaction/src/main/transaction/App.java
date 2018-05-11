package transaction;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service serv = (service)ctx.getBean("service");
        serv.transfer("hanhx","yangnf",200);
    }
}
