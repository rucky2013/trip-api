package com.ulplanet.trip.controller;

import com.alibaba.fastjson.JSON;
import com.ulplanet.trip.bean.PhoneFeedback;
import com.ulplanet.trip.constant.Constants;
import com.ulplanet.trip.service.PhoneFeedbackService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by makun on 2015/12/15.
 */
@RestController
@RequestMapping(value = "/feedback")
public class PhoneFeedbackController {
    @Resource
    private PhoneFeedbackService phoneFeedbackService;

    @RequestMapping(value = "/getPhoneFunction", method = RequestMethod.GET)
         public Map<String, Object> getPhoneFunction() {
        return this.phoneFeedbackService.getPhoneFunctions();
    }

    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    public Map<String, Object> feedback(List<PhoneFeedback> list) {
        return this.phoneFeedbackService.savePhoneFeedback(list);
    }

    @RequestMapping(value = "/html", method = RequestMethod.GET)
    @ResponseBody
    public Object getPhoneFunctionHtml(HttpServletResponse response) {
        List<PhoneFeedback> list = (List<PhoneFeedback>) phoneFeedbackService.getPhoneFunctions().get(Constants.RETURN_FIELD_DATA);
        try {
            OutputStream out = response.getOutputStream();
            PrintWriter pw=new PrintWriter(new BufferedWriter(new OutputStreamWriter(out)));
            pw.println("<!DOCTYPE HTML>");
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gbk\" />");
            pw.println("<title>意见反馈</title>");
            pw.println("<style type=\"text/css\"> .tr{height:80px;} .title{width:200px} .options{width:90px} textarea{width:80%;height:70px} button { display: inline-block; outline: none; cursor: pointer; text-align: center; text-decoration: none; font: 14px/100% Arial, Helvetica, sans-serif; padding: .5em 2em .55em; text-shadow: 0 1px 1px rgba(0,0,0,.3); -webkit-border-radius: .5em; -moz-border-radius: .5em; border-radius: .5em; -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.2); -moz-box-shadow: 0 1px 2px rgba(0,0,0,.2); box-shadow: 0 1px 2px rgba(0,0,0,.2);  } button:hover { text-decoration: none; } button:active { position: relative; top: 1px; } </style> ");
            pw.println("</head>");
            pw.println("<body style=\"background-color:#DEDDDD\">");
            pw.println("<div style=\"margin:5px 20%;padding-bottom:100%;background-color:white\">");
            pw.println("<table style=\"margin:auto;width:100%\">");
            pw.println("<thead><th colspan=4>意见反馈</th></thead>");
            pw.println("<tr><td colspan=4 style=\"font-size: 5px;color: #8E8D8D;\">请您对我们的功能进行评价：</td></tr>");
            /**迭代选项，暂时只有单选和文本**/
            for(PhoneFeedback p : list){
                if(p.getType()==1){
                    pw.println("<tr class=\"tr\">");
                    pw.print("<td class=\"title\">");
                    pw.print(p.getName()+"</td>");
                    pw.print("<td class=\"options\"><input type=\"radio\" name=\""+ p.getId() +"\" value=3/>喜欢</td>");
                    pw.print("<td class=\"options\"><input type=\"radio\" name=\""+ p.getId() +"\" value=2/>一般</td>");
                    pw.println("<td class=\"options\"><input type=\"radio\" name=\"" + p.getId() + "\" value=1/>不喜欢</td>");
                    pw.println("</tr>");
                }else if(p.getType()==3){
                    pw.println("<tr class=\"tr\">");
                    pw.print("<td class=\"title\">");
                    pw.print(p.getName()+"</td>");
                    pw.print("<td class=\"options\" colspan=3>");
                    pw.print("<textarea name=\""+ p.getId() +"\"></textarea>");
                    pw.println("</td>");
                    pw.println("</tr>");
                }
            }
            pw.println("<tr><td><button>提&nbsp;&nbsp;&nbsp;交</button></td></tr>");
            pw.println("</table>");
            pw.println("</div>");
            pw.println("</body>");
            pw.println("</html>");
            out.flush();
            out.close();
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
