import com.mq.common.util.JsonUtil;
import com.mq.conf.Application;
import com.mq.data.entity.TbMqMsg;
import com.mq.data.entity.TbUser;
import com.mq.data.to.request.MqMsgSearchRequest;
import com.mq.dbopt.mapper.TbMqMsgMapper;
import com.mq.dbopt.mapper.TbUserMapper;
import com.mq.dbopt.repository.TbUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TbUserTest {

	@Autowired
	private TbUserMapper tbUserMapper;
	@Autowired
	private TbMqMsgMapper tbMqMsgMapper;
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
		List<TbUser> userList = tbUserRepository.listByUnameAndPwd("111", "222");
		System.out.println(JsonUtil.objectToJson(userList));
	}

    @Test
    public void testMqMsgMapper() throws Exception {
        MqMsgSearchRequest param = new MqMsgSearchRequest();
        param.setStart(10L);
        param.setRows(10);
        List<TbMqMsg> list = tbMqMsgMapper.selectByOption(param);
        System.out.println(1);
    }
}