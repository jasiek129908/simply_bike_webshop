package com.example.demo.controller;

import com.example.demo.entities.CartItem;
import com.example.demo.entities.User;
import com.example.demo.service.ShoppingCartService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @GetMapping("/cart")
    public String showShoppingCart(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getCurrentlyLoggedUser(authentication);
        List<CartItem> cartItems = shoppingCartService.listCarItems(user);

        model.addAttribute("cartItems", cartItems);
        return "shopping_cart";
    }

    @PostMapping("/cart/add/{id}")
    public RedirectView addProductToCart(@PathVariable("id") Long bikeId,
                                         RedirectAttributes redirAttrs) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            redirAttrs.addFlashAttribute("info", "Muszisz się zalogować, by dodać przedmiot");
            return new RedirectView("/bikes");
        }
        User user = userService.getCurrentlyLoggedUser(authentication);
        if (user == null) {
            redirAttrs.addFlashAttribute("info", "Muszisz się zalogować, by dodać przedmiot");
            return new RedirectView("/bikes");
        }
        shoppingCartService.addProduct(bikeId, 1, user);
        redirAttrs.addFlashAttribute("info", "Dodano przedmiot!");
        return new RedirectView("/bikes");
    }

    @PostMapping("/cart/remove/{id}")
    public RedirectView removeBikeFromCart(@PathVariable("id") Long bikeId,
                                           RedirectAttributes redirAttrs) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            redirAttrs.addFlashAttribute("info", "Muszisz się zalogować, by dodać przedmiot");
            return new RedirectView("/bikes");
        }
        User user = userService.getCurrentlyLoggedUser(authentication);
        if (user == null) {
            redirAttrs.addFlashAttribute("info", "Muszisz się zalogować, by dodać przedmiot");
            return new RedirectView("/bikes");
        }
        shoppingCartService.removeBike(bikeId, user);
        return new RedirectView("/cart");
    }

    @PostMapping("/cart/update/{id}/{qty}")
    public RedirectView updateProductQuantity(@PathVariable("id") Long bikeId,
                                              @PathVariable("qty") Integer quantity,
                                              RedirectAttributes redirAttrs) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            redirAttrs.addFlashAttribute("info", "Muszisz się zalogować, by dodać przedmiot");
            return new RedirectView("/bikes");
        }
        User user = userService.getCurrentlyLoggedUser(authentication);
        if (user == null) {
            redirAttrs.addFlashAttribute("info", "Muszisz się zalogować, by dodać przedmiot");
            return new RedirectView("/bikes");
        }
        shoppingCartService.updatedQuantity(quantity, bikeId, user);
        return new RedirectView("/cart");
    }

}
