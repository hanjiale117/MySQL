import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
@SuppressWarnings("all")
public class C3P0_use {
    public static void main(String[] args) throws IOException, PropertyVetoException, SQLException {
//        //1.创建一个数据源对象
//        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
//
//        //2.通过配置文件获取相关信息
//        Properties properties = new Properties();
//        properties.load(new FileInputStream("src\\mysql.properties"));
//        String user = properties.getProperty("user");
//        String password = properties.getProperty("password");
//        String url = properties.getProperty("url");
//        String driver = properties.getProperty("driver");
//        //3.给数据源comboPooledDataSource
//
//        //连接的管理是由comboPooledDataSource管理的
//        comboPooledDataSource.setDriverClass(driver);
//        comboPooledDataSource.setJdbcUrl(url);
//        comboPooledDataSource.setUser(user);
//        comboPooledDataSource.setPassword(password);
//
//        //4.设置初始化连接数
//        comboPooledDataSource.setInitialPoolSize(10);//初始化连接为10个
//
//        //5.最大连接数
//        comboPooledDataSource.setMaxPoolSize(50);
//        long start = System.currentTimeMillis();
//        for (int i =0 ; i < 5000 ; i++){
//            Connection connection = comboPooledDataSource.getConnection();//这个方法从DataSource接口实现的
//            //6.关闭连接
//            connection.close();
//        }
//        long end = System.currentTimeMillis();
//        System.out.println(end - start);//217
//


        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource("hjl");
        long start = System.currentTimeMillis();
          for (int i =0 ; i < 5000 ; i++){
        Connection connection = comboPooledDataSource.getConnection();
        connection.close();
          }
       long end = System.currentTimeMillis();
       System.out.println(end - start);//224

    }
}
