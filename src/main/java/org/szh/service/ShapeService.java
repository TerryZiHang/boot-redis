package org.szh.service;

import org.szh.bean.ShapeInfo;

public interface ShapeService {

	int add(ShapeInfo base);

	ShapeInfo getInfoById(String markId);

}
