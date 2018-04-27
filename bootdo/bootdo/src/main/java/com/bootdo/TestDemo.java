package com.bootdo;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bootdo.blog.domain.BContentDO;
import com.bootdo.blog.service.BContentService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDemo {

	@Autowired
	private BContentService bContentService;

	@Test
	public void testDemo() {
		List<BContentDO> list = bContentService.list(null);
		for(BContentDO i:list) {
			System.out.println(i.getAuthor());
		}
		Assert.assertNotNull(list);
	}

}
