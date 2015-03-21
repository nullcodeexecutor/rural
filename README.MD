####1 Getting Started
#####1.1 Introduction
&nbsp;&nbsp;&nbsp;&nbsp;Rural是一个基于spring的mvc框架，设计上类似spring mvc，相比于spring mvc，Rural使用更简便，无需配置和注解就可以实现URL到java方法的映射。	取名Rural(乡村风味的，田园的)寓意简洁。
&nbsp;&nbsp;&nbsp;&nbsp;目前Rural支持json，jsp，freemarker三种视图。
#####1.2 Hello World
&nbsp;&nbsp;&nbsp;&nbsp;首先在web.xml中配置，类似spring mvc，需要配置
```xml
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:spring/*.xml</param-value>
    </context-param>
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    
    <servlet>
        <servlet-name>rural</servlet-name>
        <servlet-class>org.rural.context.DispatcherServlet</servlet-class>
        <load-on-startup>1</load-on-startup>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>rural</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```
