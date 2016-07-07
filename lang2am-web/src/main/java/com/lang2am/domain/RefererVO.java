package com.lang2am.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefererVO {

	private String code;

	private String refererType;

	private String refererFullname;

	private String refererName;

	private String refererPosition;

	private String refererContent;

}
