package com.management.auction.controllers.auction;

import com.management.auction.models.Criteria;
import com.management.auction.services.AuctionService;
import custom.springutils.exception.CustomException;
import custom.springutils.util.ControllerUtil;
import custom.springutils.util.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

import static custom.springutils.util.ControllerUtil.returnSuccess;

@RestController
@RequestMapping("/auctions")
public class AuctionFilterController{
    @Autowired
    private AuctionService service;
    @GetMapping("/filter")
    public ResponseEntity<SuccessResponse> filter(@RequestParam(required = false)String keyword, @RequestParam(required = false)@DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss") String startMinDate, @RequestParam(required = false)@DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")String startMaxDate, @RequestParam(required = false)@DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")String endMinDate, @RequestParam(required = false)@DateTimeFormat(pattern = "yyyy-mm-dd HH:mm:ss")String endMaxDate, @RequestParam(required = false)Long product, @RequestParam(required = false)Long category, @RequestParam(required = false)Double price, @RequestParam(required = false)Integer status) throws CustomException {
        Criteria criteria=new Criteria();
        criteria.setKeyword(keyword);
        if(startMinDate!=null){
            criteria.setStartMinDate(Timestamp.valueOf(startMinDate));
        }
        if(startMaxDate!=null){
            criteria.setStartMaxDate(Timestamp.valueOf(startMaxDate));
        }
        if(endMinDate!=null){
            criteria.setEndMinDate(Timestamp.valueOf(endMinDate));
        }
        if(endMaxDate!=null){
            criteria.setEndMaxDate(Timestamp.valueOf(endMaxDate));
        }
        criteria.setProduct(product);
        criteria.setCategory(category);
        criteria.setPrice(price);
        criteria.setStatus(status);
        return ControllerUtil.returnSuccess(this.service.findByCriteria(criteria), HttpStatus.OK);
    }
    @GetMapping("/{page}")
    public ResponseEntity<SuccessResponse> getByPage(@PathVariable(required = true) int page){
        return ControllerUtil.returnSuccess(this.service.finAll(page),HttpStatus.OK);
    }
    @GetMapping("/auctionnotfinish")
    public ResponseEntity<SuccessResponse>  AuctionNotFinish() {
        return ControllerUtil.returnSuccess(this.service.AuctionNotFinish(), HttpStatus.OK);
    }
    @GetMapping("/profile/{id}")
    public ResponseEntity<SuccessResponse> findById(@PathVariable("id") Long id) {
        return returnSuccess(this.service.findByIdView(id), HttpStatus.OK);
    }
}
