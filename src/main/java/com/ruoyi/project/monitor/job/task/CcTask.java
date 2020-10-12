package com.ruoyi.project.monitor.job.task;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("CcTask")
public class CcTask
{
    public static String getLeftTime(String date){
        Date now = new Date();
        Date d = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            d = sdf.parse(date);
        } catch (ParseException pe) {
            System.out.println(pe.getMessage());
        }
        Long dateTime =d.getTime() -now.getTime() ;
        Long day = dateTime / (1000 * 60 * 60 * 24) ;
        dateTime = dateTime - day * 1000*60*60*24 ;
        Long hours = dateTime / (60 * 60 * 1000) ;
        dateTime = dateTime - hours * 60 *60 * 1000 ;

        Long minutes = dateTime / (60 * 1000) ;
        dateTime = dateTime - minutes * 60 * 1000 ;
        Long seconds = dateTime / (1000) ;
        return  day  +"天"+ hours +"时"+ minutes + "分"+ seconds+"秒"  ;
    }
    public  void sendMessage() {
        String date = "2019-10-13 9:00:00";
        date = getLeftTime(date) ;
        Date d = new Date();
        System.out.println(date);
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "testLTAI09qBnhkPyqU4", "testVnNFZDdTmjjFTvK2PoS0lu0zggmkyp");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "15556669630,15555503256");
        request.putQueryParameter("SignName", "zoom");
        String templateCode = "SMS_172596282";
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", "{\"name\":\"薛盼盼\",\"cc\":\""+date+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
