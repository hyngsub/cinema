package com.chea.cinema.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FileType {

	private String suffix;
	private String contentType;

	public FileType(String suffix, String contentType) {
		this.setSuffix(suffix);
		this.setContentType(contentType);
	}
}
