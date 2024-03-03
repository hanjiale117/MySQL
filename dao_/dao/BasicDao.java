package dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.DruidUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
