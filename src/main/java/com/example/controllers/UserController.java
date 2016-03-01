package com.example.controllers;

import com.example.model.User;
import com.example.model.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * A class to test interactions with the MySQL database using the UserDao class.
 *
 * @author netgloo
 */
@Controller
@RequestMapping(path = "/user")
public class UserController {

  @Autowired
  private UserDao userDao;

  @RequestMapping(
          path = "/create",
          method = RequestMethod.POST)
  public @ResponseBody String create(@RequestParam(name = "email") String email,
                                     @RequestParam(name = "name") String name) {
    User user = null;
    try {
      user = new User(email, name);
      userDao.save(user);
    }
    catch (Exception ex) {
      return "Error creating the user: " + ex.toString();
    }
    return "User succesfully created! (id = " + user.getId() + ")";
  }
  

  @RequestMapping(
          path = "/delete",
  method = RequestMethod.DELETE)
  public @ResponseBody String delete(@RequestParam(name = "id") long id) {
    try {
      User user = new User(id);
      userDao.delete(user);
    }
    catch (Exception ex) {
      return "Error deleting the user:" + ex.toString();
    }
    return "User succesfully deleted!";
  }
  

  @RequestMapping(
          path = "/get-by-email",
          method =  RequestMethod.GET)
  public @ResponseBody String getByEmail(@RequestParam(name = "email") String email) {
    String userId;
    try {
      User user = userDao.findByEmail(email);
      userId = String.valueOf(user.getId());
    }
    catch (Exception ex) {
      return "User not found";
    }
    return "The user id is: " + userId;
  }
  

  @RequestMapping(
          path = "/update",
          method = RequestMethod.POST)
  public @ResponseBody String updateUser(@RequestParam(name = "id") long id,
                                         @RequestParam(name = "email") String email,
                                         @RequestParam(name = "name") String name) {
    try {
      User user = userDao.findOne(id);
      user.setEmail(email);
      user.setName(name);
      userDao.save(user);
    }
    catch (Exception ex) {
      return "Error updating the user: " + ex.toString();
    }
    return "User succesfully updated!";
  }
  
}