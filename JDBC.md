# 1.JDBC

## 1.1.JDBC介绍

- JDBC为访问不同的数据库提供了统一的接口,为使用者屏蔽了细节问题

- Java程序员使用JDBC,可以连接任何提供了JDBC驱动程序的数据库系统,从而完成对数据库的各种操作

  <a href="https://smms.app/image/Nxfg5EdoSmMIBuL" target="_blank"><img src="https://s2.loli.net/2024/02/06/Nxfg5EdoSmMIBuL.png" ></a>
  
- JDBC是Java提供一套用于数据库操作接口的API,Java程序员只需要面向这套接口编程即可,不同的数据库厂商,需要针对这套接口,提供不同实现

- JDBC程序编写步骤

  1. 注册驱动-加载Driver类
  2. 获取连接-得到Connection
  3. 执行增删改查-发送SQL给mysql执行
  4. 释放资源-关闭相关连接





## 1.2.数据库的连接方式

```java
import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Jdbc01 {
  /**
   * 创建第一个Jdbc程序
   */
  public static void main(String[] args) throws SQLException {
      //前置工作 在项目文件下创建一个文件夹比如libs
      //将mysql.jar 拷贝到当前目录下 点击 添加库 加入到项目中
      //1.注册驱动
      Driver driver = new Driver();//抛出异常
      //创建driver对象

      //2.注册驱动
      String url = "jdbc:mysql://localhost:3306/db06";

      //1.jdbc:mysql:// 规定好表示协议 通过jdbc的方式连接mysql
      //2.localhost主机 可以是ip地址
      //3.3306表示mysql监听的端口
      //4.db06表示连接到哪个数据库
      //5.mysql本质连接 就是socket连接

      //6. 将用户名和密码封装到Properties对象中
      Properties properties = new Properties();
      properties.setProperty("user", "root");//用户
      properties.setProperty("password", "Han0852963741");//密码

      Connection connect = driver.connect(url, properties);//connect可以理解为网络连接(socket)

      //3.执行sql
      //String sql = "insert into actor values (null,'刘德华','男','1970-11-11',110)";//SQL语句
      String sql = "update actor set name = '周星驰' where id = 1";

      //创建一个statement 用于执行静态的sql语句 并返回其生成的结果
      Statement statement = connect.createStatement();

      int rows = statement.executeUpdate(sql);//生效返回 1  没有生效返回1,如果是dml语句  返回就是影响行数

      System.out.println(rows > 0 ? "success":"failure");

      //4.关闭连接资源
      statement.close();
      connect.close();
  }
}
```

```java
//数据库的连接方式
import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Jdbc02 {


  public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
      Driver driver = new Driver();
      String url = "jdbc:mysql://localhost:3306/db06";
      Properties properties = new Properties();
      properties.setProperty("user", "root");//用户
      properties.setProperty("password", "Han0852963741");//密码

      Connection connect = driver.connect(url, properties);
      System.out.println(connect);//com.mysql.jdbc.JDBC4Connection@7aec35a




      //2.
      //使用发射加载Driver类
      Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
      Driver driver1 = (Driver) aClass.newInstance();

      String url = "jdbc:mysql://localhost:3306/db06";
      Properties properties = new Properties();
      properties.setProperty("user", "root");//用户
      properties.setProperty("password", "Han0852963741");//密码

      Connection connect = driver1.connect(url, properties);
      System.out.println(connect);//com.mysql.jdbc.JDBC4Connection@7aec35a

      //3.
      //使用DriverManager 替代Driver 进行统一管理
      Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
      Driver driver = (Driver) aClass.newInstance();
      String url = "jdbc:mysql://localhost:3306/db06";
      String user = "root";
      String password = "Han0852963741";

      DriverManager.registerDriver(driver);//注册Driver驱动
      Connection connection = DriverManager.getConnection(url, user, password);
      System.out.println(connection);//com.mysql.jdbc.JDBC4Connection@67424e82

      //4.
      //使用Class.forName 自动完成注册驱动 简化代码
      //使用反射加载了Driver类
      //在加载Driver类时,完成注册
      Class.forName("com.mysql.jdbc.Driver");

      /**
       *  1.静态代码块 在类加载时 会执行一次
       *  2.DriverManager.registerDriver(new Driver());
       *  3.因此注册Driver自动完成
       *  static {
       *         try {
       *             DriverManager.registerDriver(new Driver());注册Driver自动完成
       *         } catch (SQLException var1) {
       *             throw new RuntimeException("Can't register driver!");
       *         }
       *     }
       */
      //创建url和user和password
      String url = "jdbc:mysql://localhost:3306/db06";
      String user = "root";
      String password = "Han0852963741";
      Connection connection = DriverManager.getConnection(url, user, password);
      System.out.println(connection);//com.mysql.jdbc.JDBC4Connection@7aec35a

  }
}

```

