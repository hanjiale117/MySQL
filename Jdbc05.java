import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Jdbc05 {
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
            String name = resultSet.getString(2);//获取改行的第二列
            String sex = resultSet.getString(3);
            Date date = resultSet.getDate(4);
            System.out.println(id+" "+name+" "+sex+" "+date);
        }
        //1 周星驰 男 1970-11-11
        //2 dick 男 1980-02-02
        resultSet.close();
        connection.close();
        statement.close();


    }
}
