package org.szh.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.szh.bean.ContactInfo;
import org.szh.bean.Result;
import org.szh.service.ContactService;

import io.swagger.annotations.Api;

@Api(tags= {"ContactController:联系人管理"})
@RestController
@RequestMapping(value = "test")
public class ContactController {

	@Resource
	private ContactService contactService;
	
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	public Result<?> saveContactor(@RequestBody ContactInfo base){
		return new Result<>(contactService.save(base));
	}
	
	@RequestMapping(value = "/all",method = RequestMethod.GET)
	public Result<?> findAll(){
		return new Result<>(contactService.getAll());
	}
	
	@RequestMapping(value = "/update",method = RequestMethod.PATCH)
	public Result<?> updateContactor(@RequestBody ContactInfo base){
		return new Result<>(contactService.updateContact(base));
	}
	
	@RequestMapping(value = "/delete",method = RequestMethod.DELETE)
	public Result<?> deleteContactor(@RequestParam("markId") String markId){
		contactService.deleteById(markId);
		return new Result<>();
	}
	
	@RequestMapping(value = "/deleteAll",method = RequestMethod.DELETE)
	public Result<?> deleteAll(){
		contactService.deleteAll();
		return new Result<>();
	}
	
	@RequestMapping(value = "/findOne",method = RequestMethod.GET)
	public Result<?> getContactor(@RequestParam("markId") String markId){
		ContactInfo base = contactService.getById(markId);
		return new Result<>(base);
	}
	
}