**注意事项:**

1. mysql驱动5.1.6可以无需Class.forName()也能直接连接到数据库(在mysql-connector-java/META-INF/services中含有这个包 自动去注册)
2. 从jdk1.5以后使用jdbc4,不再需要显示调用class.forName()注册驱动而是自动调用驱动jar包下META-INF/services/java.sql.Driver文本中的类名称去注册
3. 但还是要写上Class.forName

```java
public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
      //在方式四的基础上改进 使用配置文件 让连接mysql更加灵活
      //通过Properties对象获取配置文件的信息
      Properties properties = new Properties();
      properties.load(new FileInputStream("libs\\mysql.properties"));
      String user = properties.getProperty("user");
      String password = properties.getProperty("password");
      String driver = properties.getProperty("driver");
      String url = properties.getProperty("url");

      Class.forName(driver);
      Connection connection = DriverManager.getConnection(url, user, password);
      System.out.println(connection);//com.mysql.jdbc.JDBC4Connection@7aec35a
  }
```



## 1.3.ResultSet(结果集)

- 结果集
  1. 表示数据库结果集的数据库,通常执行查询数据库的语句生成
  2. ResultSet对象保持一个光标指向其当前数据行,最初,光标位于第一行之前
  3. next方法将光标移动到下一行,并且由于在ResultSet的对象中没有更多行时返回false,因此可以在while循环中使用循环来遍历结果集

```java
public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
      Properties properties = new Properties();
      properties.load(new FileInputStream("libs\\mysql.properties"));
      String user = properties.getProperty("user");
      String password = properties.getProperty("password");
      String driver = properties.getProperty("driver");
      String url = properties.getProperty("url");

      Class.forName(driver);
      Connection connection = DriverManager.getConnection(url, user, password);
      Statement statement = connection.createStatement();
      String sql = "select id,`name`,sex,borndate from actor";

      ResultSet resultSet = statement.executeQuery(sql);
      //指定给定的SQL语句,该语句返回单个ResultSet对象
      //ResultSet是一个接口 JDBCResultSet实现了这个接口
      // 数据存储在 rowDate-rows-elementDate-0.1.2.3...-internalDate中

      while (resultSet.next()){//光标先向后移动 如果没有更多行 则返回false
          int id = resultSet.getInt(1);//获取该行的第一列
        	//int id = resultSet.getInt("id");//通过列名来获取值
        	//这种方法出错较少,如果是直接使用列名来获取的话 那么这个id就是取的id
        	//如果第一个不是id,那么这个id可能就是name 导致报错
          String name = resultSet.getString(2);//获取改行的第二列
          String sex = resultSet.getString(3);
          Date date = resultSet.getDate(4);
          System.out.println(id+" "+name+" "+sex+" "+date);
      }
      //1 周星驰 男 1970-11-11
      //2 dick 男 1980-02-02
  }
```

<a href="https://smms.app/image/2SEysUZ3RuWgPv7" target="_blank"><img src="https://s2.loli.net/2024/02/07/2SEysUZ3RuWgPv7.png" ></a>







## 1.4.Statement

