package com.cmd.exchange;

import com.cmd.exchange.common.response.CommonResponse;
import com.cmd.exchange.external.utils.SignUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.internal.bytebuddy.implementation.bytecode.Throw;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class TradeControllerTest {
    RestTemplate restTemplate;

//    private static String serverUrl = "http://127.0.0.1:8091";
//    private static String serverUrl = "http://127.0.0.1:8193";

    //    private static String serverUrl = "http://www.alcwallet.com/ea";
    //private static String serverUrl = "http://192.168.0.29:8193";
    private static String serverUrl = "http://127.0.0.1:8193";
    //api key
    private static String key = "3ddb8cf2292139111e4e6aa68d4d133d";

    private static String secret = "66640eb4-4b75-492c-a46a-5364eddddaaa";

    @Before
    public void setup() {
        restTemplate = new RestTemplate();
    }


    public void createTrade(String coinName,String mobile,String changeBalance) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();

        parameters.add("changeBalance", changeBalance);
        parameters.add("mobile", mobile);
        parameters.add("coinName", coinName);
        parameters.add("apiKey", key);
        parameters.add("timestamp", System.currentTimeMillis() / 1000l + "");
        parameters.add("sign", getSign(parameters));

        sendRequst(HttpMethod.POST, "/trades/change-balance", parameters);
    }

    @Test
    public void cancelTrade() {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();

        parameters.add("tradeId", "16979");
        parameters.add("apiKey", key);
        parameters.add("timestamp", System.currentTimeMillis() / 1000l + "");
        parameters.add("sign", getSign(parameters));

        sendRequst(HttpMethod.POST, "/trades/cancel", parameters);
    }

    @Test
    public void getOpenTrades() {

        List<NameValuePair> values = new ArrayList();
        values.add(new BasicNameValuePair("pageNo", "1"));
        values.add(new BasicNameValuePair("pageSize", "10"));
        values.add(new BasicNameValuePair("apiKey", key));
        values.add(new BasicNameValuePair("timestamp", System.currentTimeMillis() / 1000l + ""));
        values.add(new BasicNameValuePair("sign", getSign(values)));

        String path = URLEncodedUtils.format(values, Charset.defaultCharset());

        sendRequst(HttpMethod.GET, "/trades/current?" + path, null);
    }


    private void sendRequst(HttpMethod method, String url, MultiValueMap<String, String> parameters) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
                parameters, headers);

        ResponseEntity<CommonResponse> responseEntity = null;


        if (method == HttpMethod.GET) {
            responseEntity = restTemplate.getForEntity(serverUrl + url, CommonResponse.class);
        } else {
            responseEntity = restTemplate.postForEntity(serverUrl + url, request, CommonResponse.class);
        }

        assertEquals(responseEntity.getBody().getErrorMessage(), responseEntity.getBody().getStatusCode(), 0);

        System.out.println("response: " + responseEntity.getBody().getContent());
    }

    private static String getSign(MultiValueMap<String, String> parameters) {
        Map<String, String> maps = new HashMap<>();
        //sign 字段不参加签名
        for (String key : parameters.keySet()) {
            if (!key.equalsIgnoreCase("sign")) {
                maps.put(key, parameters.get(key).get(0));
            }
        }

        return SignUtil.getSignString("md5", secret, maps);
    }

    private static String getSign(List<NameValuePair> parameters) {
        Map<String, String> maps = new HashMap<>();
        //sign 字段不参加签名
        for (NameValuePair key : parameters) {
            if (!key.getName().equalsIgnoreCase("sign")) {
                maps.put(key.getName(), key.getValue());
            }
        }

        return SignUtil.getSignString("md5", secret, maps);
    }

    @Test
    public void getSign() {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        /*parameters.add("coinName", "BTC");
        parameters.add("settlementCurrency","USDT");
        parameters.add("type","BUY");
        parameters.add("priceType","LIMITED");
        parameters.add("amount","1");
        parameters.add("price","1");*/

        parameters.add("pageNo", "1");
        parameters.add("pageSize", "10");

        parameters.add("apiKey", "3ddb8cf2292139111e4e6aa68d4d133d");
        parameters.add("timestamp", "1530861209");

        System.out.println(getSign(parameters));
    }

    @Test
    public void inputExcel(){
        File excelFile = null;// Excel文件对象
        InputStream is = null;// 输入流对象
        String cellStr = null;// 单元格，最终按字符串处理
        StringBuffer msg = new StringBuffer();//处理信息
        Map<String,Object> iMap = new HashMap<String, Object>();//返回值
        //List<ClientInfo> list = null;//客户数据集合
       // List<ClientTextBean> textBeanList = null;//跟进信息


        try{


            excelFile = new File("F:\\inputExcel\\SPCB导入1.26.xlsx");
            is = new FileInputStream(excelFile);// 获取文件输入流
            XSSFWorkbook workbook2007 = new XSSFWorkbook(is);// 创建Excel

            int sheetcount = workbook2007.getNumberOfSheets();
            //list = new ArrayList<ClientInfo>();
            //textBeanList = new ArrayList<ClientTextBean>();
            //  循环工作表Sheet
            for (int k = 0; k < sheetcount; k++) {

                XSSFSheet sheet = workbook2007.getSheetAt(k);
                List<String> biaotou_list = new ArrayList<String>();
                // 开始循环遍历行，表头不处理，从1开始
                for (int i = 0; i <= sheet.getLastRowNum(); i++) {

                    //ClientInfo clientInfo = new ClientInfo();
                    XSSFRow row = sheet.getRow(i);// 获取行对象

                    if (row == null) {// 如果为空，不处理
                        continue;
                    }


                    String coinName = null;
                    String mobile = null;
                    String changeBalance = null;
                    // 循环遍历单元格
                    for (int j = 0; j < row.getLastCellNum(); j++) {

                        XSSFCell cell = row.getCell(j);// 获取单元格对象
                        //cell.setCellType(1);
                        if (cell == null) {// 单元格为空设置cellStr为空串
                            cellStr = "";

                        } else {// 其余按照字符串处理
                            cell.setCellType(CellType.STRING);
                            cellStr = cell.getStringCellValue();
                        }

                        if (i == 0) {

                            if(!StringUtils.isEmpty(cellStr)){

                                if(!cellStr.equals("币种") || !cellStr.equals("UK手机号") || !cellStr.equals("币总数量")){
                                    biaotou_list.add(cellStr.trim());
                                }else{
                                    throw new Exception("<span style=\"color: #b81900\">表头字段不存在：'"+cellStr+"'(第"+(i+1)+"行的第"+(j+1)+"单元格)</span></br>");
                                }

                            }
                            continue;

                        } else {

                            if (j >= biaotou_list.size()) {
                                continue;
                            }
                            if(biaotou_list.get(j).equals("币种")) {
                                coinName =  cellStr;

                            } else if (biaotou_list.get(j).equals("UK手机号")){
                                //手机号
                                if (cellStr.length() < 15) {
                                    mobile = cellStr.replaceAll("[^0-9]", "");
                                }
                            } else if (biaotou_list.get(j).equals("币总数量")) {
                                changeBalance = cellStr;
                            }

                        }

                    }//单元格循环结束

                    if(i>0){
                        boolean flse=Boolean.TRUE;
                        if(coinName == null) {
                            System.out.println("<span style=\"color: #b81900\">'币种'无效：(第"+(i+1)+"行)</span></br>");
                            flse = Boolean.FALSE;
                        }else if(mobile == null || mobile.length() != 11){
                            System.out.println("<span style=\"color: #b81900\">'手机号'长度不符："+mobile+"(第"+(i+1)+"行)</span></br>");
                            flse = Boolean.FALSE;
                        }

                        if(changeBalance==null){
                            System.out.println("<span style=\"color: #b81900\">'币总数量'无效：(第"+(i+1)+"行)</span></br>");
                            flse = Boolean.FALSE;
                        }


                        if(flse){
                            createTrade(coinName,mobile,changeBalance);
                           //
                        }

                    }
                }//行循环结束
            }//循环工作表Sheet循环结束

           // debugLog.info("调用  /background/UpClient/savefile  readFromXLSX2007 处理表格数据"+list.size());

        } catch (Exception e) {
        //serviceLog.info("调用 /background/UpClient/readFromXLSX2007 IO Error," + e.getMessage());
            e.printStackTrace();
        } finally {// 关闭文件流
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {

              //  serviceLog.info("调用 /background/UpClient/readFromXLSX2007 IO Error," + e.getMessage());
            }
        }
    }

    }

}
