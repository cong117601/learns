package com.cgm.payModel.controller;

import com.cgm.payModel.config.WxPayConfig;
import com.cgm.payModel.entity.Product;
import com.cgm.payModel.service.ProductService;
import com.cgm.payModel.vo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.PrivateKey;
import java.util.Date;
import java.util.List;

@Api(tags = "商品管理")
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;


    @Autowired
    private WxPayConfig wxPayConfig;
    @ApiOperation("测试接口")
    @RequestMapping(value = "/t1", method = RequestMethod.GET)
    public R getT() {
        return R.ok().data("date", new Date());
    }


    @ApiOperation("查询产品")
    @GetMapping("/list")
    public R getProduct() {
        List<Product> list = productService.list();
        return R.ok().data("list", list);
    }

    @ApiOperation("test证书")
    @GetMapping("/test")
    public R wxPayConfig() {
        PrivateKey privateKey = wxPayConfig.gerPrivateKey(wxPayConfig.getPrivateKeyPath());
        return R.ok().data("appId", privateKey);
    }

}