- 基本介绍

  1. Statement对象用于执行静态SQL语句并返回其生成的结果的对象

  2. 在连接建立后,需要对数据库进行访问,执行命名或是SQL语句,可以通过

     1. Statement
     2. PrepareStatement
     3. CallableStatement

  3. Statement对象执行SQL语句 存在SQL注入风险

  4. SQL注入是利用某些系统没有对用户输入的数据进行充分的检查,而在用户输入数据中注入非法的SQL语句段或命令,恶意攻击数据库

  5. 要防范SQL注入,只要用PreparedStatement(从Statement扩展而来)取代Statement就可以了

     ```MYSQL
     SELECT * FROM admin WHERE NAME = '1' OR'AND pwd = 'OR '1' = '1';
     --  用户名为1'or
     --  密码为or '1'='1
     ```





## 1.5.PreparedStatement

1. PrepatedStatement 执行的SQL语句 中的参数用问号(?)来表示,调用PreparedStatement对象的setXXX()方法来设置这些参数.setXXX()方法有俩个参数,第一个参数是要设置的SQL语句中的参数的索引(从1开始),第二个是设置的SQL语句中的参数的值
2. 调用executeQuery(),返回ResultSet对象
3. 调用executeUpdate():执行更新,包括增,删,修改
4. 不再使用+拼接sql语句,减少语法错误
5. 有效解决了sql注入问题
6. 大大减少了编译次数,效率较高

```java
String sql ="select name,pwd from admin where name =? and pwd=?";
//preparedStatement 对象实现了PreparedStatement 接口的实现类对象
PreparedStatement preparedStatement = connection.prepareStatement(sql);//这里sql已经与prepareStatement关联了
//给?赋值
preparedStatement.setString(1,admin_name);
preparedStatement.setString(2,admin_pwd);
//如果执行的是dml语句 
//这里执行executeQuery 不要在写sql
ResultSet resultSet = preparedStatement.executeQuery();//()里面不用填写sql语句 
```

```java
String sql  = "insert into admin values(?,?)";
PreparedStatement preparedStatement = connection.prepareStatement(sql);
preparedStatement.setString(1,admin_name);
preparedStatement.setString(2,admin_pwd);
int rows = prepareStatement.executeUpdate();//不用输入sql语句 上面的sql已经与prepareStatement关联了

prepareStatement.close();
con nection.close();
```







## 1.6.JDBC API

- DriverManger驱动管理类

  getConnection(url,user,pwd)获取连接

- Connection接口

  createStatement创建Statement对象

  preparedStatement(sql)生成预处理对象

- Statement接口

  executeUpdate(sql)执行dml语句,返回影响的行数

  executeQuery(sql)执行查询,返回ResultSet对象

  execute(sql)执行任意的sql,返回布尔值

- PrepareStatement接口

  executeUpdate()执行dml语句

  executeQuery()执行查询,返回ResultSet

  execute()执行sql,返回布尔类型

  setXXX(占位符索引,占位符的值)解决sql注入

  setObject(占位符索引,占位符的值)

- ResultSet(结果集)

  next()向下移动一行(指向的是第一行的前面,不是第一行)如果没有下一行返回false

  previous()向上移动一行,如果没有上一行,返回false
  
  getXXX(列的索引值|列名)返回对应列的值,接受类型是XXX
  
  getObject(列的索引|列名)返回对应列的值,接受类型为Object





## 1.7.JDBCUtils

```java
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JdbcUtils {
  //定义相关的属性
  //只需要一份 做成静态的static
  private static String user;
  private static String password;
  private static String url;//url
  private static String driver;//驱动名

  static {
      //在static代码块去初始化
      Properties properties = new Properties();
      try {
          properties.load(new FileInputStream("src\\mysql.properties"));

          user = properties.getProperty("user");
          password = properties.getProperty("password");
          url = properties.getProperty("url");
          driver = properties.getProperty("driver");
      } catch (IOException e) {
          //实际开发中 通常会把这个异常抛出
          //可以选择捕获异常,也可以选择默认处理该异常
          throw new RuntimeException(e);
      }
  }

  public static Connection getConnection(){
      try {
          return DriverManager.getConnection(url,user,password);
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
  }

  //关闭资源
  public static void close(ResultSet set, Statement statement, Connection connection){

      //判断是否为null
      try {
      if(set != null){
          set.close();
      }
      if (statement!=null){
          statement.close();
      }
      if(connection != null){
          connection.close();
      }
      } catch (SQLException e) {
          throw new RuntimeException(e);
      }
  }

}
```





