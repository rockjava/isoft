package com.isoftframework.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.isoftframework.controller.PageController;
import com.isoftframework.demo.model.page.TClubChannelDTO;
import com.isoftframework.demo.model.page.TClubMemberDTO;
import com.isoftframework.demo.service.page.IClubChannelService;
import com.isoftframework.demo.service.page.IClubMemberService;
@Controller
@RequestMapping(value = "/demo/page")
public class PageDemoController extends PageController {
	@Autowired
	IClubMemberService myClubMemberService;
	@Autowired
	IClubChannelService  myClubChannelService;

	@RequestMapping(value = "/queryClubMember")
	public String queryClubMember(HttpServletRequest request,HttpServletResponse response,Model model) throws Exception {

		//request.setCharacterEncoding("GBK");
		// 构造查询条件语句，默认必须为null，不可为"",
		// 如果查询条件为null则取已有的查询条件, 如果排序条件为null则取已有的排序条件
		String querySql = null;
		String sqlOrderBy = " order by dto.createTime desc";

		querySql = "from TClubMemberDTO dto where 1=1 ";
		String createTime = request.getParameter("createTime");
		
		String scode = request.getParameter("scode");
		String sname = request.getParameter("sname");
		System.out.println("=--sname---" + sname);
		String clubChannelDTOId = request.getParameter("clubChannelDTOId");
		String isLocked = request.getParameter("isLocked");

		if (createTime != null && !createTime.trim().equals("")) {
			// querySql=querySql+" and dto.createTime =to_date('"+createTime+" ',  'yyyy-mm-dd')  ";
			querySql = querySql
					+ " and to_char(dto.createTime ,'yyyy-mm-dd hh24:mi:ss') like '"
					+ createTime + "%' ";
			// to_char(sysdate,'yy-mm-dd hh24:mi:ss')
		}
		if (scode != null && !scode.trim().equals("")) {
			querySql = querySql + " and dto.scode like '" + scode + "%' ";
		}
		if (sname != null && !sname.trim().equals("")) {
			querySql = querySql + " and dto.sname like '" + sname + "%' ";
		}
		if (clubChannelDTOId != null && !clubChannelDTOId.trim().equals("")) {
			querySql = querySql + " and dto.clubChannelDTO.id ='"
					+ clubChannelDTOId + "' ";
		}
		if (isLocked != null && !isLocked.trim().equals("")) {
			querySql = querySql + " and dto.isLocked ='" + isLocked + "' ";
		}
		
	/*	String countSql = "select count(dto) as count  "
			+ querySql.substring(querySql.indexOf("from"));*/
		 
		querySql=querySql+sqlOrderBy;
		// 查询前计算分页数据
		computeHibernatePageInfoBefore(request, querySql);
		// System.out.println("querySql="+querySql);
		// 调用业务方法
		List result = myBaseService.findAsPageList(getContext(request).getPageInfo());
		model.addAttribute("result", result);
		
		
		// 绿色通道下来框
		model.addAttribute("channelList", myBaseService.find("from TClubChannelDTO dto "));

		return ("demo/page/manage");

	}

	// 更新锁定状态
	@RequestMapping(value = "/updateLockState")
	public String updateLockState(@RequestParam("id") String id,@RequestParam("state") int state) throws Exception {
		
		// PrintWriter out = response.getWriter();
		
		TClubMemberDTO tClubMemberDTO = myBaseService.get(TClubMemberDTO.class,id);
		tClubMemberDTO.setIsLocked(state);
		myBaseService.updateTX(tClubMemberDTO);
		logger.info("-----锁定状态修改成功-----");
		// out.print("1");
		return"goBack";
		 
	}

	// 修改会员信息
	@RequestMapping(value = "/beforeEditMemberInfo")
	public String beforeEditMemberInfo(@RequestParam("id")String id,Model model) throws  Exception {
		 
		List<TClubChannelDTO> channelList = myBaseService
				.find("from TClubChannelDTO dto ");
		model.addAttribute("channelList", channelList);

		TClubMemberDTO tClubMemberDTO = myBaseService.get(TClubMemberDTO.class,id);
		model.addAttribute("member", tClubMemberDTO);
		return"demo/page/editMember";
		 
	}

	// 修改会员信息
	@RequestMapping(value = "/editClubMember")
	public String editClubMember( TClubMemberDTO tClubMemberDTO)throws Exception {
		
		TClubMemberDTO tclMemberDTO = myBaseService.get(TClubMemberDTO.class,tClubMemberDTO.getId());
		tclMemberDTO.setScode(tClubMemberDTO.getScode());
		tclMemberDTO.setSpass(tClubMemberDTO.getSpass());
		tclMemberDTO.setSname(tClubMemberDTO.getSname());
		tclMemberDTO.setClubChannelDTO(myClubChannelService.get(tClubMemberDTO.getClubChannelDTOId()));
		myBaseService.updateTX(tclMemberDTO);
		logger.info("---会员信息修改成功---");
		return"goBack";
	 
	}
	@RequestMapping(value = "/updateAll")
	public String updateAll(@RequestParam("ids") String ids,@RequestParam("state") String state)throws Exception {
		logger.info("要更新的会员的id:" + ids);
		
		if (ids!=null && ids.length() > 0) {
			myBaseService.execUpdateBySqlTX("update demo_club_member t set t.is_locked='"+state+"' where t.id in("+ids+")" );

		}
		return"goBack";
	}
	@RequestMapping(value = "/beforeAddMember")
	public String beforeAddMember(Model model) throws Exception {
		 
			List<TClubChannelDTO> channelList = myBaseService
					.find("from TClubChannelDTO dto ");
			model.addAttribute("channelList", channelList);
			return"demo/page/addMember";
		 
	}
	@RequestMapping(value = "/addClubMember")
	public String addClubMember(TClubMemberDTO clubMemberDTO)throws Exception {
	 
		clubMemberDTO.setClubChannelDTO(this.myClubChannelService.get(clubMemberDTO.getClubChannelDTOId()));
		clubMemberDTO.setCreateTime(new java.util.Date());
		clubMemberDTO.setIsLocked(0);
		this.myBaseService.saveTX(clubMemberDTO);
		
		System.out.println("save:" + clubMemberDTO.getClubChannelDTO());
		return"goBack";
		

	}

	 
}
