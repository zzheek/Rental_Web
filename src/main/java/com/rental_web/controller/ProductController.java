package com.rental_web.controller;

import com.rental_web.dto.PageRequestDTO;
import com.rental_web.dto.PageResponseDTO;
import com.rental_web.dto.product.ProductDTO;
import com.rental_web.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Controller
@RequestMapping("/product")
@Log4j2
@RequiredArgsConstructor
public class ProductController {

    @Value("${com.rental_web.upload.path}")
    private String uploadPath;

    private final ProductService productService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        PageResponseDTO<ProductDTO> responseDTO = productService.list(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO",responseDTO);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/register")
    public void registerGET(){

    }

    @PostMapping("/register")
    public String registerPost(@Valid ProductDTO productDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        log.info("product POST register.......");

        if(bindingResult.hasErrors()) {
            log.info("has errors.......");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );
            return "redirect:/product/register";
        }

        log.info(productDTO);

        Long productnum  = productService.register(productDTO);

        redirectAttributes.addFlashAttribute("result", productnum);

        return "redirect:/product/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping({"/read", "/modify"})
    public void read(Long productnum, PageRequestDTO pageRequestDTO, Model model){

        ProductDTO productDTO = productService.readOne(productnum);

        log.info(productDTO);

        model.addAttribute("dto", productDTO);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/modify")
    public String modify( @Valid ProductDTO productDTO,
                          BindingResult bindingResult,
                          PageRequestDTO pageRequestDTO,
                          RedirectAttributes redirectAttributes){


        log.info("product modify post......." + productDTO);

        if(bindingResult.hasErrors()) {
            log.info("has errors.......");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors() );

            redirectAttributes.addAttribute("bno", productDTO.getProductnum());

            return "redirect:/product/modify?"+link;
        }

        productService.modify(productDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("bno", productDTO.getProductnum());

        return "redirect:/product/read";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/remove")
    public String remove(ProductDTO productDTO, RedirectAttributes redirectAttributes) {

        Long productnum  = productDTO.getProductnum();
        log.info("remove post.. " + productnum);

        productService.remove(productnum);

        //게시물이 삭제되었다면 첨부 파일 삭제
        log.info(productDTO.getFileNames());
        List<String> fileNames = productDTO.getFileNames();
        if(fileNames != null && fileNames.size() > 0){
            removeFiles(fileNames);
        }

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/product/list";

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
