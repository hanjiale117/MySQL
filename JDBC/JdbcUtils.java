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
            return DriverManager.getConnection(url,user,password);//加载驱动
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
