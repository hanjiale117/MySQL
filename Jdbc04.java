import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Jdbc04 {
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("libs\\mysql.properties"));
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");

        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, user, password);

//        String sql = "create table news (id INT PRIMARY KEY , `title` VARCHAR(32), content VARCHAR(32) DEFAULT '') ";
//        String sql1= "INSERT INTO news VALUES (1,'标题1','内容1')";
//        String sql2= "INSERT INTO news VALUES (2,'标题2','内容2')";
//        String sql3= "INSERT INTO news VALUES (3,'标题3','内容3')";
//        String sql4= "INSERT INTO news VALUES (4,'标题4','内容4')";
//        String sql5= "INSERT INTO news VALUES (5,'标题5','内容5')";
//        String sql = "update news SET content = '新的消息' where id = 1";
//        String sql2 = "delete from news where id = 3";
        Statement statement = connection.createStatement();
//        int rows =statement.executeUpdate(sql);
//        int rows1 =statement.executeUpdate(sql1);
//        int rows2 =statement.executeUpdate(sql2);
//        int rows3 =statement.executeUpdate(sql3);
//        int rows4 =statement.executeUpdate(sql4);
//        int rows5 =statement.executeUpdate(sql5);
//        statement.executeUpdate(sql );
//        statement.executeUpdate(sql2);
//        String sql = "select "
//        statement.executeUpdate(sql);

        statement.close();
        connection.close();












    }
}
