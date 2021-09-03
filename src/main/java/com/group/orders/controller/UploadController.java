package com.group.orders.controller;

import com.group.orders.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller()
public class UploadController {
    private Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    private UploadService uploadService;

    public UploadController(@Autowired UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("status", true);
        return "index";
    }

    @PostMapping(value = "/csv")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        LOGGER.debug("Received file");
        return uploadService.uploadFile(file, model);
    }

    @GetMapping(value = "/csv/{page}")
    public String getOrders(@PathVariable(name = "page") final int pageNumber, final Model model) {
        model.addAttribute("status", true);
        model.addAttribute("responseEntity", uploadService.getOrders(pageNumber));
        return "display";
    }
}
