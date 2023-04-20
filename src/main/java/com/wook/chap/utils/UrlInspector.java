package com.wook.chap.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.*;

@Component
@Slf4j
public class UrlInspector {



    public boolean urlValidator(String requestURI) {

        try {
            URL requestURL = new URL(requestURI);

            requestURL.toURI();
            log.debug("URL 형식 검사 결과 : valid");

            HttpsURLConnection con = (HttpsURLConnection) requestURL.openConnection();
            //현재 노트북의 User-Agent 정보 세팅
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36");
            con.setRequestProperty("referer", requestURL.getProtocol()+"://"+requestURL.getHost()+"/");
            con.setDoOutput(true);
            HttpURLConnection exitCode = (HttpURLConnection) con;

            log.debug("ResponseCode RequestProperties : {}", exitCode.getRequestProperties());
            log.debug("ResponseCode : {}", exitCode.getResponseCode());
            log.debug("ResponseCode Permission : {}", exitCode.getPermission());
            log.debug("ResponseCode ResponseMessage : {}", exitCode.getResponseMessage());
            log.debug("ResponseCode RequestMethod : {}", exitCode.getRequestMethod());

            if (!(exitCode.getResponseCode() >= 200 && exitCode.getResponseCode() < 300)) {
                log.debug("상태 코드 이상");
                return false;
            }


            return true;

        } catch (URISyntaxException exception) {
            /**
             * Checked exception thrown to indicate that a string could not be parsed as a URI reference.
             * URI 참조에 해당하는 문자값을 읽어들이지 못해서 던져진 체크드 예외
             */
            log.debug("URL 형식 검사 결과 : invalid");
            return false;
        } catch (MalformedURLException exception) {
            /**
             * Thrown to indicate that a malformed URL has occurred. Either no legal protocol
             * could be found in a specification string or the string could not be parsed.
             * 정해진 protocol이 아니거나 parsing 되지 않은 문자가 확인될 때 던져진 예외
             */
            log.debug("URL 형식 검사 결과 : invalid");
            return false;
        } catch (IOException ie) {
            log.debug("URL 검사 중 IOException 발생");
            return false;
        }






    }


}
