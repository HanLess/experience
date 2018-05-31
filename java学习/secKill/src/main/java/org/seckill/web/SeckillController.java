package org.seckill.web;


import com.mysql.cj.api.Session;
import org.seckill.dto.*;
import org.seckill.entry.Seckill;
import org.seckill.enums.SeckillState;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.seckill.service.impl.SeckillServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seckill")      // 模块
public class SeckillController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public void test(
            Link link,
            School school,
                     HttpServletRequest request
                     ){
//        System.out.println(list);
//        for(People o:list){
//            System.out.println(o);
//        }

        System.out.println(link);
        System.out.println(school);
        Enumeration a = request.getParameterNames();
        System.out.println(a);
    }



    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public List<Seckill> list(){
        List<Seckill> list = seckillService.getSeckillList();
        return list;
    }

    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    @ResponseBody
    public Seckill detail(
            Long seckillId
    )
    {

        if(seckillId == null){
            return null;
        }
        Seckill seckill = seckillService.getById(seckillId);
        if(seckill == null){
            return null;
        }
        return seckill;
    }

    @RequestMapping(value = "/exposer",method = RequestMethod.POST,produces = {
            "application/json"
    })
    @ResponseBody
    public SeckillResult<Exposer> exposer(Long seckillId){
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<>(true,exposer);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            result = new SeckillResult<>(false,e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/excutionWithProcedure",method = RequestMethod.POST,produces = {
            "application/json"
    })
    @ResponseBody
    public SeckillResult<SeckillExecution> excuteWith(Long seckillId,
                                                  String md5,
                                                  @CookieValue(value = "killPhone",required = false) Long phone){
//        可以采用springMVC的验证信息 springmvc valid
        if(phone == null){
            return new SeckillResult<>(false,"未登录");
        }
        SeckillResult<SeckillExecution> result;
        try{
            SeckillExecution seckillExecution = seckillService.executeWithProcedure(seckillId,phone,md5);
            result = new SeckillResult<>(true,seckillExecution);
        }catch (SeckillCloseException closeException){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillState.END);
            result = new SeckillResult<>(false,seckillExecution);
        }catch (RepeatKillException repeatException){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillState.REPEAT_KILL);
            result = new SeckillResult<>(false,seckillExecution);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            e.printStackTrace();
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillState.INNER_ERROR);
            result = new SeckillResult<>(false,seckillExecution);
        }
        return result;
    }

    @RequestMapping(value = "/excution",method = RequestMethod.POST,produces = {
            "application/json"
    })
    @ResponseBody
    public SeckillResult<SeckillExecution> excute(Long seckillId,
                                                  String md5,
                                                  @CookieValue(value = "killPhone",required = false) Long phone){
//        可以采用springMVC的验证信息 springmvc valid
        if(phone == null){
            return new SeckillResult<>(false,"未登录");
        }
        SeckillResult<SeckillExecution> result;
        try{
            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId,phone,md5);
            result = new SeckillResult<>(true,seckillExecution);
        }catch (SeckillCloseException closeException){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillState.END);
            result = new SeckillResult<>(false,seckillExecution);
        }catch (RepeatKillException repeatException){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillState.REPEAT_KILL);
            result = new SeckillResult<>(false,seckillExecution);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            e.printStackTrace();
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillState.INNER_ERROR);
            result = new SeckillResult<>(false,seckillExecution);
        }
        return result;
    }

    @RequestMapping(value = "/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        SeckillResult<Long> result;
        Long now = (new Date()).getTime();

        return new SeckillResult<>(true,now);
    }
}
