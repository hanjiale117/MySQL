import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
}
