package com.rental_web.controller;

import com.rental_web.dto.*;
import com.rental_web.service.RenboardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/renboard")
@Log4j2
@RequiredArgsConstructor
public class RenboardController {

    @Value("${com.rental_web.upload.path}")
    private String uploadPath;

    private final RenboardService renboardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        //PageResponseDTO<RenboardDTO> responseDTO = renboardService.list(pageRequestDTO);

        PageResponseDTO<RenboardListAllDTO> responseDTO =
                renboardService.listWithAll(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);
    }

    //    @PreAuthorize("hasRole('USER')")
    @GetMapping("/register")
    public void registerGET() {

    }

    @PostMapping("/register")
    public String registerPost(@Valid RenboardDTO renboardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        log.info("renboard POST register.......");

        if(bindingResult.hasErrors()) {
            log.info("has errors.......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );
            return "redirect:/renboard/register";
        }

        log.info(renboardDTO);

        Long rennum  = renboardService.register(renboardDTO);

        redirectAttributes.addFlashAttribute("result", rennum);



        return "redirect:/renboard/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping({"/read", "modify"})
    public void read(Long rennum, PageRequestDTO pageRequestDTO, Model model){

        RenboardDTO renboardDTO = renboardService.readOne(rennum);

        log.info(renboardDTO);

        model.addAttribute("dto", renboardDTO);

    }

    @PreAuthorize("principal.username == #renboardDTO.renwriter")
    @PostMapping("/modify")
    public String modify( @Valid RenboardDTO renboardDTO,
                          BindingResult bindingResult,
                          PageRequestDTO pageRequestDTO,
                          RedirectAttributes redirectAttributes){


        log.info("renboard modify post......." + renboardDTO);

        if(bindingResult.hasErrors()) {
            log.info("has errors.......");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );

            redirectAttributes.addAttribute("rennum", renboardDTO.getRennum());

            return "redirect:/renboard/modify?"+link;
        }

        renboardService.modify(renboardDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("rennum", renboardDTO.getRennum());

        return "redirect:/renboard/read";
    }

    @PreAuthorize("principal.username == #renboardDTO.renwriter")
    @PostMapping("/remove")
    public String remove(RenboardDTO renboardDTO, RedirectAttributes redirectAttributes) {

        Long rennum = renboardDTO.getRennum();
        log.info("remove post.. " + rennum);

        renboardService.remove(rennum);

        // 게시물이 DB에서 삭제되었다면 첨부파일 삭제
        List<String> fileNames = renboardDTO.getFileNames();
        if (fileNames != null && fileNames.size() > 0) {
            removeFiles(fileNames);
        }

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/renboard/list";

    }

    public void removeFiles(List<String> files){

        for (String fileName:files) {

            Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
            String resourceName = resource.getFilename();


            try {
                String contentType = Files.probeContentType(resource.getFile().toPath());
                resource.getFile().delete();

                //섬네일이 존재한다면
                if (contentType.startsWith("image")) {
                    File thumbnailFile = new File(uploadPath + File.separator + "s_" + fileName);
                    thumbnailFile.delete();
                }

            } catch (Exception e) {
                log.error(e.getMessage());
            }

        }//end for
    }
}
