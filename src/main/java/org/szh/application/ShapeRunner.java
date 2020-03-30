package org.szh.application;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.szh.mapper.ShapeInfoMapper;
import org.szh.util.MyRedisTemplate;

@Component
public class ShapeRunner implements ApplicationRunner{
	
	@Resource
	private MyRedisTemplate myRedisTemplate;
	
	@Resource
	private ShapeInfoMapper shapeInfoMapper;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 重启动时删除之前缓存
		myRedisTemplate.delete("shapes:info:ids");
		List<String> ids = shapeInfoMapper.selectIds();
		for(String id:ids) {
			long offset = (id.hashCode() & Integer.MAX_VALUE) % 0x7fff;
			myRedisTemplate.setBitMap("shapes:info:ids", offset,true);
		}
	}

}
