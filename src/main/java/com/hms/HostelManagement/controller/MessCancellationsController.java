package com.hms.HostelManagement.controller;

import com.hms.HostelManagement.model.Hostel;
import com.hms.HostelManagement.model.MessCancellations;
import com.hms.HostelManagement.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MessCancellationsController extends BaseController{
    @Autowired
    private ToastService toastService;

    @Autowired
    private StudentService studentService;


    @Autowired
    private UserService userService;

    @Autowired
    private HostelService hostelService;

    @Autowired
    private MessCancellationsService messCancellationsService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    @GetMapping("/mess")
    public String allCancellations(Model model, HttpSession httpSession) {
        if(!isAuthenticated(httpSession)){
            return "redirect:/";
        }
        addDefaultAttributes(model, httpSession);
        model.addAttribute("cancellation", messCancellationsService.getAll());
        return "messCancellations/allMessCancellations";
    }
    @GetMapping("/mess/add")
    public String addMess(Model model, HttpSession session){
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        addDefaultAttributes(model, session);

        MessCancellations mess = new MessCancellations();

        model.addAttribute("messAdd", mess);
          String s;
        return    "messCancellations/addMessCancellations";

    }
    @PostMapping("/mess")
    public String PostAddMess(@ModelAttribute("messAdd") MessCancellations m, Model model, HttpSession session){
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        System.out.println(messCancellationsService);
        addDefaultAttributes(model,session);
       Date utildate=null;
       utildate=m.getDate();
       java.sql.Date sqlDate = new java.sql.Date(utildate.getTime());
        m.setDate(sqlDate);
         messCancellationsService.createMessCancellation(m);
        return "redirect:/mess";
    }
}
