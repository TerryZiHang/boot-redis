package org.szh.bean;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

@Data
public class ShapeInfo implements Serializable{
	
	private static final long serialVersionUID = -107808767442812172L;

	private String markId;

    private String theme;

    private String description;

    public Map<String,Object> toMap(Object e) {
		return null;
    }
}