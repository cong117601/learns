package com.cgm.payModel.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cgm.payModel.entity.Product;
import com.cgm.payModel.mapper.ProductMapper;
import com.cgm.payModel.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
