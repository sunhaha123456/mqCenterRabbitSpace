import com.mq.common.util.HttpClientUtil;
import com.mq.common.util.JsonUtil;
import com.mq.conf.Application;
import com.mq.data.to.request.ThirdPlatformBuildMqMsgRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class InnerServerMqMsgTest {

	@Test
	public void thirdPlatformBuildMqMsgTest() throws Exception {
		for (int x = 0; x<100 ;x++) {
			String requestUrl = "http://127.0.0.1:8082/mqCenterInnerServer/mqMsg/thirdPlatformBuildMqMsg";
			String destUrl = "http://127.0.0.1:8082/mqCenterInnerServer/mqMsg/callbackTest";
			Map<String, Object> msg = new HashMap();
			msg.put("id", (int)(Math.random()*100));
			String content = JsonUtil.objectToJson(msg);
			ThirdPlatformBuildMqMsgRequest param = new ThirdPlatformBuildMqMsgRequest();
			if (x < 5) {
				param.setRequestPushMsgContent(Math.random() + "");
			} else {
				param.setRequestPushMsgContent(content);
			}
			param.setRequestPushPlatform(2);
			param.setRequestPushRemark("无");
			param.setRequestPushDestAddr(destUrl);
			Long intervalSecond = Long.valueOf(3+(int)(Math.random()*10));
			param.setRequestPushIntervalSecond(intervalSecond);
			HttpClientUtil.postJson(requestUrl, JsonUtil.objectToJson(param), true);
		}
	}
}