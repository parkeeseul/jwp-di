package next.config;

import core.di.context.ApplicationContext;
import core.di.context.support.AnnotationConfigApplicationContext;
import core.mvc.DispatcherServlet;
import core.mvc.asis.ControllerHandlerAdapter;
import core.mvc.asis.RequestMapping;
import core.mvc.tobe.AnnotationHandlerMapping;
import core.mvc.tobe.HandlerExecutionHandlerAdapter;
import core.web.WebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class MyWebApplicationInitializer implements WebApplicationInitializer {
    private static final Logger log = LoggerFactory.getLogger(MyWebApplicationInitializer.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        ApplicationContext ac = new AnnotationConfigApplicationContext(MyConfiguration.class);
        AnnotationHandlerMapping ahm = new AnnotationHandlerMapping(ac);
        ahm.initialize();

        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.addHandlerMapping(ahm);
        dispatcherServlet.addHandlerMapping(new RequestMapping());
        dispatcherServlet.addHandlerAdapter(new HandlerExecutionHandlerAdapter());
        dispatcherServlet.addHandlerAdapter(new ControllerHandlerAdapter());

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        log.info("Start MyWebApplication Initializer");
    }
}
