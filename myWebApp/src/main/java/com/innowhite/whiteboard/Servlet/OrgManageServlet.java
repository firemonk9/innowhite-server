package com.innowhite.whiteboard.Servlet;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.innowhite.whiteboard.persistence.beans.OrganizationVO;
import com.innowhite.whiteboard.persistence.dao.OrganizationDAO;
import com.innowhite.whiteboard.util.Utility;

/**
 * Servlet implementation class OrgManageServlet
 */
public class OrgManageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrgManageServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log.info("entered Do Post to create new Org");
		OrganizationVO orgVO = new OrganizationVO();
		orgVO.setOrgName(request.getParameter("org_name"));
		orgVO.setParentOrg(request.getParameter("parent_org"));
		orgVO.setOrgType(request.getParameter("org_type"));
		orgVO.setRoomCallback(request.getParameter("room_callback"));
		orgVO.setSaltKey(request.getParameter("salt_key"));

		String startDate = request.getParameter("start_date");
		String expDate = request.getParameter("expiry_date");
		orgVO.setStartDate(Utility.getSqlDateFromStr(startDate));
		orgVO.setExpiryDate(Utility.getSqlDateFromStr(expDate));

		log.debug(" the values are : " + orgVO);
		OrganizationDAO.saveOrganization(orgVO);

	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log.info("entered Do Put to Update an Org");
		OrganizationVO orgVO = new OrganizationVO();
		orgVO.setOrgName(request.getParameter("org_name"));
		orgVO.setParentOrg(request.getParameter("parent_org"));
		orgVO.setOrgType(request.getParameter("org_type"));
		orgVO.setRoomCallback(request.getParameter("room_callback"));
		orgVO.setSaltKey(request.getParameter("salt_key"));

		String startDate = request.getParameter("start_date");
		String expDate = request.getParameter("expiry_date");
		orgVO.setStartDate(Utility.getSqlDateFromStr(startDate));
		orgVO.setExpiryDate(Utility.getSqlDateFromStr(expDate));

		log.debug(" the values are : " + orgVO);
		OrganizationDAO.updateOrganization(orgVO);

	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log.info("entered Do delete to delete an Organization orgName ");

		String orgName = request.getParameter("org_name");
		String parentOrg = request.getParameter("parent_org");
		log.debug(" delete orgName : " + orgName + " parentOrg:  " + parentOrg);
		OrganizationDAO.deleteOrganization(orgName, parentOrg);

	}

}
