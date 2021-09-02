package com.group.orders.controller;

import com.group.orders.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;*/
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller()
public class UploadController {
    private Logger LOGGER = LoggerFactory.getLogger(UploadController.class);

    private UploadService uploadService;

    public UploadController(@Autowired UploadService uploadService) {
        this.uploadService = uploadService;
    }
    @Operation(summary = "Upload csv file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully computed"),
            @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content)
    }
    )
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("status", true);
        return "login";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("status", true);
        return "index";
    }

    @Operation(summary = "Upload csv file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully computed"),
            @ApiResponse(responseCode = "400", description = "Invalid Request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Accessing the resource you were trying to reach is forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "The resource you were trying to reach is not found", content = @Content)
    }
    )
    @PostMapping(value = "/csv")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        LOGGER.debug("Received file");
        return uploadService.uploadFile(file, model);
    }

    @GetMapping(value = "/csv/{page}")
    public String getOrders(@PathVariable(name = "page") final int pageNumber, final Model model) {
        try {
            model.addAttribute("status", true);
            model.addAttribute("responseEntity", uploadService.getOrders(pageNumber));
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("message", "An error occurred while processing the CSV file.");
            model.addAttribute("status", false);
        }
        return "display";
    }

/*    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }*/
}
