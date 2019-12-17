package com.savannah.controller;

import com.savannah.service.model.ItemDTO;
import org.springframework.web.bind.annotation.*;

/**
 * @author stalern
 * @date 2019/12/17~19:27
 */
@RestController
@RequestMapping("/item")
@CrossOrigin(allowCredentials="true", allowedHeaders = "*")
public class ItemController {

    @PostMapping("/postItem")
    public void postItem(@RequestBody ItemDTO itemDTO) {

    }
}
