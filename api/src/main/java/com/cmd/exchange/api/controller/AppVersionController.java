package com.cmd.exchange.api.controller;

import com.cmd.exchange.common.model.AppVersion;
import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.common.vo.ArticleVo;
import com.cmd.exchange.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@Api(tags = "app升级接口")
@RestController
@RequestMapping("/app-version")
public class AppVersionController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "获取升级版本号")
    @GetMapping(value = "")
    public CommonResponse<AppVersion> getArticleByLocale(
            @ApiParam(value = "platform:ios,android", required = true) @RequestParam String platform) {
        return new CommonResponse(userService.getAppVersion(platform));
    }

    //@GetMapping(value = "update")
    public static String update(@RequestParam String command,
                                @RequestParam(required = false) String outFile) throws Exception {
        String returnString = "";
        Process pro = null;
        Runtime runTime = Runtime.getRuntime();
        if (runTime == null) {
            System.err.println("Create runtime false!");
        }
        try {
            pro = runTime.exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            PrintWriter output = new PrintWriter(new OutputStreamWriter(pro.getOutputStream()));
            FileOutputStream fileOutputStream = null;
            if (outFile != null && outFile.trim().length() > 0) {
                fileOutputStream = new FileOutputStream(outFile);
            }
            String line;
            while ((line = input.readLine()) != null) {
                returnString = returnString + line + "\n";
                if (outFile != null) {
                    fileOutputStream.write(line.getBytes("utf8"));
                    fileOutputStream.write("\n".getBytes("utf8"));
                }
            }
            input.close();
            output.close();
            pro.destroy();
        } catch (IOException ex) {
            StringWriter stringWriter = new StringWriter();
            PrintWriter output = new PrintWriter(stringWriter);
            ex.printStackTrace(output);
            returnString = stringWriter.toString();
            output.close();
        }
        return returnString;
    }
}
