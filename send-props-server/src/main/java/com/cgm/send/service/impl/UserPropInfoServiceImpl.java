package com.cgm.send.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cgm.send.entiy.UserPropInfo;
import com.cgm.send.mapper.UserPropInfoMapper;
import com.cgm.send.service.UserPropInfoService;
import org.springframework.stereotype.Service;

@Service
public class UserPropInfoServiceImpl extends ServiceImpl<UserPropInfoMapper, UserPropInfo> implements UserPropInfoService {

}