## 1.8.事务

- 介绍

  1. JDBC程序中当一个Connection对象创建时,默认情况下是自动提交事务:每次执行一个SQL语句时,如果执行成功,就会向数据库自动提交,而不能回滚
  2. JDBC程序中为了让多个SQL语句作为一个整体执行,需要使用事务
  3. 调用Connection的setAutoCommite(false)可以取消自动提交事务
  4. 在所有的SQL语句都成功执行后,调用commit():方法提交事务
  5. 在其中某个操作失败或出现异常时,调用rollback():方法回滚事务

  ```java
  import java.sql.Connection;
  import java.sql.PreparedStatement;
  import java.sql.SQLException;
  
  public class Transaction {
    public static void main(String[] args) {
        Connection connection = null;//初始化
        String sql = "update account set balance = balance - 100 where id = 1";
        String sql2 = "update account set balance = balance + 100 where id = 2";
        PreparedStatement preparedStatement = null;
        try{
            connection = JdbcUtils.getConnection();
            connection.setAutoCommit(false);//取消sql的自动提交
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();//执行第一条sql
  
            //int i = 1/0;//抛出异常
  
            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.executeUpdate();//执行第二条语句
  
            //提交事务
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        JdbcUtils.close(null, preparedStatement,connection);
    }
  }
  ```



## 1.9.批处理

- 介绍

  1. 当需要成批插入或者更新记录时,可以采用Java的批量更新机制,这一机制允许多条语句一次性提交给数据库批量处理,通常情况下比单独提交处理更有效率

  2. JDBC的批量处理语句包括下面方法:

     addBatch():添加需要批处理的SQL语句或参数

     executeBatch():执行批量处理语句

     clearBatch():清空批处理包的语句

  3. **JDBC连接MYSQL时,如果要使用批处理功能,请在url中加参数 url=jdbc:mysql://localhost:3306/db06?rewriteBatchedStatements=true**

  4. 批处理往往和PrepareStatement一起搭配使用,可以减少编译次数,又减少运行次数,效率大大提高

  ```mysql
  import java.sql.Connection;
  import java.sql.PreparedStatement;
  import java.sql.SQLException;
  
  public class Batch {
    public static void main(String[] args) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String sql = "insert into admin values(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.out.println("开始执行");
        long start = System.currentTimeMillis();//开始时间
        for (int i = 0; i < 5000; i++){
            preparedStatement.setString(1,"jack"+i);
            preparedStatement.setString(2,"666");
            //将sql语句加入到批处理包中
            preparedStatement.addBatch();
            /**
             * 1.先创建ArrayList - elementData = Object
             * 2.elementData = Objcet[]
             * 3.当elementData满后,就会按照1.5倍扩容
             * 4.当添加到指定的值后,就executeBatch
             * 5.批量处理会减少我们发送sql语句的网络开销 而且减少编译次数 因此效率提高
             *
             *  public void addBatch() throws SQLException {
             *         synchronized(this.checkClosed().getConnectionMutex()) {
             *             if (this.batchedArgs == null) {
             *                 this.batchedArgs = new ArrayList();
             *             }
             *             for(int i = 0; i < this.parameterValues.length; ++i) {
             *                 this.checkAllParametersSet(this.parameterValues[i], this.parameterStreams[i], i);
             *             }
             *             this.batchedArgs.add(new BatchParams(this.parameterValues,
             *             this.parameterStreams, this.isStream, this.streamLengths, this.isNull));
             *         }
             *     }
             *
             */
            if((i+1) %1000 == 0){
                preparedStatement.executeBatch();
                //清空
                preparedStatement.clearBatch();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        JdbcUtils.close(null, preparedStatement, connection);
    }
  }
  
  ```

  





## 1.10.数据库连接池

