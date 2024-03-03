package test;

import dao.ActorDao;
import domain.Actor_;

import java.util.List;

public class TestDao {
    //测试ActorDao的curd方法
    public void testActorDao(){
        ActorDao actorDao = new ActorDao();
        //1.查询
        List<Actor_> actorS = actorDao.queryMulti("select * from actor where id >= ?", Actor_.class, 1);

        for (Actor_ actor : actorS){
            System.out.println(actor);
        }

        //2.查询单行记录
        //....

    }
}
