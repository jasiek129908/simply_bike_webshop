package com.example.demo.controller;

import com.example.demo.entities.Bike;
import com.example.demo.entities.ClientOrder;
import com.example.demo.entities.OrderDetails;
import com.example.demo.entities.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    private UserService userService;
    @Autowired
    private BikeService bikeService;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private ClientOrderService clientOrderService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("")
    public String getHomePage(Model model) {
        List<Bike> lastThreeAddedBikes = bikeService.getLastThreeAddedBikes();
        model.addAttribute("bikeList", lastThreeAddedBikes);
        return "index";
    }

    @GetMapping("/login")
    public String getLoginForm() {
        return "loginForm";
    }

    @GetMapping("/bikes")
    public String getBikesView(Model model) {
        List<Bike> bikes = bikeService.getAllBikes();
        if (model.containsAttribute("bikeList") == false)
            model.addAttribute("bikeList", bikes);

        return "bikes";
    }

    @GetMapping("/bikes/search")
    public ModelAndView searchBikes(@RequestParam("text") String text, RedirectAttributes redirAtt) {
        List<Bike> bikes = bikeService.findBikesByPhrase(text);
        ModelAndView mv = new ModelAndView("redirect:/bikes");
        redirAtt.addFlashAttribute("bikeList", bikes);
        return mv;
    }

    @GetMapping("/register")
    public String getRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registerForm";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(roleRepository.getRoleByName("USER"));
        user.setStatusDeleted(false);
        userService.registerUser(user);
        return "redirect:";
    }

    @GetMapping("/purchase")
    public String getPurchasePage(Model model) {
        model.addAttribute("clientOrder", new ClientOrder());
        return "purchase";
    }

    @PostMapping("/process_purchase")
    public RedirectView processPurchase(ClientOrder clientOrder, RedirectAttributes redirAttrs) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getCurrentlyLoggedUser(authentication);

        purchaseService.processPurchase(clientOrder, user);
        redirAttrs.addFlashAttribute("info", "Dokonałeś zakupu :)");
        return new RedirectView("/bikes");
    }

    @GetMapping("/transactionsHistory")
    public String getPurchaseHistoryPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getCurrentlyLoggedUser(authentication);
        List<ClientOrder> allUserOrder = clientOrderService.getAllUserOrder(user);

        model.addAttribute("orderList", allUserOrder);
        return "transactionsHistory";
    }

    @GetMapping("/transactionsHistory/details/{orderID}")
    public String getPurchaseHistoryDetailsPage(@PathVariable("orderID") Long orderId, Model model) {
        List<OrderDetails> orderDetailsByOrder = clientOrderService.getOrderDetailsByOrderId(orderId);
        model.addAttribute("orderDetail", orderDetailsByOrder);
        return "transactionsHistoryDetails";
    }
}