- 介绍
  1. 预先在缓冲中池放入一定数量的连接,当需要建立数据库连接时,只需要从“缓冲池”中取出一个,使用完毕之后再放回去
  2. 数据库连接池负责分配,管理和释放数据库连接,它允许应用程序重复使用一个现有的数据库连接,而不是重新建立一个
  3. 当应用程序向连接池请求的连接数超过最大连接数量时,这些请求将被加入到等待队列中
  
- 数据库连接池种类
  1. JDBC的数据库连接池使用java.sql.DataSource来表示,DataSource只是一个接口,该接口通常由第三方提供实现
  2. C3P0数据库连接池,速度相对较慢,稳定性不错(hibernate,spring)
  3. DBCP数据库连接池,速度相对c3p0较快,但不稳定
  4. Proxool数据库连接池,有监控连接池状态的功能,稳定性较c3p0差一点
  5. BoneCP数据库连接池,速度快
  6. Druid(德鲁伊)是阿里提供的数据库连接池,集DBCP,C3P0,Proxool优点于一身的数据库连接池
  
- **C3P0连接池**

  ```java
  public class C3P0_use {
    public static void main(String[] args) throws IOException, PropertyVetoException, SQLException {
        //1.创建一个数据源对象
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
  
        //2.通过配置文件获取相关信息
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\mysql.properties"));
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driver = properties.getProperty("driver");
        //3.给数据源comboPooledDataSource
  
        //连接的管理是由comboPooledDataSource管理的
        comboPooledDataSource.setDriverClass(driver);
        comboPooledDataSource.setJdbcUrl(url);
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(password);
  
        //4.设置初始化连接数
        comboPooledDataSource.setInitialPoolSize(10);//初始化连接为10个
  
        //5.最大连接数
        comboPooledDataSource.setMaxPoolSize(50);
        long start = System.currentTimeMillis();
        for (int i =0 ; i < 5000 ; i++){
            Connection connection = comboPooledDataSource.getConnection();//这个方法从DataSource接口实现的
            //6.关闭连接
            connection.close();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);//217
    }
  }
  
  ```

  ```xml
  <!--c3p0-config.xml 配置文件--!>
  <c3p0-config>
    <!-- 数据源名称代表连接池 -->
  <named-config name="hjl">
  <!-- 驱动类 -->
  <property name="driverClass">com.mysql.jdbc.Driver</property>
  <!-- url-->
    <property name="jdbcUrl">jdbc:mysql://localhost:3306/db06</property>
  <!-- 用户名 -->
      <property name="user">root</property>
      <!-- 密码 -->
    <property name="password">Han0852963741</property>
    <!-- 每次增长的连接数-->
    <property name="acquireIncrement">5</property>
    <!-- 初始的连接数 -->
    <property name="initialPoolSize">10</property>
    <!-- 最小连接数 -->
    <property name="minPoolSize">5</property>
   <!-- 最大连接数 -->
    <property name="maxPoolSize">50</property>
  
  <!-- 可连接的最多的命令对象数 -->
    <property name="maxStatements">5</property> 
  
    <!-- 每个连接对象可连接的最多的命令对象数 -->
    <property name="maxStatementsPerConnection">2</property>
  </named-config>
  </c3p0-config>
  ```

  ```java
  ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource("hjl");
        long start = System.currentTimeMillis();
          for (int i =0 ; i < 5000 ; i++){
        Connection connection = comboPooledDataSource.getConnection();
        connection.close();
          }
       long end = System.currentTimeMillis();
       System.out.println(end - start);//224 
  ```

