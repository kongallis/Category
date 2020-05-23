package com.supermarketTeam.controllers;


import java.util.List;
import javax.validation.Valid;
import com.supermarketTeam.dto.ProductRegistrationDto;
import com.supermarketTeam.entities.Product;
import com.supermarketTeam.services.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class AppController {

    @Autowired
    private ProductServiceImpl service;
  

    @RequestMapping("/")
    public String viewHomePage(Model model) {
        List<Product> products = service.listAll();
        model.addAttribute("products", products);
        return "index";
    }

    @RequestMapping("/new")
    public String showNewProductPage(Model model) {
        ProductRegistrationDto product = new ProductRegistrationDto();            
        model.addAttribute("product", product);
        return "new";
    }

    @PostMapping("/new")
    public String addProduct(@ModelAttribute("product") @Valid ProductRegistrationDto prodDto,
            BindingResult result) {
        if (result.hasErrors()) {
            return "/new";
        }
        service.save(prodDto);
        return "redirect:/";
    }
    

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id")int id, Model model) {
    Product product = service.findById(id);  
    service.delete(product);
    return "redirect:/";
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editProduct(ModelMap view, @PathVariable int id) {
        Product product = service.findById(id); 
        view.addAttribute("product", product);
        return ("edit");
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String updateProduct(@Valid Product product, BindingResult result, ModelMap view) {

        if (result.hasErrors()) {
            return "edit";
        }
        service.update(product);
        return ("redirect:/");
    }
    
}
