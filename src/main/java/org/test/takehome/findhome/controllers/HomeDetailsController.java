package org.test.takehome.findhome.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.test.takehome.findhome.model.HomeDetails;
import org.test.takehome.findhome.service.HomeDetailsService;

import java.util.List;

@RestController
@RequestMapping("/homedetails")
public class HomeDetailsController {

    @Autowired
    HomeDetailsService homeDetailsService;

    @PostMapping(value = "/highestValue",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getHighestValuedHome(@RequestBody List<String> homeIds){
        if(homeIds==null || homeIds.size()==0){
            return new ResponseEntity<String>("Home Ids is EMpty or null",HttpStatus.BAD_REQUEST);
        }
        List<HomeDetails> highestValuedHomes = homeDetailsService.getHighestValueHome(homeIds);
        if(highestValuedHomes!=null && highestValuedHomes.size()>0){
            return new ResponseEntity<>(highestValuedHomes.get(0).getOwner(),HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("Property Not Found",HttpStatus.OK);
        }

    }
}
