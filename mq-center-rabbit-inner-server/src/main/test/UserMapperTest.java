import com.mq.conf.Application;
import com.mq.data.entity.TbUser;
import com.mq.dbopt.mapper.TbUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserMapperTest {

	@Autowired
	private TbUserMapper userMapper;

	@Test
	public void testInsert() throws Exception {
        TbUser user = new TbUser();
        user.setUserName("sss");
        user.setPassword("ppp");
		int c = userMapper.insert(user);
        System.out.println(c);
    }

	@Test
	public void testSelect() throws Exception {
		TbUser user = userMapper.selectByPrimaryKey(1L);
        System.out.println(user.getUserName());
    }
}