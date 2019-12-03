package com.softmetrix.controllers.administrator;

import com.softmetrix.model.User;
import com.softmetrix.model.DTO.UserDTO;
import com.softmetrix.service.RoleService;
import com.softmetrix.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("administrator/users")
public class AdministratorUserController {
    @Autowired private UserService userService;    
    @Autowired private RoleService roleService;           
    
    @GetMapping("")
    public String listUsers(Model model, @PageableDefault(size = 10, sort = "id") Pageable pageable){               
        model.addAttribute("page", userService.getPage(pageable));        
        List<Sort.Order> sortOrders = pageable.getSort().stream().collect(Collectors.toList());
        if (sortOrders.size() > 0) {
            Sort.Order order = sortOrders.get(0);
            model.addAttribute("sortProperty", order.getProperty());
            model.addAttribute("sortDesc", order.getDirection() == Sort.Direction.DESC);
        }
        return "administrator/users/list";
    }
    
    @GetMapping("/new")
    String createNewUser(Model model, UserDTO userDTO){
        model.addAttribute("heading", "New User");         
        model.addAttribute("roles", roleService.findAll());        
        return "administrator/users/form";
    }
    
    @PostMapping("/new")
    String proccessNewUser(Model model, @Valid UserDTO userDTO, BindingResult result){                   
        if(result.hasErrors()){
            model.addAttribute("roles", roleService.findAll());    
            model.addAttribute("heading", "New User");
            return "administrator/users/form";
        } 
        try {
        	userService.save(userDTO);
        }catch(DataIntegrityViolationException ex) {
        	model.addAttribute("roles", roleService.findAll());    
            model.addAttribute("heading", "New User");
            model.addAttribute("unique", true);
            return "administrator/users/form";
        }
        
        return "redirect:/administrator/users";
    }
    
    @GetMapping("/edit")
    String editUser (@RequestParam("user_id") Integer id, Model model){
        User user = userService.findById(id).orElse(null);
            
        UserDTO userDTO = new UserDTO(user);
        model.addAttribute("userDTO", userDTO);        
        model.addAttribute("roles", roleService.findAll()); 
        model.addAttribute("heading", "Edit User");
        
        return "administrator/users/form";
    }
   
    @GetMapping("/delete")
    String deleteUser(@RequestParam("user_id") Integer id){
        userService.delete(id);
        return "redirect:/administrator/users";
    }   
}
