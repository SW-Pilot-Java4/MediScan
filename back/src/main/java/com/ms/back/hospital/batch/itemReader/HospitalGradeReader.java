package com.ms.back.hospital.batch.itemReader;

import com.ms.back.hospital.batch.dto.HospitalGradeRegister;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class HospitalGradeReader{
    @Autowired
    private Environment env;

    @Bean
    public ItemReader<? extends HospitalGradeRegister> read() {
        String xml;
        try {
            xml = fetchFromAPI(); // OpenAPI에서 XML 문자열 가져오기
        } catch (Exception e) {
            log.info("Open API 데이터 불러오는 중 문제 발생");
            xml = null;
        }

        Resource resource = new ByteArrayResource(xml.getBytes(StandardCharsets.UTF_8));

        return new StaxEventItemReaderBuilder<HospitalGradeRegister>()
                .name("hospitalGradeXmlReader")
                .resource(resource)
                .addFragmentRootElements("item")  // 'items'가 아니라 'item'으로 변경
                .unmarshaller(itemUnmarshaller())
                .build();
    }

    public Unmarshaller itemUnmarshaller() {
        final Map<String, Class<?>> aliases = new HashMap<>();
        aliases.put("item", HospitalGradeRegister.class); // XML의 <item> 태그와 매핑
        aliases.put("ykiho", String.class);

        final XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        xStreamMarshaller.setAliases(aliases);

        // 보안 정책 때문에 필요하면 아래 허용 설정도 추가 가능
         XStream xStream = xStreamMarshaller.getXStream();

        xStream.allowTypes(new Class[]{HospitalGradeRegister.class});

        // xStream.addPermission(AnyTypePermission.ANY);
        xStream.ignoreUnknownElements(); //

        xStream.aliasField("ykiho", HospitalGradeRegister.class, "hospitalCode");

        xStream.addPermission(AnyTypePermission.ANY);  // 위험: 모든 클래스 허용
        return xStreamMarshaller;
    }

    private String fetchFromAPI() throws Exception {
        String serviceKey = env.getProperty("openapi.key.encoding");
//        TODO: 배포 전 모든 데이터를 읽어 오기 위해 수정 해야 함
//        Long numOfRows = 35962L;  // API를 통해 얻을 수 있는 데이터 수

        StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/B551182/hospAsmInfoService1/getHospAsmInfo1");
        urlBuilder.append("?serviceKey=").append(serviceKey);
        urlBuilder.append("&pageNo=1");
        urlBuilder.append("&numOfRows=1000");

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        String xml = sb.toString();
        System.out.println("[API 응답]: " + xml);

        return xml;
    }
}
