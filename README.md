学习笔记springboot2  
===
**配置优先级顺序进行加载 ：**  
  • 命令行参数   
  • 来自 java:comp/env 的JNDI属性  
  • Java 系统属性（ System.getProperties())  
  • 操作系统环境变量   
  • RandomValuePrope1tySource 配置的 random.*属性值  
  • jar 包外部的 application- {profi l e} .properties 或 application.yml （ 带 spring.profile ）配置文件  
  • jar 包内部的 application-{profile} .properties 或 application.ym （带 spring.profile ）配置文件  
  • jar 包外部的 application.properties 或 application .yml （不带 spring.profile ）配置文件  
  • jar 包内部的 application .properties 或 application .ym （不带 spring.profile ）配置文件  
  • @Configuration 注解类上的＠PropertySource  
  • 通过 SpringApplication .setDefaultProperties 指定的默认属性  
 
 **注解**   
1.@ComponentScan  
 (1)扫面当前包和子包  
 (2)设置被@Service注解类不被扫描 @ComponentScan(excludeFilters = {@Filter(classes = {Service . class})})   
2.@Primery   
 spring容器中存在两个实现同一接口的bean,当使用接口接收注入的bean时报错,解决这种情况:使用@Primery,意思为发现多  
 个同类型的bean,加@Primery的bean被优先注入    
3.@ConfigurationProperties(value = "pdc")  
 属性装配,和@Component一起使用,注意配合@Configuration时无法序列化  
4.@PropertySource(value={” classpath : jclbc. properties ”｝ ，ignoreResourceNotFound=true)  
 加载配置文件,默认只支持properties配置,不支持yml  
5.@Profile   
在 Spring中存在两个参数可以提供给我们配置,以修改启动Profile机制，一个是spring.profiles.active，  
另一个是 spring.profiles.default。在这两个属性都没有配置的情况下，Spring 将不会启动Profile机制.  
这就意味着被＠Profile标注的Bean将不会被Spring装配到roe容器中,以appcation-{profile}.[yml/properties]的配置  
不会加载。Spring是先判定是否存在spring.profiles .active配置后，再去查找spring.profiles.default配置的，  
所以spring.profiles.active的优先级要大于spring.profiles.default  
在 Java 启动项目中，我们只 需要如下配置就能够启动 Profile 机制 ：  
JAVA OPTS= "Dspring.profiles.active=dev "  
6.@Value  
可以使用SPEL    
propertiesFromConfig是注入容器的bean,propertiesFromConfig是bean的name或者类名开头小写  
@Value("#{propertiesFromConfig.getMap()}")  
private Map map;  
以下含义:这里引用由属性后跟着是一个？，这个符号 的含义是判断这个属性  
是否为空。如果不为空才会去执行 toUppercase 方法  
@Value ("#(beanName.str?.toUpperCase()}")   
private String otherBeanProp = null ;      
进行运算    
＃数学运算  
@Value("#{1+2}")  
private int run;  
＃浮点数比较运算  
@Value("#{beanName.pi==3.14f}" )  
private boolean piFlag;  
＃字符串比较运算  
@Value("#{beanName.str eq 'Spring Boot'}" )  
private boolean strFlag ;  
＃字符串连接  
@Value("#{beanName.str + '连接字符串 '}"）  
private String strApp = null ;  
＃三元运算  
@Value("#{beanName.d>1000? '大子':'小子'}")  
private String resultDesc = null ;  



 
**bean作用域**   
作用域类型--------使用范围------------用域描述
singleton--------所有 S pring 应用----默认值 ， loC 容器只存在单例    
prototype--------所有 Spring 应用-----每当从 IoC 容器中取出一个 Bean ，则创建一个新的 Bean  
session----------Spring Web 应用------HTTP 会话  
application------Spring Web 应用------Web 工程生命周期  
request----------Spring Web 应用------Web 工程单次请求 （ req uest)  
globalSession----Spring Web 应用------在一个全局的 HTTPSession 中，一个 Bean定义对应一个实例.实践中基本不使用  









 