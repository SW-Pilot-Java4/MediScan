package com.ms.back.hospital.Infrastructure.repository;

import com.ms.back.hospital.Infrastructure.repository.entity.Hospital;
import com.ms.back.hospital.Infrastructure.repository.entity.QHospital;
import com.ms.back.hospital.domain.port.HospitalCustomRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import com.querydsl.core.BooleanBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
@RequiredArgsConstructor
public class HospitalCustomRepositoryImpl implements HospitalCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Hospital> searchByKeyword(String hname,String haddress, String hnum,String catcode) {
        QHospital hospital = QHospital.hospital;

        BooleanBuilder builder = new BooleanBuilder();

        //조건들 맵에 넣음
        Map<String, String> conditions = Map.of(
                "name", hname,
                "address",haddress,
                "callNumber",hnum,
                "categoryCode", catcode
        );

        conditions.forEach((key,value)->{
            if(value!=null&&!value.isBlank()){
                switch(key){
                    case "name" :
                            builder.and(hospital.name.containsIgnoreCase(value));
                            break;
                    case "address" :
                        builder.and(hospital.address.containsIgnoreCase(value));
                        break;
                    case "callNumber" :
                        builder.and(
                                Expressions
                                        .stringTemplate("function('replace',{0},'-','')",hospital.callNumber)
                                        .containsIgnoreCase(value));
                        break;
                    case "categoryCode" :
                        //todo: 문제 발생 부분 나중에 수정
                        if (isNumeric(value))
                            builder.and(hospital.categoryCode.eq(Long.valueOf(value)));

                        break;
                }
            }
        });


        return queryFactory
                .selectFrom(hospital)
                .where(builder)
                .fetch();
    }

    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;

        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
}

//        if (hname != null && !hname.isBlank()) {
//            builder.and(hospital.name.containsIgnoreCase(hname));
//        }
//        if (haddress != null && !haddress.isBlank()) {
//            builder.and(hospital.address.containsIgnoreCase(haddress));
//        }
//        if (hnum != null && !hnum.isBlank()) {
//            String sanitizedNum = hnum.replaceAll("-","");
//            builder.and(Expressions.stringTemplate(
//                    "function('replace', {0}, '-', '')", hospital.callNumber
//                    ).containsIgnoreCase(sanitizedNum)
//            );
//        }
