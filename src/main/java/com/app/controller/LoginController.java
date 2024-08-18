package com.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.app.model.SidebarItem;
import com.app.service.RoleBasedMenuAccessService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private RoleBasedMenuAccessService objRoleBasedMenuAccessService;

	@GetMapping("/login")
	public String loginPage()

	{
		return "auth-login";

	}

	@GetMapping("/home")
	public String homePage(Model model, Authentication authentication) {
		if (authentication == null || !authentication.isAuthenticated()
				|| !(authentication.getPrincipal() instanceof UserDetails)) {
			logger.info("Unauthorized access");
			return "auth-login";
		}

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String currentUsername = userDetails.getUsername();
		String roleNames = userDetails.getAuthorities().toString();
		logger.info("username:{}", currentUsername);
		logger.info("Roles:{}", roleNames);

		try {
			List<SidebarItem> sidebarItems = objRoleBasedMenuAccessService.buildSidebarItems(currentUsername);
			model.addAttribute("sidebarItems", sidebarItems);
			return "pages/main-home";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "auth-login";
		}
	}

	@GetMapping("/perform-logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("Custom logout endpoint called");
        Cookie cookie = new Cookie("JSESSIONID", null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		return "redirect:/login";
	}

}
