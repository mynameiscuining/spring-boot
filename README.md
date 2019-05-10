学习笔记springboot2  
===
**配置优先级顺序进行加载 ：**  
===
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
 ===
**1.@ComponentScan**  
 (1)扫面当前包和子包  
 (2)设置被@Service注解类不被扫描 @ComponentScan(excludeFilters = {@Filter(classes = {Service . class})})   
**2.@Primery**   
 spring容器中存在两个实现同一接口的bean,当使用接口接收注入的bean时报错,解决这种情况:使用@Primery,意思为发现多  
 个同类型的bean,加@Primery的bean被优先注入    
**3.@ConfigurationProperties(value = "pdc")**  
 属性装配,和@Component一起使用,注意配合@Configuration时无法序列化  
**4.@PropertySource(value={” classpath : jclbc. properties ”｝ ，ignoreResourceNotFound=true)**  
 加载配置文件,默认只支持properties配置,不支持yml  
**5.@Profile**   
在 Spring中存在两个参数可以提供给我们配置,以修改启动Profile机制，一个是spring.profiles.active，  
另一个是 spring.profiles.default。在这两个属性都没有配置的情况下，Spring 将不会启动Profile机制.  
这就意味着被＠Profile标注的Bean将不会被Spring装配到roe容器中,以appcation-{profile}.[yml/properties]的配置  
不会加载。Spring是先判定是否存在spring.profiles .active配置后，再去查找spring.profiles.default配置的，  
所以spring.profiles.active的优先级要大于spring.profiles.default  
在 Java 启动项目中，我们只 需要如下配置就能够启动 Profile 机制 ：  
JAVA OPTS= "Dspring.profiles.active=dev "  
**6.@Value**  
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
===  
作用域类型--------使用范围------------用域描述
singleton--------所有 S pring 应用----默认值 ， loC 容器只存在单例    
prototype--------所有 Spring 应用-----每当从 IoC 容器中取出一个 Bean ，则创建一个新的 Bean  
session----------Spring Web 应用------HTTP 会话  
application------Spring Web 应用------Web 工程生命周期  
request----------Spring Web 应用------Web 工程单次请求 （ req uest)  
globalSession----Spring Web 应用------在一个全局的 HTTPSession 中，一个 Bean定义对应一个实例.实践中基本不使用  

**mybatis mapper注入容器的方式**  
1.MapperScan("{mapper Package}")
2.mapper接口 注解@Mapper  

**spring-security**  
UserDetailsBuilder方法:  
accountExpired(boolean)------------->设置账号是否过期  
accountLocked(boolean)------------->是否锁定账号  
credential sExpired(boolean)------------->定义凭证是否过期  
disabled(boolean)------------->是否禁用用户  
username(String)------------->用户名  
authorities(GrantedAuthority... )------------->赋予一个或者权限  
authorities( List<? extends GrantedAuthority>)---------->权限列表   
password(String) 定义密码  
roles(String .. . ) 使用列表（ Li st ）赋予权限 赋予角色，会自动加入前缀“ROLE ”  

**redis**  
//获取地理位置操作接口  
redisTemplate.opsForGeo() ;  
//获取散列操作接口  
redisTemplate.opsForHash();  
//获取基数操作接口  
redisTemplate.opsForHyperLogLog() ;  
//获取列表操作接口  
redisTemplate.opsForList() ;  
//获取集合操作接口  
redisTemplate.opsForSet();  
//获取字符串操作接口  
redisTemplate.opsForValue() ;  
//获取有序集合操作接口  
redisTemplate.opsForZSet();   

//绑定某个键,做多次操作  
//获取地理位置绑定键操作接口  
redisTemplate.boundGeoOps("geo")  ;  
//获取散列绑定键操作接口  
redisTemplate.boundHashOps (”hash” );  
//获取列表（链表）绑定键操作接口  
redisTemplate.boundListOps("list");  
//获取集合绑定键操作接口  
redisTemplate.boundSetOps ("set");  
//获取字符串绑定键操作接口  
redisTemplate.boundValueOps("string")   
//获取有序集合绑定键操作接口  
redisTemplate.boundZSetOps("zset");  

SessionCallback接口和RedisCallback接口，它们的作用是让RedisTemplate进行回调，  
通过它们可以在同一条连接下执行多个Redis命令。其中SessionCallback提供了良好的封装，  
对于开发者比较友好，因此在实际的开发中应该优先选择使用它；相对而言 ，RedisCallback  
接口比较底层,需要处理的内容也比较多,可读性较差,所以在非必要的时候尽量不选择使用它  

首先 Redis是支持一定事务能力的 NoSQL，在 Redis中使用事务，通常的命令组合是 watch...  
multi .. .exec，也就是要在一个 Redis连接中执行多个命令，这时我们可以考虑使用SessionCallback  
接口来达到这个目的。其中，watch 命令是可以监控 Redis 的一些键：multi命令是开始事务，开始事务  
后，该客户端的命令不会马上被执行，而是存放在一个队列里，这点是需要注意的地方,也就是在  
这时我们执行一些堪回数据的命令 ， Redis也是不会马上执行的，而是把命令放到一个队列里，所以  
此时调用 Redis 的命令，结果都是返回 null ，这是初学者容易犯的错误： exe 命令的意义在于执行事  
务，只是它在队列命令执行前会判断被 watch 监控的 Redis 的键的数据是否发生过变化（即使赋予与  
之前相同的值也会被认为是变化过)，如果它认为发生了变化，那么 Redis 就会取消事务 ， 否则就会  
执行事务， Redis 在执行事务时，要么全部执行 ， 要么全部不执行 ，而且不会被其他客户端打断，这  
样就保证了 Redis 事务下数据的一致性   



















 