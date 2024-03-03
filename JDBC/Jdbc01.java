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
        String url = "jdbc:mysql.properties://localhost:3306/db06";

        //1.jdbc:mysql.properties:// 规定好表示协议 通过jdbc的方式连接mysql
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
