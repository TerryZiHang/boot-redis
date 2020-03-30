package org.szh.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.szh.bean.ContactInfo;
import org.szh.mapper.ContactInfoMapper;
import org.szh.service.ContactService;

@Service("contactService")
public class ContactServiceImpl implements ContactService {

	@Resource
	private ContactInfoMapper contactInfoMapper;
	
	@Resource
	private RedisTemplate<Object, Object> redisTemplate;

	@Cacheable(value = "contacts")
	@Override
	public List<ContactInfo> getAll() {
		List<ContactInfo> list = contactInfoMapper.selectAll();
		System.out.println("查询所有数据");
		return list;
	}

	@CachePut(value = "contacts", key = "#contact.markId")
	@Override
	public ContactInfo save(ContactInfo contact) {
		contact.setMarkId(UUID.randomUUID().toString());
		contactInfoMapper.insert(contact);
		System.out.println("插入某条数据");
		return contact;
	}

	@CachePut(value = "contacts", key = "#contact.markId")
	@Override
	public ContactInfo updateContact(ContactInfo contact) {
		contactInfoMapper.updateByPrimaryKeySelective(contact);
		System.out.println("更新某条数据");
		return contact;
	}

	@Cacheable(value = "contacts", key = "#markId")
	@Override
	public ContactInfo getById(String markId) {
		ContactInfo base = contactInfoMapper.selectByPrimaryKey(markId);
		System.out.println("查询某条数据");
		return base;
	}

	@CacheEvict(allEntries = true)
	@Override
	public void deleteAll() {
		contactInfoMapper.deleteAll();
		System.out.println("清除所有缓存");
	}

	@CacheEvict(value = "contacts", key = "#markId")
	@Override
	public void deleteById(String markId) {
		contactInfoMapper.deleteByPrimaryKey(markId);
		System.out.println("删除某条数据");
	}
}
