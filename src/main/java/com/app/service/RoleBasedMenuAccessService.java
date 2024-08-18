package com.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.model.SidebarItem;

@Service
public class RoleBasedMenuAccessService {

	 public List<SidebarItem> buildSidebarItems(String roleName) {
	        SidebarItem merchantMaster = new SidebarItem();
	        merchantMaster.setTitle("Merchant Master");
	        merchantMaster.setIcon("shopping-cart");
	        merchantMaster.setLink("#");
	        merchantMaster.setSubItems(Arrays.asList(
	                new SidebarItem("Mid Activation", null, "component-alert.html", null),
	                new SidebarItem("Merchant Configuration", null, "component-badge.html", null)
	        ));

	        SidebarItem userMaster = new SidebarItem();
	        userMaster.setTitle("User Master");
	        userMaster.setIcon("user");
	        userMaster.setLink("#");
	        userMaster.setSubItems(Arrays.asList(
	                new SidebarItem("User Creation", null, "component-extra-avatar.html", null),
	                new SidebarItem("Unlock User", null, "component-extra-divider.html", null)
	        ));

	        SidebarItem reportMaster = new SidebarItem();
	        reportMaster.setTitle("Report Master");
	        reportMaster.setIcon("file");
	        reportMaster.setLink("#");
	        reportMaster.setSubItems(Arrays.asList(
	                new SidebarItem("Merchant Transaction Report", null, "component-extra-avatar.html", null),
	                new SidebarItem("Merchant Transaction Status Report", null, "component-extra-divider.html", null),
	                new SidebarItem("WL All Transaction Report", null, "component-extra-divider.html", null)
	        ));

	        List<SidebarItem> sidebarItems = new ArrayList<>();
	        sidebarItems.add(merchantMaster);
	        sidebarItems.add(userMaster);
	        sidebarItems.add(reportMaster);

	        if (roleName.contains("admin")) {
	            SidebarItem authentication = new SidebarItem();
	            authentication.setTitle("Authentication");
	            authentication.setIcon("user");
	            authentication.setLink("#");
	            authentication.setSubItems(Arrays.asList(
	                    new SidebarItem("Login", null, "/login", null),
	                    new SidebarItem("Forgot Password", null, "auth-forgot-password.html", null)
	            ));
	            sidebarItems.add(authentication);
	        }

	        return sidebarItems;
	        
	 }
}
