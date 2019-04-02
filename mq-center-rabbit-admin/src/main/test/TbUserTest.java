import com.alibaba.fastjson.JSON;
import com.mq.common.util.JsonUtil;
import com.mq.conf.Application;
import com.mq.data.entity.TbUser;
import com.mq.dbopt.mapper.TbUserMapper;
import com.mq.dbopt.repository.TbUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TbUserTest {

	@Autowired
	private TbUserMapper tbUserMapper;
	@Autowired
	private TbUserRepository tbUserRepository;

	@Test
	public void testSelect() throws Exception {

		TbUser user = tbUserMapper.selectByPrimaryKey(1L);
        System.out.println(user.getUname());
    }

	@Test
	public void testFindById() throws Exception {
		Optional<TbUser> tbUserOptional = tbUserRepository.findById(1L);
		if (tbUserOptional.isPresent()) {
			TbUser user = tbUserOptional.get();
			System.out.println(JsonUtil.objectToJson(user));
		}
	}

	@Test
	public void testListByUname() throws Exception {
		List<TbUser> userList = tbUserRepository.listByUname("111");
		System.out.println(JsonUtil.objectToJson(userList));
	}
}