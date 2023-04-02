package com.wook.chap.web.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.*;

@Component
@Slf4j
public class UrlInspector {

    /**
     * URL 형식 검사와 URL 상태 코드 검사로 분류
     * 원래는 URL 형식 검사를 할 때 new URL(requestURI).toURI()를 생성하여 검사를 마친 후 다시 URL 상태 코드 검사에서 URL url = new URL(requestURI)를 진행하여 url을 생성함
     * 여기서 똑같은 과정을 두번 거치지 않게 하려고 함(new URL()을 만드는 과정에서 MalformedURLException 체크드 예외가 발생할 수 있기 때문에 예외 처리 해줘야함)
     * 그래서 URL 형식 검사를 통과한 URL을 URL 상태 코드에 넘겨줘서 위의 상황을 해결해주려고 하는데 URLInspector는
     * 스프링 빈이라 공유 변수를 사용해서 넘겨주기엔 멀티 쓰레드 환경에서 위험하여 ThreadLocal 변수를 사용하였음
     */

    public ThreadLocal<URL> url = new ThreadLocal<>();


    public boolean urlValidator(String requestURI) {

        // URL 형식 검사
        // URL 상태 코드 검사
        if (urlFormValidator(requestURI) && urlStatusValidator()) {
            url.remove();
            return true;
        }

        url.remove();
        return false;

    }

    private boolean urlStatusValidator() {
        URL requestURL = this.url.get();
        try {
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
        } catch (IOException ie) {
            log.debug("URL 검사 중 IOException 발생");
        }
        return false;

    }


    private boolean urlFormValidator(String requestURI) {
        try {
            URL requestURL = new URL(requestURI);
            this.url.set(requestURL);
            requestURL.toURI();
            log.debug("URL 형식 검사 결과 : valid");
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
        }
    }

}
