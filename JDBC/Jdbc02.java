import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Jdbc02 {


    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
//        Driver driver = new Driver();
//        String url = "jdbc:mysql.properties://localhost:3306/db06";
//        Properties properties = new Properties();
//        properties.setProperty("user", "root");//用户
//        properties.setProperty("password", "Han0852963741");//密码
//
//        Connection connect = driver.connect(url, properties);
//        System.out.println(connect);//com.mysql.properties.jdbc.JDBC4Connection@7aec35a
//
//
//
//
//        //2.
//        //使用发射加载Driver类
//        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
//        Driver driver1 = (Driver) aClass.newInstance();
//
//        String url = "jdbc:mysql.properties://localhost:3306/db06";
//        Properties properties = new Properties();
//        properties.setProperty("user", "root");//用户
//        properties.setProperty("password", "Han0852963741");//密码
//
//        Connection connect = driver1.connect(url, properties);
//        System.out.println(connect);//com.mysql.properties.jdbc.JDBC4Connection@7aec35a
//
//        //3.
//        //使用DriverManager 替代Driver 进行统一管理
//        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
//        Driver driver = (Driver) aClass.newInstance();
//        String url = "jdbc:mysql.properties://localhost:3306/db06";
//        String user = "root";
//        String password = "Han0852963741";
//
//        DriverManager.registerDriver(driver);//注册Driver驱动
//        Connection connection = DriverManager.getConnection(url, user, password);
//        System.out.println(connection);//com.mysql.properties.jdbc.JDBC4Connection@67424e82
//
//        //4.
//        //使用Class.forName 自动完成注册驱动 简化代码
//        //使用反射加载了Driver类
//        //在加载Driver类时,完成注册
//        Class.forName("com.mysql.jdbc.Driver");
//
//        /**
//         *  1.静态代码块 在类加载时 会执行一次
//         *  2.DriverManager.registerDriver(new Driver());
//         *  3.因此注册Driver自动完成
//         *  static {
//         *         try {
//         *             DriverManager.registerDriver(new Driver());注册Driver自动完成
//         *         } catch (SQLException var1) {
//         *             throw new RuntimeException("Can't register driver!");
//         *         }
//         *     }
//         */
//        //创建url和user和password
//        String url = "jdbc:mysql.properties://localhost:3306/db06";
//        String user = "root";
//        String password = "Han0852963741";
//        Connection connection = DriverManager.getConnection(url, user, password);
//        System.out.println(connection);//com.mysql.properties.jdbc.JDBC4Connection@7aec35a




    }
}
