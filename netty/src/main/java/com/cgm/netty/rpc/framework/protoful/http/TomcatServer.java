package com.cgm.netty.rpc.framework.protoful.http;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;


/**
 * 依据tomcat对请求数据进行解析
 */
public class TomcatServer {


    public static void start0Tomcat(String hostName, int port) throws LifecycleException {

        //1.构建tomcat对象(遵循JAVAEE规范)
        Tomcat tomcat = new Tomcat();
        Server server = tomcat.getServer();
        Service service = server.findService("Tomcat");


        Connector connector = new Connector();
        connector.setPort(port);

        Engine standardEngine = new StandardEngine();
        standardEngine.setDefaultHost(hostName);

        Host standardHost = new StandardHost();
        standardHost.setName(hostName);


        String contxtPath = "";
        Context context = new StandardContext();
        context.setPath(contxtPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());


        standardHost.addChild(context);
        standardEngine.addChild(standardHost);

        service.setContainer(standardEngine);
        service.addConnector(connector);

        tomcat.addServlet(contxtPath, "dispatcher", new DispatcherServlet());
        context.addServletMappingDecoded("/*", "dispatcher");
        //3.启动tomcat
        tomcat.start();
        tomcat.getServer().await();
    }
}
