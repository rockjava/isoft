package com.isoftframework.demo.service.page;

import java.util.List;

import com.isoftframework.common.exception.AppException;
import com.isoftframework.demo.model.page.TClubChannelDTO;

public interface IClubChannelService {

	public abstract TClubChannelDTO get(String clubChannelDTOId)
			throws Exception;

	public abstract List listByIds(String ids) throws Exception;

	/**
	 * 导入一条绿色通道的数据，或者新增，或者修改
	 * @param pDto
	 * @return
	 * @throws AppException
	 */
	public abstract int intoChannelTX(TClubChannelDTO pDto) throws Exception;

}