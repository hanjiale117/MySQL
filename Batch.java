import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Batch {
    public static void main(String[] args) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String sql = "insert into admin values(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.out.println("开始执行");
        long start = System.currentTimeMillis();//开始时间
        for (int i = 0; i < 5000; i++){
            preparedStatement.setString(1,"jack"+i);
            preparedStatement.setString(2,"666");
            //将sql语句加入到批处理包中
            preparedStatement.addBatch();
            /**
             * 1.先创建ArrayList - elementData = Object
             * 2.elementData = Objcet[]
             * 3.当elementData满后,就会按照1.5倍扩容
             * 4.当添加到指定的值后,就executeBatch
             * 5.批量处理会减少我们发送sql语句的网络开销 而且减少编译次数 因此效率提高
             *  public void addBatch() throws SQLException {
             *         synchronized(this.checkClosed().getConnectionMutex()) {
             *             if (this.batchedArgs == null) {
             *                 this.batchedArgs = new ArrayList();
             *             }
             *             for(int i = 0; i < this.parameterValues.length; ++i) {
             *                 this.checkAllParametersSet(this.parameterValues[i], this.parameterStreams[i], i);
             *             }
             *             this.batchedArgs.add(new BatchParams(this.parameterValues,
             *             this.parameterStreams, this.isStream, this.streamLengths, this.isNull));
             *         }
             *     }
             *
             */
            if((i+1) %1000 == 0){
                preparedStatement.executeBatch();
                //清空
                preparedStatement.clearBatch();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("time="+(end - start));
        JdbcUtils.close(null, preparedStatement, connection);
    }
}
