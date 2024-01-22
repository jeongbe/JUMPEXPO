package com.example.JumpExpo.Controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.UUID;

@Slf4j
@Controller
@CrossOrigin(origins = "http://your-smart-editor-client-url.com")
public class ImageController {

    @RequestMapping(value = "smarteditorMultiImageUpload")
    public void smarteditorMultiImageUpload(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 파일 정보를 저장할 변수
            String sFileInfo = "";

            // HTTP 헤더에서 파일명을 가져옵니다.
            String sFilename = request.getHeader("file-name");

            // 파일 확장자를 추출합니다.
            String sFilenameExt = sFilename.substring(sFilename.lastIndexOf(".") + 1);

            // 파일 확장자를 소문자로 변경합니다.
            sFilenameExt = sFilenameExt.toLowerCase();

            // 허용된 이미지 파일 확장자 배열
            String[] allowFileArr = {"jpg", "png", "bmp", "gif"};

            // 허용된 이미지 확장자인지 확인합니다.
            int nCnt = 0;
            for (String allowExt : allowFileArr) {
                if (sFilenameExt.equals(allowExt)) {
                    nCnt++;
                }
            }

            // 허용된 이미지가 아니라면 응답으로 "NOTALLOW_"와 함께 파일명을 전송합니다.
            if (nCnt == 0) {
                PrintWriter print = response.getWriter();
                print.print("NOTALLOW_" + sFilename);
                print.flush();
                print.close();
            } else {
                // 이미지를 저장할 디렉토리 경로 설정
                String link = "\\\\192.168.2.3\\images\\a";

                // 생성할 디렉토리 경로
                String directoryPath = link + File.separator;

                // 파일 생성 시간을 기반으로 한 고유한 파일명 생성
                String sRealFileNm = "";
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                String today = formatter.format(new java.util.Date());
                sRealFileNm = today + UUID.randomUUID().toString() + sFilename.substring(sFilename.lastIndexOf("."));
                String rlFileNm = directoryPath + sRealFileNm;

                // 생성할 디렉토리가 없다면 생성
                File directory = new File(directoryPath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // 서버에 파일 쓰기
                InputStream inputStream = request.getInputStream();
                File outputFile = new File(rlFileNm);

                try (OutputStream outputStream = new FileOutputStream(outputFile)) {
                    int numRead;
                    byte bytes[] = new byte[Integer.parseInt(request.getHeader("file-size"))];

                    // 파일을 읽어서 서버에 저장합니다.
                    while ((numRead = inputStream.read(bytes, 0, bytes.length)) != -1) {
                        outputStream.write(bytes, 0, numRead);
                    }
                }

                // 이미지 정보를 출력할 변수에 추가

                sFileInfo += "&bNewLine=true";
                String decodedFileName = URLDecoder.decode(sFilename, StandardCharsets.UTF_8.toString());
                sFileInfo += "&sFileName=" + URLEncoder.encode(decodedFileName, "UTF-8");

// 아파치 서버의 외부 URL로 변경
                String externalUrl = "http://192.168.2.3/images/a/" + URLEncoder.encode(sRealFileNm, "UTF-8");
                sFileInfo += "&sFileURL=" + externalUrl;



                // 로그에 생성된 이미지 파일 정보 출력
                log.info("Generated sFileName: {}", URLEncoder.encode(decodedFileName, "UTF-8"));
                log.info("Generated sFileURL: {}", rlFileNm);

                // 이미지 정보를 응답으로 전송
                PrintWriter printWriter = response.getWriter();
                printWriter.print(sFileInfo);
                printWriter.flush();
                printWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