- **Druid数据库连接池**

  ```java
  public class Druid_ {
    public static void main(String[] args) throws Exception {
        //1.加入Druid jar包
        //2.加载配置文件
        //3.创建properties对象 读取配置文件
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\druid.properties"));
        //4.创建一个指定参数的数据库连接池  Druid连接池
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000 ; i++) {
            Connection connection = dataSource.getConnection();
            //关闭连接
            connection.close();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);//233
    }
  }
  //连接次数越多   C3P0的速度不如Druid  Druid性能稳定
  ```

  ```properties
  #key=value
  driverClassName=com.mysql.jdbc.Driver
  url=jdbc:mysql://localhost:3306/db06?rewriteBatchedStatements=true
  #url=jdbc:mysql://localhost:3306/db06
  username=root
  password=Han0852963741
  #initial connection Size 初始化连接数
  initialSize=10
  #min idle connection size 最小连接数
  minIdle=5
  #max active connection size 最大连接数
  maxActive=20
  #max wait time (5000 mil seconds) 连接最大时间 超过5秒说明超时了
  maxWait=5000
  
  #druid.properties
  ```
  
  ```java
  
  public class Druid_plus {
    private static DataSource ds;
    //静态代码块完成ds初始化
  
    static {
        Properties properties = new Properties();
        try{
            properties.load(new FileInputStream("src//druid.properties"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
    //getConnection方法
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
  
    //关闭连接方法 (不是真正的关闭,而是把连接放回数据池)
    //在数据库连接池技术中,close不是真正的断掉连接 而是把使用的Connection对象放回连接池
  
    public static void  close(ResultSet resultSet, Statement statement, Connection connection){
        try {
            if(resultSet != null){
                resultSet.close();
            }
  
            if (statement != null){
                statement.close();
            }
            if(connection != null){
                connection.close();
                //把引用断掉 跟Mysql中的Close不一样
            }
  
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
  }
  
  ```
  
  **druid的close和mysql的close是俩个断开机制**





## 1.11.Apache-DBUtils

- 介绍

  1. commons-dbutils是Apache组织提供的一个开源的JDBC工具库类,它是对JDBC的封装,**使用dbutils能极大简化jdbc编码的工作量**

- DBUtils类

  1. QueryRunner类:该类封装了SQL的执行,**线程是安全的**,可以实现增,删,该,查,批处理
  2. 使用QueryRunner类实现查询
  3. ResultSetHandle接口:该接口用于处理java.sql.ResultSet,将数据按要求转换为另一种形式
  4. ArrayHandler:把结果集中的第一行数据转化为对象数组
  5. ArrayListHandler:把结果集中的每一行数据都转换成一个数组,再存放在List中
  6. BeanHandler:将结果集中的第一行数据封装到一个对应的JavaBean实例中
  7. BeanListHandler:将结果集中的每一行数据都封装到一个对应的JavaBean实例中,再存放在List中
  8. ColumnListHandler:将结果集中的某一列的数据存放在List中
  9. KeyedHandler(name):将结果集中的每行数据都封装到Map中,再把这些,map再存放到一个map中,其key为指定的key
  10. MapHandler:将结果集中的第一行数据封装到一个Map里,key是列名,value就是对应的值
  11. MapListHandler:将结果集中的每一行数据都封装到一个Map里,然后再存放在List

  ```java
  public class DBUtils_use {
      //使用apache-DBUtils 工具类 + druid对表的增删改查
      public void testQuery() throws SQLException {
          //返回是多行的情况
          Connection connection = Druid_plus.getConnection();
          //引入DBUtils 相关的jar包
          //创建QueryRunner类
          QueryRunner queryRunner = new QueryRunner();
          //queryRunner可以执行相关查询
          String sql = "select * from actor where id >= ?";
          List<Actor> query = queryRunner.query(connection, sql, new BeanListHandler<>(Actor.class), 1);
          //query方法就是执行一个sql语句 并返回resultSet 再把这个ResultSet封装到一个ArraryList集合中 最后返回这个集合
          //connection:连接
          //sql:sql语句
          //new BeanListHandler<>(Actor.class):在将ResultSet取出Actor对象,然后再封装到ArraryList
          //1:是传给"?"的  给"?"赋值的  它是可变参数 "...params"
          //底层使用反射机制去获取Actor类的属性 然后进行封装
  
          for (Actor actor : query){
              System.out.println(actor);
          }
          //释放资源
          //底层会自动关闭resultSet 也会关闭statement (PreparedStatement) 所以只用关闭connection就可以了
          Druid_plus.close(null, null, connection);
      }
    //queryRunner.query的源码
  /**
       * public <T> T query(Connection conn, String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
       *         PreparedStatement stmt = null;//定义PreparedStatement
       *         ResultSet rs = null;//接受返回的ResultSet
       *         T result = null; // 返回ArrayList
       *         try {
       *             stmt = this.prepareStatement(conn, sql);//创建PreparedStatement
       *             this.fillStatement(stmt, params);//对sql的?进行赋值
       *             rs = this.wrap(stmt.executeQuery());//执行sql语句 返回ResultSet
       *             result = rsh.handle(rs);//把返回的ResultSet 转换为ArraryList(使用到反射)
       *         } catch (SQLException var33) {
       *             this.rethrow(var33, sql, params);
       *         } finally {
       *             try {
       *                 this.close(rs);//关闭resultSet对象
       *             } finally {
       *                 this.close((Statement)stmt);//关闭preparedStatement对象
       *             }
       *         }
       *         return result;
       *     }
       */
  }
  ```

  ```java
  //ScalarHandler
  public void testScalar() throws SQLException {
      Connection connection = Druid_plus.getConnection();
      //引入DBUtils 相关的jar包
      //创建QueryRunner类
      QueryRunner queryRunner = new QueryRunner();
      String sql = "select name from actor where id = ?";
      Object query = queryRunner.query(connection, sql, new ScalarHandler(), 4);//返回的是单行单列的一个数值
      //返回的是一个对象,使用的Handler就是ScalarHandler
      System.out.println(query);
  
      Druid_plus.close(null, null, connection);
  }
  ```

  ```java
  //执行dml语句(update,insert,delete)
  public void testDml() throws SQLException {
      Connection connection = Druid_plus.getConnection();
      //引入DBUtils 相关的jar包
      //创建QueryRunner类
      QueryRunner queryRunner = new QueryRunner();
      String sql = "upadate actor set name = ? where id = ?";
  
      int update = queryRunner.update(connection, sql, "dick", 4);// 执行几行 返回几
      //方法是update 但是可以执行insert和delete对象
      //返回的值是受影响的行数
      Druid_plus.close(null, null, connection);
  }
  ```







