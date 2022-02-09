package com.example.demo.controller;

import com.example.demo.entities.Bike;
import com.example.demo.entities.ClientOrder;
import com.example.demo.entities.OrderDetails;
import com.example.demo.entities.User;
import com.example.demo.service.AdminService;
import com.example.demo.service.BikeService;
import com.example.demo.service.ClientOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ListIterator;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private BikeService bikeService;
    @Autowired
    private ClientOrderService clientOrderService;

    private final String UPLOAD_DIR = "src/main/resources/static/images/";

    @GetMapping("")
    public String getAdminPage() {
        return "admin";
    }

    @GetMapping("/deleteUser")
    public String getDeleteUserPage(Model model) {
        List<User> usersList = adminService.getAllUsers();
        model.addAttribute("usersList", usersList);
        return "deleteUser";
    }

    @PostMapping("/deleteUser/{userId}")
    public RedirectView deleteUser(@PathVariable("userId") Long userId, RedirectAttributes redirAttrs) {
        adminService.setUserStatusToDeleted(userId);
        redirAttrs.addFlashAttribute("info", "Ustawiono status");
        return new RedirectView("/admin/deleteUser");
    }

    @GetMapping("/changeBikeData")
    public String getDeleteBikePage(Model model) {
        model.addAttribute("bike", new Bike());
        List<Bike> bikes = bikeService.getAllBikes();
        model.addAttribute("bikeList", bikes);
        return "changeBikeData";
    }

    @PostMapping("/changeBikeData")
    public RedirectView changeBikeData(Bike bike, RedirectAttributes redirAttrs) {
        if (bike != null)
            System.out.println(bike);
        else
            System.out.println("bike jest nullem");
        if (adminService.updateBikeData(bike) == false) {
            redirAttrs.addFlashAttribute("info", "Wprowadziłeś złe dane!");
        } else {
            redirAttrs.addFlashAttribute("info", "Udało się zmienić inforamcje");
        }
        return new RedirectView("/admin/changeBikeData");
    }

    @GetMapping("/submitTheOrder")
    public String getSubmitOrderPage(Model model) {
        List<ClientOrder> allUserOrder = clientOrderService.getAllOrders();
        ListIterator<ClientOrder> iter = allUserOrder.listIterator();
        while (iter.hasNext()) {
            if (iter.next().isStatus()) {
                iter.remove();
            }
        }
        model.addAttribute("orderList", allUserOrder);
        return "submitOrder";
    }

    @PostMapping("/submitTheOrder/{orderId}")
    public RedirectView submitTheOrder(@PathVariable("orderId") Long orderId, RedirectAttributes redirAttrs) {
        System.out.println("order ID: " + orderId);
        adminService.confirmOrder(orderId);
        redirAttrs.addFlashAttribute("info", "Status zmieniony na zrealizowany(true)");
        return new RedirectView("/admin/submitTheOrder");
    }

    @GetMapping("/addBike")
    public String getAddBikePage(Model model) {
        model.addAttribute("bike", new Bike());
        return "addBike";
    }

    @PostMapping("/addBike")
    public RedirectView addBike(Bike bike, @RequestParam("file") MultipartFile file, RedirectAttributes redirAttrs) {
        if (file.isEmpty()) {
            redirAttrs.addFlashAttribute("info", "Coś z plikiem jest nie tak");
            return new RedirectView("/admin/addBike");
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bike.setImagePath("/images/" + fileName);
        adminService.addBike(bike);
        redirAttrs.addFlashAttribute("info", "Dodano rower!");
        return new RedirectView("/admin/addBike");
    }

    @GetMapping("/bikes")
    public String getBikesView(Model model) {
        List<Bike> bikes = bikeService.getAllBikes();
        if (model.containsAttribute("bikeList") == false)
            model.addAttribute("bikeList", bikes);

        return "adminBikes";
    }

    @GetMapping("/bikes/search")
    public ModelAndView searchBikes(@RequestParam("text") String text, RedirectAttributes redirAtt) {
        List<Bike> bikes = bikeService.findBikesByPhrase(text);
        ModelAndView mv = new ModelAndView("redirect:/admin/bikes");
        redirAtt.addFlashAttribute("bikeList", bikes);
        return mv;
    }

    @GetMapping("/orderDetails/{orderId}")
    public String getAdminHistoryDetailsPage(@PathVariable("orderId") Long orderId, Model model) {
        List<OrderDetails> orderDetailsByOrder = clientOrderService.getOrderDetailsByOrderId(orderId);
        model.addAttribute("orderDetail", orderDetailsByOrder);

        User userByOrderId = clientOrderService.getUserByOrderId(orderId);
        model.addAttribute("customerInfo", userByOrderId);

        return "adminHistoryDetails";
    }
}
