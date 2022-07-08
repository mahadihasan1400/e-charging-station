package com.example.demo.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


import dto.Owner;
import dto.ChargingStation;

@Controller
public class OwnerController {

	// https://e-charging-cs.herokuapp.com
	// http://172.22.194.20:8080
	@Autowired
	private HttpSession httpSession;

	@GetMapping("/owner-registration-forms")
	public String viewOwnerRegistration() {
		return "owner-registration";
	}

	@GetMapping("/owner")
	public Object saveData(@RequestParam String name, String email, String pass) {
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(
				"https://e-charging-cs.herokuapp.com/saveOwner?name=" + name + "&email=" + email + "&pass=" + pass,
				String.class);
		if ("success".equals(response))
			return "redirect:/owner-sign-in-form";
		else
			return "redirect:/owner-registration-forms";

	}

	@GetMapping("/owner-sign-in-form")
	public String viewUserSignInForm() {

		return "owner-sign-in";
	}

	@GetMapping("/owner/email")
	public String checkOwner(@RequestParam String email, Model model) {
		RestTemplate restTemplate = new RestTemplate();
		Owner response = restTemplate.getForObject("https://e-charging-cs.herokuapp.com/loginOwner?email=" + email,
				Owner.class);

		httpSession.setAttribute("ownerId", response.getId());
		httpSession.setAttribute("ownerName", response.getName());
		return "redirect:/owner-dashboard";

	}
	
	@GetMapping("/owner-dashboard")
	public String ownerDashboard(Model model) {
		
		String ownerName = (String) httpSession.getAttribute("ownerName");
		model.addAttribute("name", ownerName);
		return "owner-dashboard";
	}


	@GetMapping("/charging-form")
	public Object chargingForm() {

		return "charging-add";
	}

	@GetMapping("/saveChargingStation")
	public String saveChargingStation(@RequestParam String city, String address, String price, String status) {

		String ownerId = (String) httpSession.getAttribute("ownerId");
		RestTemplate restTemplate = new RestTemplate();
		ChargingStation response = restTemplate.getForObject("https://e-charging-cs.herokuapp.com/saveStation?city="
				+ city + "&address=" + address + "&price=" + price + "&status=" + status + "&id=" + ownerId,
				ChargingStation.class);
		httpSession.setAttribute("city", response.getCity());
		httpSession.setAttribute("address", response.getAddress());
		httpSession.setAttribute("price", response.getPrice());
		httpSession.setAttribute("status", response.getStatus());
		return "redirect:/owner-charging-station-details";

	}

	@GetMapping("/owner-charging-station-details")
	public String ownerChargingCtationDetails(Model model) {
		String city = (String) httpSession.getAttribute("city");
		String price = (String) httpSession.getAttribute("price");
		String status = (String) httpSession.getAttribute("status");
		String address = (String) httpSession.getAttribute("address");

		model.addAttribute("city", city);
		model.addAttribute("price", price);
		model.addAttribute("status", status);
		model.addAttribute("address", address);

		return "owner-charging-station-details";
	}
}
