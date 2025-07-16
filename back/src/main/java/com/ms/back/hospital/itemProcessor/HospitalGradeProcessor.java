package com.ms.back.hospital.itemProcessor;

import com.ms.back.hospital.entity.Hospital;
import com.ms.back.hospital.entity.HospitalGrade;
import com.ms.back.hospital.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HospitalGradeProcessor implements ItemProcessor<HospitalGrade, HospitalGrade> {
    private final HospitalRepository hospitalRepository;

    @Override
    public HospitalGrade process(HospitalGrade item) throws Exception {
        item.setHospital(validationData(item.getHospitalCode()));
//        item.setAsmGrd01(item.getAsmGrd01() == null ? "10" : item.getAsmGrd01());
//        item.setAsmGrd02(item.getAsmGrd02() == null ? "10" : item.getAsmGrd02());
//        item.setAsmGrd03(item.getAsmGrd03() == null ? "10" : item.getAsmGrd03());
//        item.setAsmGrd04(item.getAsmGrd04() == null ? "10" : item.getAsmGrd04());
//        item.setAsmGrd05(item.getAsmGrd05() == null ? "10" : item.getAsmGrd05());
//        item.setAsmGrd06(item.getAsmGrd06() == null ? "10" : item.getAsmGrd06());
//        item.setAsmGrd07(item.getAsmGrd07() == null ? "10" : item.getAsmGrd07());
//        item.setAsmGrd08(item.getAsmGrd08() == null ? "10" : item.getAsmGrd08());
//        item.setAsmGrd09(item.getAsmGrd09() == null ? "10" : item.getAsmGrd09());
//        item.setAsmGrd10(item.getAsmGrd10() == null ? "10" : item.getAsmGrd10());
//        item.setAsmGrd11(item.getAsmGrd11() == null ? "10" : item.getAsmGrd11());
//        item.setAsmGrd12(item.getAsmGrd12() == null ? "10" : item.getAsmGrd12());
//        item.setAsmGrd13(item.getAsmGrd13() == null ? "10" : item.getAsmGrd13());
//        item.setAsmGrd14(item.getAsmGrd14() == null ? "10" : item.getAsmGrd14());
//        item.setAsmGrd15(item.getAsmGrd15() == null ? "10" : item.getAsmGrd15());
//        item.setAsmGrd16(item.getAsmGrd16() == null ? "10" : item.getAsmGrd16());
//        item.setAsmGrd17(item.getAsmGrd17() == null ? "10" : item.getAsmGrd17());
//        item.setAsmGrd18(item.getAsmGrd18() == null ? "10" : item.getAsmGrd18());
//        item.setAsmGrd19(item.getAsmGrd19() == null ? "10" : item.getAsmGrd19());
//        item.setAsmGrd20(item.getAsmGrd20() == null ? "10" : item.getAsmGrd20());
//        item.setAsmGrd21(item.getAsmGrd21() == null ? "10" : item.getAsmGrd21());
//        item.setAsmGrd22(item.getAsmGrd22() == null ? "10" : item.getAsmGrd22());
//        item.setAsmGrd23(item.getAsmGrd23() == null ? "10" : item.getAsmGrd23());

        return item;
    }

    private Hospital validationData(String hospitalCode) {
        Optional<Hospital> hospital = hospitalRepository.findByHospitalCode(hospitalCode);
        return hospital.orElseThrow(RuntimeException :: new);
    }
}
