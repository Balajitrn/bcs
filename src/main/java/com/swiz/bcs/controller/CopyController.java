package com.swiz.bcs.controller;

import com.swiz.bcs.dto.CopyDTO;
import com.swiz.bcs.entity.Copy;
import com.swiz.bcs.service.CopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/copies")
public class CopyController {

    @Autowired
    private CopyService copyService;

    @PostMapping
    public Copy createCopy(@RequestBody CopyDTO copy) {
        return copyService.saveCopy(copy);
    }

    @GetMapping
    public List<Copy> getAllCopies() {
        return copyService.findAllCopies();
    }

    @GetMapping("/{id}")
    public Copy getCopyById(@PathVariable Long id) {
        return copyService.findCopyById(id);
    }

    // Other endpoints related to copies
}

