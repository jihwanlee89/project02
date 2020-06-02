package com.springboot.payment.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CardCompanyMapper {

	public boolean insertRequestData(String data);
}
