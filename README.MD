####1 Getting Started
#####1.1 Introduction
&nbsp;&nbsp;&nbsp;&nbsp;Rural是一个基于spring的mvc框架，设计上类似spring mvc，相比于spring mvc，Rural使用更简便，无需配置和注解就可以实现URL到java方法的映射。	取名Rural(乡村风味的，田园的)寓意简洁。<br/>
&nbsp;&nbsp;&nbsp;&nbsp;目前Rural支持json，jsp，freemarker三种视图。
#####1.2 Hello World
&nbsp;&nbsp;&nbsp;&nbsp;首先在web.xml中配置，类似spring mvc，需要配置listener来启动spring context，配置servlet负责请求分发
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
&nbsp;&nbsp;&nbsp;&nbsp;增加spring bean配置如下
```xml
    <bean id="defaultRuralConfig" class="org.rural.config.DefaultRuralConfig"/>
```
&nbsp;&nbsp;&nbsp;&nbsp;然后编写controller
```java
@Controller
public class HelloController {

    public String sayHello(Model model) {
        model.put("msg", "Hello World!");
        return "json";
    }

}
```
&nbsp;&nbsp;&nbsp;&nbsp;访问url ``/contextPath/hello/sayHello``时，method``sayHello``将执行，容器返回如下json字符串
```js
{"msg":"Hello World!"}
```
####2 Config
&nbsp;&nbsp;&nbsp;&nbsp;Rural配置极少，一般只需要配置默认的DefaultRuralConfig即可。若需要更改配置或需要定义拦截器则可自定义配置类，继承自DefaultRuralConfig即可。
```java
@Component
public class RuralConfig extends DefaultRuralConfig {
    @Override
    public void setInterceptorConfig(InterceptorConfigHolder interceptorConfigHolder) {
        super.setInterceptorConfig(interceptorConfigHolder);
        //增加拦截器
        interceptorConfigHolder.add("/user/*", UserInterceptor.class);
    }

    @Override
    public void setContextConfig(ContextConfigHolder contextConfigHolder) {
        super.setContextConfig(contextConfigHolder);
        //重写定义某些配置，这里重定义了模板类型为freemarker
        contextConfigHolder.add(TEMPLATE, "fm");
    }
}
```
&nbsp;&nbsp;&nbsp;&nbsp;DefaultRuralConfig 类中定义了所有Rural支持的配置。
```java
    @Override
    public void setContextConfig(ContextConfigHolder contextConfigHolder) {
	    //请求响应编码类型
        contextConfigHolder.add(CHARSET, "UTF-8");
        //模板类型，目前只支持jsp和fm（freemarker）
        contextConfigHolder.add(TEMPLATE, "fm");
        //页面文件所在位置
        contextConfigHolder.add(PAGE_LOCATION, "/WEB-INF/page/");
        //资源文件所在位置
        contextConfigHolder.add(RESOURCES_LOCATION, "/resources");
        //Controller所在包
        contextConfigHolder.add(CONTROLLER_LOCATION, "");
    }
```
####3 URL Mapping
#####3.1 Default
&nbsp;&nbsp;&nbsp;&nbsp;Rural自动建立URL到方法的映射。当有如下两个Controller类和方法URL映射如下<br/>
```java
org.coder.controller.HelloController	
	public String sayHello(Model model)
	
org.coder.controller.resc.ResouceController
	public  String fineOne(Model model);
```
&nbsp;&nbsp;&nbsp;&nbsp;当没有配置``CONTROLLER_LOCATION``时，默认所有以Controller为后缀的类都是Controller，url路径为类名截去后缀``Controller``，再把首字母小写。<br/>
&nbsp;&nbsp;&nbsp;&nbsp;以上所示的两个方法分别对应URL ``/hello/sayHello``和``/resource/findOne``。
#####3.2 自定义Controller包位置
&nbsp;&nbsp;&nbsp;&nbsp;当自定义Rural Config的CONTROLLER_LOCATION后，必须满足以该包名开头且以Controller为后缀的类才是Controller，且包名会与URL路径对应。
&nbsp;&nbsp;&nbsp;&nbsp;如类结构仍为3.1所示，设置
```java
    @Override
    public void setContextConfig(ContextConfigHolder contextConfigHolder) {
        super.setContextConfig(contextConfigHolder);
        contextConfigHolder.add(CONTROLLER_LOCATION, "org.coder.controller");
    }
```
&nbsp;&nbsp;&nbsp;&nbsp;此时两个URL 为``/hello/sayHello``和``/resc/resource/findOne``。
####4 Metadata Inject
#####4.1 Is what metadata 
&nbsp;&nbsp;&nbsp;&nbsp;Metadata是指能够被Rural自动注入在URL对应的方法参数的数据类型，这包括请求类型和其它一些servlet和rural相关类型。<br/>
&nbsp;&nbsp;&nbsp;&nbsp;如访问url``xxx/findOne?name=myname&age=24``，如下的方法中的参数name,age,model都会被自动注入。
```java
public String findOne(String name, int age, Model model, HttpServletRequest request){
    System.out.println("user findOne ...");
    model.put("name", name);
    model.put("age", age);
    return "forward:user.jsp";
}
```
#####4.2 所有能被注入的类型
&nbsp;&nbsp;&nbsp;&nbsp;能够被Rural注入的请求参数类型包括8种基本数据类及其包装器类型和String类型。
&nbsp;&nbsp;&nbsp;&nbsp;此外还有三个servlet相关类型，ServletContext，HttpServletRequest，HttpServletResponse，HttpSession。注入这四个类型是必须定义成这几个接口类型。
&nbsp;&nbsp;&nbsp;&nbsp;还有比较特殊的类型Model，该类实际上是一个HashMap，用于持有用于页面渲染的数据类型。当模板类型是JSP时，model中的数据会自动转存到request中。
####5 View
&nbsp;&nbsp;&nbsp;&nbsp;Controller中的方法必须返回String，String的内容指明了该此请求使用何种视图。
+ json&nbsp;&nbsp;&nbsp;&nbsp;json视图
+ forward:xxx/xx&nbsp;&nbsp;&nbsp;&nbsp;通过forward形式跳转到页面，目前页面支持freemarker(fm)和jsp
+ redirect:xxx&nbsp;&nbsp;&nbsp;&nbsp;通过redirect跳转到另一个请求

