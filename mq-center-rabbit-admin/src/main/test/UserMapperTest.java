import com.mq.conf.Application;
import com.mq.data.entity.TbUser;
import com.mq.dbopt.mapper.UserMapper;
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
	public void testSelect() throws Exception {
		TbUser user = userMapper.selectByPrimaryKey(1L);
        System.out.println(user.getUname());
    }
}