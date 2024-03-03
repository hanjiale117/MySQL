import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Transaction {
    public static void main(String[] args) {
        Connection connection = null;//初始化
        String sql = "update account set balance = balance - 100 where id = 1";
        String sql2 = "update account set balance = balance + 100 where id = 2";
        PreparedStatement preparedStatement = null;
        try{
            connection = JdbcUtils.getConnection();
            connection.setAutoCommit(false);//取消sql的自动提交
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();//执行第一条sql

            //int i = 1/0;//抛出异常

            preparedStatement = connection.prepareStatement(sql2);
            preparedStatement.executeUpdate();//执行第二条语句

            //提交事务
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        JdbcUtils.close(null, preparedStatement,connection);
    }
}