## 1.12.BasicDao

- 介绍

  1. DAO:date access object 数据访问对象
  2. 这样通用类被称为BasicDao,是专门用来和数据库交互的,既完成对数据库的CRUD操作
  3. 在BasicDao的基础上,实现一张表对应一个Dao,更好的完成功能

  ```java
  //BasicDao的具体代码实现
  public class BasicDao<T> {
      //泛型指定具体类型
      private QueryRunner qr = new QueryRunner();
  
      //dml语句
      public int update(String sql, Object... paremeters){
          //动态指定sql语句
          Connection connection = null;
          try {
               connection = DruidUtils.getConnection();
              int update = qr.update(connection, sql, paremeters);
              //返回影响的行数
              return update;
          } catch (SQLException e) {
              throw new RuntimeException(e);
          }finally {
              DruidUtils.close(null, null, connection);
  
          }
      }
  
      //返回多个对象 针对任意的对象  既查询的结果是多行
  
      /**
       *
       * @param sql  sql语句可以有 "?"
       * @param clazz 传入一个类的Class对象 比如Actor.class
       * @param parameters  传入?的具体的值 可以是多个
       * @return 根据Actor.class 返回对应的ArrayList集合
       */
      public List<T> queryMulti(String sql,Class<T> clazz, Object... parameters){
          Connection connection = null;
          try {
              connection = DruidUtils.getConnection();
             return qr.query(connection, sql,new BeanListHandler<T>(clazz),parameters);
  
          } catch (SQLException e) {
              throw new RuntimeException(e);
          }finally {
              DruidUtils.close(null, null, connection);
          }
  
      }
  
      //查询单行结果 的通用方法
      public T querySingle(String sql, Class <T> clazz, Object... parameters){
          Connection connection = null;
          try {
              connection = DruidUtils.getConnection();
              return qr.query(connection,sql, new BeanHandler<T>(clazz),parameters);
  
          } catch (SQLException e) {
              throw new RuntimeException(e);
          }finally {
              DruidUtils.close(null, null, connection);
          }
      }
  }
  ```

  
