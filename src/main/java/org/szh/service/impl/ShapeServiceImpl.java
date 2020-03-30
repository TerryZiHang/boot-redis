package org.szh.service.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.szh.bean.ShapeInfo;
import org.szh.mapper.ShapeInfoMapper;
import org.szh.service.ShapeService;
import org.szh.util.MyRedisTemplate;

@Service("shapeService")
public class ShapeServiceImpl implements ShapeService{
	
	@Resource
	private ShapeInfoMapper shapeInfoMapper;
	
	@Resource
	private MyRedisTemplate myRedisTemplate;

	@Override
	public int add(ShapeInfo base) {
		base.setMarkId(UUID.randomUUID().toString());
		int count = shapeInfoMapper.insertSelective(base);
		if( count > 0) {
			String key = "shapes:shape:"+base.getMarkId();
			myRedisTemplate.lLeftPush(key, base, 1, TimeUnit.HOURS);
		}
		return count;
	}

	@Override
	public ShapeInfo getInfoById(String markId) {
		// 防止雪崩
		long offset = (markId.hashCode() & Integer.MAX_VALUE) % 0x7fff;
		boolean isExist = myRedisTemplate.getBitMap("shapes:info:ids", offset);
		if(!isExist) {
			return null;
		}
		return shapeInfoMapper.selectByPrimaryKey(markId);
	}
	
}

