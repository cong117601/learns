package com.cgm.payModel.facade;

import com.cgm.payModel.entity.PayBody;
import com.cgm.payModel.enums.PayType;
import com.cgm.payModel.enums.StrategyEnum;
import org.springframework.stereotype.Component;

/**
 * 提供策略门面
 */

//@Component
public class PayStrategyFacade {


//    public static Boolean pay(PayBody payBody) {
//        //获取策略枚举
//        StrategyEnum strategyEnum = getStrategyEnum(payBody.getType());
//        //获取策略对象
//        PayStrategy payStrategy = StrategyFactory.getPayStrategy(strategyEnum);
//
//
//        //从请求参数中 取得哪个支付方式，去内存中获取具体的支付
//
//        //生成策略上下文
//        PayContext payContext = new PayContext(payStrategy);
//        //进行付款
//        Boolean execute = payContext.execute(payBody);
//        return execute;
//    }
//
//    private static StrategyEnum getStrategyEnum(int type) {
//        switch (type) {
//            case 0:
//                return StrategyEnum.ZfbPayStrategy;
//            case 1:
//                return StrategyEnum.WxPayStrategy;
//            case 2:
//                return StrategyEnum.BankPayStrategy;
//            default:
//                return null;
//        }
//    }


}



