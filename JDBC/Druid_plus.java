import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

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