&nbsp;&nbsp;&nbsp;&nbsp;下列情景Rural会返回/WEB-INF/page/user/one.ftl页面渲染的结果。
```java
        contextConfigHolder.add(TEMPLATE, "fm");
        contextConfigHolder.add(PAGE_LOCATION, "/WEB-INF/page/");
```
```java
    public String user(Model model){
        model.put("user", new User("lisi", 18));
        return "forward:user/one";
    }
```
####6 Interceptor
&nbsp;&nbsp;&nbsp;&nbsp;Rural支持自定义拦截器，继承抽象类``Interceptor``。无论``before``还是``after ``方法返回``false``都会使请求链终止。
```java
@Component
public class UserInterceptor extends Interceptor{
    @Override
    public boolean before(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("user before");
        return true;
    }
    @Override
    public boolean after(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("user after");
        return true;
    }
}
```
&nbsp;&nbsp;&nbsp;&nbsp;Rural拦截器的配置需要在代码中完成。拦截器中URL配置使用的是``org.springframework.util.AntPathMatcher``
```java
@Component
public class MyConfig extends DefaultRuralConfig {
    @Override
    public void setInterceptorConfig(InterceptorConfigHolder interceptorConfigHolder) {
        super.setInterceptorConfig(interceptorConfigHolder);
        interceptorConfigHolder.add("/user/*", UserInterceptor.class);
    }
    @Override
    public void setContextConfig(ContextConfigHolder contextConfigHolder) {
        super.setContextConfig(contextConfigHolder);
    }
}
```

