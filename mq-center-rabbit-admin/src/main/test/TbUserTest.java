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
	public void testFind() throws Exception {
		System.out.println(JsonUtil.objectToJson(tbUserRepository.findAll()));
		/*
		TbUser user = new TbUser();
		user.setCreateDate(new Date());
		user.setUname("哈哈哈");
		user.setUpwd("aaaa");
		tbUserRepository.save(user);
		*/
		System.out.println(JsonUtil.objectToJson(tbUserRepository.findAll()));



		//Optional<TbUser> tbUserOptional = tbUserRepository.findById(1L);
		//System.out.println(user.getUname());
	}
}