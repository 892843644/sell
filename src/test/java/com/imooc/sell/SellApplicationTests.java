package com.imooc.sell;

import com.alibaba.fastjson.JSONObject;
import com.imooc.sell.entity.OrderDetailEntity;
import com.imooc.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellApplicationTests {

	@Test
	public void contextLoads() {
		String jsonStr= "[{\n" +
				"    productId: \"1423113435324\",\n" +
				"    productQuantity: 2 //购买数量\n" +
				"}]";
		List<OrderDetailEntity> orderDetailEntity = JSONObject.parseObject(jsonStr,List.class);
		log.info(orderDetailEntity+"");
	}

}
