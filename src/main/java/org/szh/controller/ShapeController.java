package org.szh.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.szh.bean.ShapeInfo;
import org.szh.service.ShapeService;

@RestController
@RequestMapping(value = "shapes")
public class ShapeController {
	
	@Resource
	private ShapeService shapeService;
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public Object add(@RequestBody ShapeInfo base) {
		return shapeService.add(base);
	}
	
	@RequestMapping(value = "/{markId}",method = RequestMethod.GET)
	public Object getInfoById(@PathVariable("markId") String markId) {
		return shapeService.getInfoById(markId);
	}
}
