package com.cmd.exchange.api;

import com.cmd.exchange.common.constants.ErrorCode;
import com.cmd.exchange.common.mapper.TransferAgentsMapper;
import com.cmd.exchange.common.mapper.TransferUsersMapper;
import com.cmd.exchange.common.model.TransferAgents;
import com.cmd.exchange.common.model.TransferUsers;
import com.cmd.exchange.common.model.User;
import com.cmd.exchange.common.utils.Assert;
import com.cmd.exchange.common.utils.EncryptionUtil;
import com.cmd.exchange.service.UserRecommendService;
import com.cmd.exchange.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private TransferUsersMapper transferUsersMapper;
    @Autowired
    private TransferAgentsMapper transferAgentsMapper;

    @Autowired
    private UserRecommendService userRecommendService;

    @Test
    public void contextLoads() {
		/*String payPassword = EncryptionUtil.MD5("a11111");
		System.out.println(""+payPassword);
		payPassword = "1679091c5a880faf6fb5e6087eb1b2dc";
		User user =userService.getUserByUserId(81);
		Assert.check(!EncryptionUtil.check(payPassword, user.getPayPassword(), user.getPaySalt()), ErrorCode.ERR_USER_PASSWORD_ERROR);
		System.out.println("00000");*/

        userRecommendService.intertUserRecommend("00000000013","00000000014","13111111111");
       // userRecommendService.userRecInit();


    }
    @Test
    public void transferUsers()
    {
        try{


            List<TransferUsers> oneInfos = transferUsersMapper.getInfo(new TransferUsers().setIstransfer(0));
            if(null == oneInfos)
            {
                System.out.println("已经同步完成");
                return;
            }
                for(TransferUsers oneInfo:oneInfos)
                {
                    if(!StringUtils.isNumeric(oneInfo.getMobile()) || oneInfo.getMobile().length() < 11)
                    {
                        oneInfo.setIstransfer(2);
                        int mod = transferUsersMapper.mod(oneInfo);

                        continue;
                    }

                    User user = new User();
                    user.setMobile(oneInfo.getMobile());
                    user.setPassword(oneInfo.getPassword());
                    user.setAreaCode("86");
                    String email =oneInfo.getEmail();
                    if(StringUtils.isEmpty(email)){
                        email =  oneInfo.getName()+"@"+oneInfo.getIdentity();
                    }

                    user.setEmail(email);
                    user.setUserName(oneInfo.getMobile());
                    user.setUserType(oneInfo.getProprieter());
                    user.setStatus(oneInfo.getStatus()==1 ? 0:1);
                    //user.setRealName()
                    String inviteCode = null;



                    TransferAgents transferAgents = transferAgentsMapper.getOneInfo(new TransferAgents().setIstransfer(0).setUserId(oneInfo.getId()));
                    Integer p1 = 0;

                    if(null != transferAgents && transferAgents.getP1() != 0)
                    {
                        p1 = transferAgents.getP1();

                        TransferUsers puser = transferUsersMapper.getOneInfo(new TransferUsers().setIstransfer(1).setId(transferAgents.getP1()));
                        // boolean flse = true;
                        int pNum = 0;
                        if(null == puser )//查找最终推荐人
                        {
                            while (true)
                            {
                                if(pNum>9){

                                    System.out.println("未找到最终推荐人");
                                    break;//按照九级推荐人
                                }

                                puser = transferUsersMapper.getOneInfo(new TransferUsers().setId(p1));//查找推荐人

                                if(null == puser){
                                    System.out.println("找不到推荐人");
                                    break;
                                }

                                transferAgents = transferAgentsMapper.getOneInfo(new TransferAgents().setIstransfer(0).setUserId(puser.getId()));

                                if(null==transferAgents)break;

                                p1 = transferAgents.getP1();//取父ID
                                if(p1 == 0)break;

                                puser = transferUsersMapper.getOneInfo(new TransferUsers().setIstransfer(1).setId(p1));

                                if(null != puser)break;

                                pNum ++;
                            }
                        }

                        if(null != puser && p1 !=0)
                        {

                            User userByMobile = userService.getUserByMobile(puser.getMobile());

                            inviteCode = userByMobile.getInviteCode();
                        }

                    }



                    userService.register(user,inviteCode,null,user.getMobile());


                    oneInfo.setIstransfer(1);
                    transferUsersMapper.mod(oneInfo);


                    System.out.println("oneInfo:"+oneInfo.toString());
                }



        }catch (Exception e){
            e.printStackTrace();

        }
    }
    @Test
    public void synTransfer(){

        while (true)
        {
            try {
                Thread.sleep(1000);
                transferUsers();

            } catch (Throwable th) {
               th.printStackTrace();
            }
        }

    }


}
