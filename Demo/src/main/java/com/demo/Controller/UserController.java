package com.demo.Controller;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import com.demo.Dto.LoginDto;
import com.demo.Dto.SignupDto;
import com.demo.Dto.UserProfileDto;
import com.demo.Model.User;
import com.demo.Service.IMultipartFileService;
import com.demo.Service.IUserService;

@Controller
@RequestMapping("/v1/api/")
public class UserController {

	@Autowired
	IUserService userService;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	IMultipartFileService multipartFileService;

	@RequestMapping(value = "user", method = RequestMethod.POST)  //REST API TO SIGNUP USER
	public ResponseEntity<Map<String, Object>> signUp(@RequestBody SignupDto signupDto) {
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			if (signupDto != null) {
				User user = userService.findByEmail(signupDto.getEmail());
				if (user == null) {
					user = userService.mergeAsNew(modelMapper.map(signupDto, User.class));
					data.put("user", user);
					response.put("data", data);
					response.put("message", "Your account has been created successfully");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
				} else {
					response.put("message", "Email is already registered with us");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
				}
			} else {
				response.put("message", "Bad Request");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("message", "Something Went Wrong");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "login", method = RequestMethod.POST) //REST API TO LOGIN USER
	public ResponseEntity<Map<String, Object>> login(@RequestBody LoginDto loginDto) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			User user = userService.findByEmail(loginDto.getEmail());
			if (user != null && user.getPassword().matches(loginDto.getPassword())) {
				response.put("message", "Your account has been login successfully");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			} else {
				response.put("message", "Email & Password are not correct");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("message", "Something went Wrong");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "user/{id}", method = RequestMethod.PUT) //REST API TO CREATE USER PROFILE
	public ResponseEntity<Map<String, Object>> createProfile(@PathVariable("id") Long id,
			@RequestBody UserProfileDto userProfileDto) {
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			User user = userService.findById(id);
			if (user != null) {
				user.setFirstName(userProfileDto.getFirstName());
				user.setLastName(userProfileDto.getLastName());
				user.setAddress(userProfileDto.getAddress());
				user.setLatitude(userProfileDto.getLatitude());
				user.setLongitude(userProfileDto.getLongitude());
				user = userService.save(user);

				data.put("user", user);
				response.put("data", data);
				response.put("message", "Your account has been updated successfully");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

			} else {
				response.put("message", "We are unable to user with given id");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("message", "Something went Wrong");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "user/{id}", method = RequestMethod.POST) //REST API TO UPLOAD PROFILE PICTURE
	public ResponseEntity<Map<String, Object>> updateProfilePicture(@PathVariable("id") Long id,
			@RequestPart("file") MultipartFile file) {
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			User user = userService.findById(id);
			if (user != null) {
				user.setImage(multipartFileService.saveUserImage(file, id));
				data.put("user", user);
				response.put("data", data);
				response.put("message", "Your account has been updated successfully");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

			} else {
				response.put("message", "We are unable to user with given id");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("message", "Something went Wrong");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "createUser/{id}", method = RequestMethod.POST) //REST API TO CREATE USER PROFILE WITH PICTURE
	public ResponseEntity<Map<String, Object>> createProfile(@PathVariable("id") Long id,
			@RequestParam(value = "data") String jsonStr,
			@RequestPart(value = "file", required = false) MultipartFile file) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			User user = userService.findById(id);
			if (user != null) {

				if (file != null) {
					user.setImage(multipartFileService.saveUserImage(file, user.getId()));
				}
				user.setFirstName(jsonObject.getString("firstName"));
				user.setLastName(jsonObject.getString("lastName"));
				user.setAddress(jsonObject.getString("address"));
				user.setLatitude(jsonObject.getDouble("latitude"));
				user.setLongitude(jsonObject.getDouble("longitude"));
				user = userService.save(user);
				data.put("user", user);
				response.put("data", data);
				response.put("message", "Your account has been updated successfully");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			} else {
				response.put("message", "We are unable to user with given id");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("message", "Something went Wrong");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value = "user/{id}", method = RequestMethod.GET) //REST API TO GET USER PROFILE
	public ResponseEntity<Map<String, Object>> getUser(@PathVariable("id") Long id) {
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			User user = userService.findById(id);
			if (user != null) {
				data.put("user", user);
				response.put("data", data);
				response.put("message", "Your account detail has been successfully");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

			} else {
				response.put("message", "We are unable to user with given id");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("message", "Something went Wrong");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
