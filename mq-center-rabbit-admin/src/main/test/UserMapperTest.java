import com.mq.conf.Application;
import com.mq.data.model.User;
import com.mq.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserMapperTest {

	@Autowired
	private UserMapper userMapper;

	@Test
	public void testInsert() throws Exception {
        User user = new User();
        user.setUserName("sss");
        user.setPassword("ppp");
		int c = userMapper.insert(user);
        System.out.println(c);
    }

	@Test
	public void testSelect() throws Exception {
		User user = userMapper.selectByPrimaryKey(1L);
        System.out.println(user.getUserName());
    }
}