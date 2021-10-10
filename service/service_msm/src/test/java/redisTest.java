import com.wang.msm.MsmApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = MsmApplication.class)
@RunWith(SpringRunner.class)
public class redisTest {
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Test
    public void redis(){
        redisTemplate.opsForValue().set("key1","value");
    }
}
