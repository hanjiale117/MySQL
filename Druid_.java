import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;
@SuppressWarnings("all")
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
        for (int i = 0; i < 500000 ; i++) {

            Connection connection = dataSource.getConnection();
            //关闭连接
            connection.close();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);//233

    }
}
