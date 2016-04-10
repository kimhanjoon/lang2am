package com.lang2am.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TextVO {

	private String code;
	private String type;
	private String description;
	private int referenced;

	private String locale;
	private String text;
	private String comment;
	private String status;
	private String createdIp;
	private String createdId;
	private String modifiedIp;
	private String modifiedId;

}
