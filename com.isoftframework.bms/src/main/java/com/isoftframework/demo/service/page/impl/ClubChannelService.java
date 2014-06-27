package com.isoftframework.demo.service.page.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.isoftframework.common.exception.AppException;
import com.isoftframework.demo.dao.page.IClubChannelDAO;
import com.isoftframework.demo.model.page.TClubChannelDTO;
import com.isoftframework.demo.service.page.IClubChannelService;
import com.isoftframework.service.AbstractService;
import com.isoftframework.service.IService;


@Transactional
public class ClubChannelService implements IClubChannelService 
{	//com.sinovatech.jxltclub.com.sinovatech.page.model.dao.ClubChannelDAO
	@Autowired
	private IService myBaseService;
	
	private IClubChannelDAO myClubChannelDAO;

	
	/* (non-Javadoc)
	 * @see com.isoftframework.demo.service.page.IClubChannelService#get(java.lang.String)
	 */
	@Override
	public TClubChannelDTO get(String clubChannelDTOId) throws Exception {
		
		return  myBaseService.get(TClubChannelDTO.class, clubChannelDTOId);
	}
	/* (non-Javadoc)
	 * @see com.isoftframework.demo.service.page.IClubChannelService#listByIds(java.lang.String)
	 */
	@Override
	public List listByIds(String ids)throws Exception
	{
		ids = "'" + ids.replaceAll(",", "','")+ "'";
		return myClubChannelDAO.listByIds(ids);
	}
	/* (non-Javadoc)
	 * @see com.isoftframework.demo.service.page.IClubChannelService#intoChannelTX(com.isoftframework.demo.model.page.TClubChannelDTO)
	 */
	@Override
	public int intoChannelTX(TClubChannelDTO pDto)throws Exception
	{
		String channelID = pDto.getScode()==null?"":pDto.getScode();//绿色通道编号
		String[] obj= {channelID};
		TClubChannelDTO dto = myClubChannelDAO.findEntity("from TClubChannelDTO dto where dto.scode=?",obj);
//		数据表中已经有数据
		if(dto==null)
			myBaseService.saveTX(pDto);
		else{
			myBaseService.updateTX(pDto);
		}
		return 0;
	}
	 


	 
	///////////////////////////////////////////////////////
	////////////////////Spring调用区////////////////////////
	///////////////////////////////////////////////////////

	@Resource(name="myClubChannelDAO")
	public void setMyClubChannelDAO(IClubChannelDAO myClubChannelDAO)
	{
		this.myClubChannelDAO=myClubChannelDAO;
	}
	
	

}