package self.IEEE754;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

public class Test {
    private static final String str16[] = { "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000",
            "1001", "1010", "1011", "1100", "1101", "1110", "1111" };

    public static void main(String[] args) {
        double d1;
        String str1;

        BigDecimal bd1 = new BigDecimal("1");
        BigDecimal bd2 = new BigDecimal("2");
        bd2 = bd2.pow(1022);
        bd1 = bd1.divide(bd2);
        System.out.printf("最小正规格化浮点数的值为%s\n", bd1.toPlainString());
        // 最小正规格化浮点数 pow(2, -1022) 它的IEEE754编码是0010000000000000
        d1 = bd1.doubleValue();
        str1 = doubleToIEEE754Hex(d1);
        System.out.printf("最小正规格化数的IEEE754编码为%s\n", str1); // 0010000000000000

        str1 = "3FE0000000000000"; // 0.5的IEEE754编码
        d1 = IEEE754HexToDouble(str1);
        System.out.println("IEEE754编码为3FE0000000000000的浮点数是" + d1); // 0.5

        str1 = doubleToIEEE754Hex(d1);
        System.out.println(d1 + "的IEEE754编码是" + str1); // 打印3FE0000000000000 //是0.5的IEEE754编码

        str1 = "BFE0000000000000"; // -0.5的IEEE754编码
        String str2;
        str2 = IEEE754HexToExactValueString(str1);
        System.out.printf("IEEE754编码为BFE0000000000000的浮点数是%s\n", str2);
        // 打印-0.5 是BFE0000000000000所对应的浮点数

        bd1 = new BigDecimal(str2); // bd1为-0.5

        str1 = "BFE0000000000001"; // -0.50000000000000011102230246251565404236316680908203125的IEEE754编码
        str2 = IEEE754HexToExactValueString(str1);
        System.out.printf("IEEE754编码为BFE0000000000001的浮点数为%s\n", str2);
        // 打印-0.50000000000000011102230246251565404236316680908203125
        // 它的IEEE754编码是BFE0000000000001

        bd2 = new BigDecimal(str2);
        // -0.50000000000000011102230246251565404236316680908203125
        // 它的IEEE754编码是BFE0000000000001
        BigDecimal bd3;
        bd3 = bd1.add(bd2).divide(new BigDecimal("2")); // ((BFE0000000000000 + BFE0000000000001) / 2) 的精确值
        System.out.printf("((IEEE754编码为BFE0000000000000的浮点数 + IEEE754编码为BFE0000000000001的浮点数) / 2) 的精确值为%s\n",
                bd3.toPlainString());
        // 打印-0.500000000000000055511151231257827021181583404541015625
        // 是 ((BFE0000000000000 + BFE0000000000001) / 2) 的精确值

        str1 = "BFE0000000000002"; // -0.5000000000000002220446049250313080847263336181640625的IEEE754编码
        str2 = IEEE754HexToExactValueString(str1);
        System.out.printf("IEEE754编码为BFE0000000000002的浮点数为%s\n", str2);
        // -0.5000000000000002220446049250313080847263336181640625

        BigDecimal bd4;
        bd4 = new BigDecimal(str2);
        // -0.5000000000000002220446049250313080847263336181640625
        // 它的IEEE754编码为BFE0000000000002
        BigDecimal bd5;
        bd5 = bd2.add(bd4).divide(new BigDecimal("2")); // ((BFE0000000000001 + BFE0000000000002) / 2) 的精确值
        System.out.printf("((IEEE754编码为BFE0000000000001的浮点数 + IEEE754编码为BFE0000000000002的浮点数) / 2) 的精确值为 %s\n",
                bd5.toPlainString());
        // 打印-0.500000000000000166533453693773481063544750213623046875
        // 是 ((BFE0000000000001 + BFE0000000000002) / 2) 的精确值

        d1 = -0.500000000000000055511151231257827021181583404541015625;
        // ((BFE0000000000000 + BFE0000000000001) / 2) 的精确值
        str1 = doubleToIEEE754Hex(d1);
        System.out.printf("((IEEE754编码为BFE0000000000000的浮点数 + IEEE754编码为BFE0000000000001的浮点数) / 2) 的精确值的IEEE754编码为%s\n",
                str1);
        // 打印BFE0000000000000
        // 是 ((BFE0000000000000 + BFE0000000000001) / 2) 的精确值舍入后的编码

        d1 = -0.500000000000000055511151231257827021181583404541015624;
        // BFE0000000000000 < d1 < ((BFE0000000000000 + BFE0000000000001) / 2) 的精确值
        str1 = doubleToIEEE754Hex(d1);
        System.out.printf(
                "大于IEEE754编码为BFE0000000000000的浮点数且小于((IEEE754编码为BFE0000000000000的浮点数且 + IEEE754编码为BFE0000000000001的浮点数) / 2) 的精确值的浮点数的IEEE754编码为%s\n",
                str1);
        // 打印BFE0000000000000
        // 是d1舍入后的编码
        // BFE0000000000000 < d1 < ((BFE0000000000000 + BFE0000000000001) / 2) 的精确值

        d1 = -0.500000000000000055511151231257827021181583404541015625001;
        // ((BFE0000000000000 + BFE0000000000001) / 2) 的精确值 < d1 < BFE0000000000001
        str1 = doubleToIEEE754Hex(d1);
        System.out.printf(
                "大于((IEEE754编码为BFE0000000000000的浮点数 + IEEE754编码为BFE0000000000001的浮点数) / 2) 的精确值且小于EEE754编码为BFE0000000000001的浮点数的浮点数的IEEE754编码为%s\n",
                str1);
        // 打印BFE0000000000001
        // 是d1舍入后的编码
        // ((BFE0000000000000 + BFE0000000000001) / 2) 的精确值 < d1 < BFE0000000000001

        d1 = -0.500000000000000166533453693773481063544750213623046875;
        // ((BFE0000000000001 + BFE0000000000002) / 2) 的精确值
        str1 = doubleToIEEE754Hex(d1);
        System.out.printf("((IEEE754编码为BFE0000000000001的浮点数 + IEEE754编码为BFE0000000000002的浮点数) / 2) 的精确值的IEEE754编码为%s\n",
                str1);
        // 打印BFE0000000000002
        // 是 ((BFE0000000000001 + BFE0000000000002) / 2) 的精确值舍入后的编码

        d1 = -0.500000000000000166533453693773481063544750213623046874;
        // BFE0000000000001 < d1 < ((BFE0000000000001 + BFE0000000000002) / 2) 的精确值
        str1 = doubleToIEEE754Hex(d1);
        System.out.printf(
                "大于IEEE754编码为BFE0000000000001的浮点数且小于((IEEE754编码为BFE0000000000001的浮点数且 + IEEE754编码为BFE0000000000002的浮点数) / 2) 的精确值的浮点数的IEEE754编码为%s\n",
                str1);
        // 打印BFE0000000000001
        // 是d1舍入后的编码
        // BFE0000000000001 < d1 < ((BFE0000000000001 + BFE0000000000002) / 2) 的精确值

        d1 = -0.500000000000000166533453693773481063544750213623046875001;
        // ((BFE0000000000001 + BFE0000000000002) / 2) 的精确值 < d1 < BFE0000000000002
        str1 = doubleToIEEE754Hex(d1);
        System.out.printf(
                "大于((IEEE754编码为BFE0000000000001的浮点数 + IEEE754编码为BFE0000000000002的浮点数) / 2) 的精确值且小于EEE754编码为BFE0000000000002的浮点数的浮点数的IEEE754编码为%s\n",
                str1);
        // 打印BFE0000000000002
        // 是d1舍入后的编码
        // ((BFE0000000000001 + BFE0000000000002) / 2) 的精确值 < d1 < BFE0000000000002

        BigDecimal bd6;
        bd6 = (new BigDecimal("1").divide(new BigDecimal("2")).pow(1022))
                .subtract(new BigDecimal("1").divide(new BigDecimal("2")).pow(1074));
        // 最大正非规格化数 pow(2, -1022) - pow(2, -1074) 它的IEEE754编码是000FFFFFFFFFFFFF

        System.out.printf("最大正非规格化数为%s\n", bd6.toPlainString());
        // 打印最大正非规格化数 (pow(2, -1022) - pow(2, -1074)) 的精确值

        BigDecimal bd7;
        bd7 = new BigDecimal("1").divide(new BigDecimal("2")).pow(1022);
        // 最小正规格化数 pow(2, -1022) 它的IEEE754编码是0010000000000000
        System.out.printf("最小正规格化数为%s\n", bd7.toPlainString());
        // 打印最小正规格化数 pow(2, -1022) 的精确值

        bd1 = bd6.add(bd7).divide(new BigDecimal("2"));
        // 最大正非规格化数与最小正规格化数之和除以2
        System.out.printf("((最大正非规格化数 + 最小正规格化数) / 2)的精确值为%s\n", bd1.toPlainString());
        // 最大正非规格化数与最小正规格化数之和除以2的精确值

        d1 = 0.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000222507385850720113605740979670913197593481954635164564802342610972482222202107694551652952390813508791414915891303962110687008643869459464552765720740782062174337998814106326732925355228688137214901298112245145188984905722230728525513315575501591439747639798341180199932396254828901710708185069063066665599493827577257201576306269066333264756530000924588831643303777979186961204949739037782970490505108060994073026293712895895000358379996720725430436028407889577179615094551674824347103070260914462157228988025818254518032570701886087211312807951223342628836862232150377566662250398253433597456888442390026549819838548794829220689472168983109969836584681402285424333066033985088644580400103493397042756718644338377048603786162277173854562306587467901408672332763671875;
        // 最大正非规格化数与最小正规格化数之和除以2的精确值 即 ((000FFFFFFFFFFFFF + 0010000000000000) / 2) 的精确值
        str1 = doubleToIEEE754Hex(d1);
        System.out.printf("((最大正非规格化数 + 最小正规格化数) / 2)的精确值的IEEE754编码为%s\n", str1);
        // 打印0010000000000000
        // 是最大正非规格化数与最小正规格化数之和除以2的精确值舍入后的编码
        // 即 ((000FFFFFFFFFFFFF + 0010000000000000) / 2) 的精确值舍入后的编码

        d1 = 0.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000222507385850720113605740979670913197593481954635164564802342610972482222202107694551652952390813508791414915891303962110687008643869459464552765720740782062174337998814106326732925355228688137214901298112245145188984905722230728525513315575501591439747639798341180199932396254828901710708185069063066665599493827577257201576306269066333264756530000924588831643303777979186961204949739037782970490505108060994073026293712895895000358379996720725430436028407889577179615094551674824347103070260914462157228988025818254518032570701886087211312807951223342628836862232150377566662250398253433597456888442390026549819838548794829220689472168983109969836584681402285424333066033985088644580400103493397042756718644338377048603786162277173854562306587467901408672332763671874;
        // 最大正非规格化数 < d1 < 最大正非规格化数与最小正规格化数之和除以2的精确值
        // 即000FFFFFFFFFFFFF < d1 < ((000FFFFFFFFFFFFF + 0010000000000000) / 2) 的精确值
        str1 = doubleToIEEE754Hex(d1);
        System.out.printf("大于最大非规格化数且小于((最大正非规格化数 + 最小正规格化数) / 2)的精确值的浮点数的IEEE754编码为%s\n", str1);
        // 打印000FFFFFFFFFFFFF
        // 是d1舍入后的编码
        // 最大正非规格化正数 < d1 < 最大正非规格化数与最小正规格化数之和除以2的精确值
        // 即000FFFFFFFFFFFFF < d1 < ((000FFFFFFFFFFFFF + 0010000000000000) / 2) 的精确值

        d1 = 0.0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000222507385850720113605740979670913197593481954635164564802342610972482222202107694551652952390813508791414915891303962110687008643869459464552765720740782062174337998814106326732925355228688137214901298112245145188984905722230728525513315575501591439747639798341180199932396254828901710708185069063066665599493827577257201576306269066333264756530000924588831643303777979186961204949739037782970490505108060994073026293712895895000358379996720725430436028407889577179615094551674824347103070260914462157228988025818254518032570701886087211312807951223342628836862232150377566662250398253433597456888442390026549819838548794829220689472168983109969836584681402285424333066033985088644580400103493397042756718644338377048603786162277173854562306587467901408672332763671875001;
        // 最大正非规格化数与最小正规格化数之和除以2的精确值 < d1 < 最小正规格化数
        // 即 ((000FFFFFFFFFFFFF + 0010000000000000) / 2) 的精确值 < d1 < 0010000000000000
        str1 = doubleToIEEE754Hex(d1);
        System.out.printf("大于((最大正非规格化数 + 最小正规格化数) / 2)的精确值且小于最小正规格化数的浮点数的IEEE754编码为%s\n", str1);
        // 打印0010000000000000
        // 是d1舍入后的编码
        // 最大正非规格化数与最小正规格化数之和除以2的精确值 < d1 < 最小正规格化数
        // 即 ((000FFFFFFFFFFFFF + 0010000000000000) / 2) 的精确值 < d1 < 0010000000000000

        d1 = 0.1;
        str1 = doubleToIEEE754Hex(d1);
        double d2;
        d2 = 0.2;
        str2 = doubleToIEEE754Hex(d2);
        double d3;
        d3 = d1 + d2;
        String str3;
        str3 = doubleToIEEE754Hex(d3);
        System.out.printf("%s浮点加%s等于%s\n", str1, str2, str3);
        String str4;
        String str5;
        String str6;
        str4 = IEEE754HexToExactValueString(str1);
        str5 = IEEE754HexToExactValueString(str2);
        str6 = IEEE754HexToExactValueString(str3);

        String str7;
        String str8;
        String str9;
        String str10;
        System.out.printf("存在编程语言使得%s + %s = %s\n", str4, str5, str6);
        bd4 = new BigDecimal(str4);
        bd5 = new BigDecimal(str5);
        bd6 = bd4.add(bd5);
        str7 = bd4.toPlainString();
        str8 = bd5.toPlainString();
        str9 = bd6.toPlainString();
        System.out.printf("在数学上%s + %s = %s\n", str7, str8, str9);

        str10 = IEEE754HexToExactValueString(DoubleAddToHexIEEE754(d1, d2));
        System.out.printf("Objects.equals(%s, %s) is %s\n", str6, str10, Objects.equals(str6, str10));

        d1 = -0;
        str1 = doubleToIEEE754Hex(d1);
        System.out.printf("-0的IEEE754编码为%s\n", str1);
        d1 = 0;
        str1 = doubleToIEEE754Hex(d1);
        System.out.printf("0的IEEE754编码为%s\n", str1);
        d1 = -1;
        d2 = 1;
        d3 = d1 + d2;
        str1 = doubleToIEEE754Hex(d3);
        System.out.printf("-1 + 1的IEEE754编码为%s\n", str1);
        d3 = d2 + d1;
        str1 = doubleToIEEE754Hex(d3);
        System.out.printf("1 + (-1)的IEEE754编码为%s\n", str1);

        long[] ls1 = { 0X037FFFFFFFFFFFFFL, 0X037FFFFFFFFFFFFDL, 0X037FFFFFFFFFFFFFL, 0X837FFFFFFFFFFFFEL,
                0X837FFFFFFFFFFFFEL, 0X8370000000000000L, 0X037FFFFFFFFFFFFEL, 0X037FFFFFFFFFFFFEL, 0X002FFFFFFFFFFFFFL,
                0X0020000000000001L, 0X0370000000000000L, 0X0370000000000000L, 0X0020000000000000L, 0X0020000000000000L,
                0X0008000000000000L, 0X0008000000000000L, 0X0010000000000001L, 0X0020000000000000L, 0X0000000000000002L,
                0X0010000000000000L, 0X7FE0000000000000L, 0X8020000000000000L, 0X0020000000000000L, 0X0000000000000000L,
                0X0000000000000000L, 0X0008000000000000L, 0X0004000000000000L };
        long[] ls2 = { 0X0020000000000000L, 0X0020000000000000L, 0X8020000000000000L, 0X8020000000000001L,
                0X0020000000000001L, 0X0020000000000001L, 0X0020000000000001L, 0X0020000000000001L, 0X0020000000000001L,
                0X8020000000000000L, 0X836FFFFFFFFFFFFFL, 0X836FFFFFFFFFFFFEL, 0X8360000000000000L, 0X8380000000000000L,
                0X0008000000000000L, 0X0007FFFFFFFFFFFFL, 0X0000000000000001L, 0X000FFFFFFFFFFFFFL, 0X8000000000000001L,
                0X800FFFFFFFFFFFFFL, 0X7FDFFFFFFFFFFFFEL, 0X0000000000000000L, 0X0000000000000000L, 0X8020000000000000L,
                0X0020000000000000L, 0X0008000000000000L, 0X0004000000000000L };

        int i;
        i = 0;
        while (i < ls1.length) {
            d1 = Double.longBitsToDouble(ls1[i]);
            d2 = Double.longBitsToDouble(ls2[i]);
            d3 = d1 + d2;
            str1 = doubleToIEEE754Hex(d3);
            str2 = DoubleAddToHexIEEE754(d1, d2);
            if (!(Objects.equals(str1, str2))) {
                System.out.printf("%s浮点加%s等于%s不等于%s\n", doubleToIEEE754Hex(d1), doubleToIEEE754Hex(d2), str1, str2);
                break;
            } else {
                System.out.printf("%s浮点加%s等于%s\n", doubleToIEEE754Hex(d1), doubleToIEEE754Hex(d2), str2);
            }

            i++;
        }

        System.out.printf("%d == %d is %b\n", i, ls1.length, i == ls1.length);

        i = 0;
        while (i < ls1.length) {
            d1 = Double.longBitsToDouble(ls1[i]);
            str1 = HexToBinary(doubleToIEEE754Hex(d1));
            if (str1.charAt(0) == '0') {
                str1 = "1" + str1.substring(1, str1.length());
            } else {
                str1 = "0" + str1.substring(1, str1.length());
            }
            d1 = Double.longBitsToDouble(Long.parseUnsignedLong(str1, 2));
            d2 = Double.longBitsToDouble(ls2[i]);
            str2 = HexToBinary(doubleToIEEE754Hex(d2));
            if (str2.charAt(0) == '0') {
                str2 = "1" + str2.substring(1, str2.length());
            } else {
                str2 = "0" + str2.substring(1, str2.length());
            }
            d2 = Double.longBitsToDouble(Long.parseUnsignedLong(str2, 2));
            d3 = d1 + d2;
            str1 = doubleToIEEE754Hex(d3);
            str2 = DoubleAddToHexIEEE754(d1, d2);
            if (!(Objects.equals(str1, str2))) {
                System.out.printf("%s浮点加%s等于%s不等于%s\n", doubleToIEEE754Hex(d1), doubleToIEEE754Hex(d2), str1, str2);
                break;
            } else {
                System.out.printf("%s浮点加%s等于%s\n", doubleToIEEE754Hex(d1), doubleToIEEE754Hex(d2), str2);
            }

            i++;
        }

        System.out.printf("%d == %d is %b\n", i, ls1.length, i == ls1.length);

        i = 0;
        while (i < ls1.length) {
            d1 = Double.longBitsToDouble(ls1[i]);
            d2 = Double.longBitsToDouble(ls2[i]);
            d3 = d1 - d2;
            str1 = doubleToIEEE754Hex(d3);
            str2 = DoubleSubtractToHexIEEE754(d1, d2);
            if (!(Objects.equals(str1, str2))) {
                System.out.printf("%s浮点减%s等于%s不等于%s\n", doubleToIEEE754Hex(d1), doubleToIEEE754Hex(d2), str1, str2);
                break;
            } else {
                System.out.printf("%s浮点减%s等于%s\n", doubleToIEEE754Hex(d1), doubleToIEEE754Hex(d2), str2);
            }

            i++;
        }

        System.out.printf("%d == %d is %b\n", i, ls1.length, i == ls1.length);

        i = 0;
        while (i < ls1.length) {
            d1 = Double.longBitsToDouble(ls1[i]);
            d2 = Double.longBitsToDouble(ls2[i]);
            d3 = d2 - d1;
            str1 = doubleToIEEE754Hex(d3);
            str2 = DoubleSubtractToHexIEEE754(d2, d1);
            if (!(Objects.equals(str1, str2))) {
                System.out.printf("%s浮点减%s等于%s不等于%s\n", doubleToIEEE754Hex(d2), doubleToIEEE754Hex(d1), str1, str2);
                break;
            } else {
                System.out.printf("%s浮点减%s等于%s\n", doubleToIEEE754Hex(d1), doubleToIEEE754Hex(d2), str2);
            }

            i++;
        }

        System.out.printf("%d == %d is %b\n", i, ls1.length, i == ls1.length);

        long[] ls3 = { 0X3FE0000000000000L, 0X3FE0000000000000L, 0X3FD0000000000000L, 0X3FF0000000000000L,
                0X3FF0000000000001L, 0X3FFC000000000000L, 0X3FF8000000000000L, 0X4315555555555555L, 0X4305555555555555L,
                0X3FF0000000000001L, 0X1E70000000000001L, 0X0000000002000000L, 0XCFF0000000000000L };
        long[] ls4 = { 0X0010000000000000L, 0X0020000000000000L, 0X0010000000000000L, 0X3FF0000000000000L,
                0X3FF0000000000001L, 0X3FF0000000000004L, 0X3FF0000000000001L, 0X0000000000000003L, 0X0000000000000003L,
                0X3FF8000000000001L, 0X1E60000000000001L, 0X8000000004000000L, 0X0000000000000000L };
        // 4315555555555555 浮点乘 0000000000000003 在尾数规格化之前有
        // 尾数的真值为pow(2, 54) - 1，阶码的真值为-1024
        // 在规格化后舍入之前尾数的真值为pow(2, 52) - 1，即尾数的真值小于53位，阶码的真值为-1022
        // 在舍入并规格化后尾数的真值为pow(2, 52)，即尾数的真值有53位，阶码的真值为-1022
        // 所以在舍入并规格化后是一个规格化数
        // 3FF0000000000001 浮点乘 3FF8000000000001 在尾数规格化之前的真值为
        // (pow(2, 52) + 1) * (pow(2, 52) + pow(2, 51) + 1) 规格化之后有最低有效位为0 保护位为1, 粘贴位为1

        i = 0;
        while (i < ls3.length) {
            d1 = Double.longBitsToDouble(ls3[i]);
            d2 = Double.longBitsToDouble(ls4[i]);
            d3 = d1 * d2;
            str1 = doubleToIEEE754Hex(d3);
            str2 = DoubleMultiplyToHexIEEE754(d1, d2);
            if (!(Objects.equals(str1, str2))) {
                System.out.printf("%s浮点乘%s等于%s不等于%s\n", doubleToIEEE754Hex(d1), doubleToIEEE754Hex(d2), str1, str2);
                break;
            } else {
                System.out.printf("%s浮点乘%s等于%s\n", doubleToIEEE754Hex(d1), doubleToIEEE754Hex(d2), str2);
            }

            i++;
        }

        System.out.printf("%d == %d is %b\n", i, ls3.length, i == ls3.length);

        // d1 = Double.longBitsToDouble(0X7332AD3F4A24C115L);
        // d2 = Double.longBitsToDouble(0XC996A7C34BF1D837L);
        // d3 = d1 / d2;
        // str1 = doubleToIEEE754Hex(d3);
        // str2 = DoubledDivideToHexIEEE754(d1, d2);
        // if (!(Objects.equals(str1, str2))) {
        // System.out.println("d1 is " + d1);
        // System.out.println("d2 is " + d2);
        // System.out.println("d3 is " + d3);
        // System.out.printf("%s浮点除%s等于%s不等于%s\n", doubleToIEEE754Hex(d1),
        // doubleToIEEE754Hex(d2), str1, str2);
        // } else {
        // System.out.printf("%s浮点除%s等于%s\n", doubleToIEEE754Hex(d1),
        // doubleToIEEE754Hex(d2), str2);
        // }
        // System.exit(1);

        long[] ls5 = { 0X3FFFFFFFFFFFFFFFL, 0X3FEFFFFFFFFFFFFFL, 0X3FF0000000000001L, 0X3FE0000000000003L,
                0X3FFFFFFFFFFFFFFFL };
        long[] ls6 = { 0X7FE0000000000000L, 0X7FE0000000000000L, 0X7FE0000000000000L, 0X7FE0000000000000L,
                0X7FD0000000000000L };

        i = 0;
        while (i < ls5.length) {
            d1 = Double.longBitsToDouble(ls5[i]);
            d2 = Double.longBitsToDouble(ls6[i]);
            d3 = d1 / d2;
            str1 = doubleToIEEE754Hex(d3);
            str2 = DoubledDivideToHexIEEE754(d1, d2);
            if (!(Objects.equals(str1, str2))) {
                System.out.printf("%s浮点除%s等于%s不等于%s\n", doubleToIEEE754Hex(d1), doubleToIEEE754Hex(d2), str1, str2);
                break;
            } else {
                System.out.printf("%s浮点除%s等于%s\n", doubleToIEEE754Hex(d1), doubleToIEEE754Hex(d2), str2);
            }

            i++;
        }

        System.out.printf("%d == %d is %b\n", i, ls5.length, i == ls5.length);

        return;
    }

    public static String HexToBinary(String str) { // 十六进制转为二进制，长度为64
        str = str.toUpperCase();
        int i = 0;
        char ch;
        StringBuffer result = new StringBuffer();
        while (i < str.length()) {
            ch = str.charAt(i);
            if (ch <= '9' && ch >= '0') {
                result.append(str16[(int) (ch - '0')]);

            } else {
                result.append(str16[(int) (ch - 'A') + 10]);
            }

            i++;
        }

        return result.toString();
    }

    public static String IEEE754BinaryToExactValueString(String str) {
        // 用字符串保存二进制形式的IEEE754编码的浮点数的精确值
        String s = str.substring(0, 1); // 获得浮点数的符号位
        String e = str.substring(1, 12); // 获得浮点数的阶码
        String m = str.substring(12, 64); // 获得浮点数的尾数
        int index;
        BigDecimal mvalue = new BigDecimal("0");
        BigDecimal result = null;
        if (!(Objects.equals(e, "00000000000")) && !(Objects.equals(e, "11111111111"))) {
            // 如果阶码不全为0且阶码不全为1
            index = 51;
            while (index >= 0) {
                if (m.charAt(index) == '1') {
                    mvalue = mvalue.add(new BigDecimal("2").pow(51 - index));
                }
                index--;
            }

            mvalue = mvalue.divide(new BigDecimal("2").pow(52)); // 计算尾数的精确值，不包含隐藏位

            BigDecimal base = mvalue.add(new BigDecimal("1"));
            // 计算尾数的精确值，包含隐藏位。由于阶码不全为0且阶码不全为1，所以隐藏位为1

            index = 10;
            BigInteger bie = new BigInteger("0");
            while (index >= 0) {
                if (e.charAt(index) == '1') {
                    bie = bie.add(new BigInteger("2").pow(10 - index)); // 计算阶码的值
                }
                index--;
            }
            bie = bie.subtract(new BigInteger("1023"));
            // 计算阶码的真值。由于阶码不全为0且阶码不全为1，所以阶码的偏移值是1023

            if (bie.max(new BigInteger("0")).equals(bie)) { // 计算浮点数的真值的绝对值
                result = base.multiply(new BigDecimal("2").pow(bie.intValue()));
            } else {
                result = base.multiply(new BigDecimal("1").divide(new BigDecimal("2").pow(-(bie.intValue()))));
            }
        } else if (Objects.equals(e, "00000000000")) { // 如果阶码全为0
            index = 51;
            while (index >= 0) {
                if (m.charAt(index) == '1') {
                    mvalue = mvalue.add(new BigDecimal("2").pow(51 - index));
                }
                index--;
            }

            mvalue = mvalue.divide(new BigDecimal("2").pow(52));
            // 计算尾数的精确值。由于阶码全为0，所以隐藏位为0
            result = mvalue.multiply(new BigDecimal("1").divide(new BigDecimal("2").pow(1022)));
            // 计算浮点数的绝对值的真值。由于阶码全为0，所以阶码的真值是-1022
        }

        if (Objects.equals(s, "1")) { // 如果符号位为1
            result = new BigDecimal("0").subtract(result);
        }

        return result.toPlainString();
    }

    public static String BinaryToHex(String str) { // 二进制转为十六进制，长度为16
        Long l = Long.parseUnsignedLong(str, 2);
        String result = Long.toHexString(l);
        while (result.length() < 16) {
            result = "0" + result;
        }

        return result.toUpperCase();
    }

    public static String doubleToIEEE754Hex(double d) { // 返回浮点数的IEEE754编码，用十六进制表示，共16位
        long l = Double.doubleToRawLongBits(d);
        String result = Long.toHexString(l).toUpperCase();
        while (result.length() < 16) {
            result = "0" + result;
        }
        return result;
    }

    public static double IEEE754HexToDouble(String str) { // 返回十六进制IEEE754编码所表示的浮点数
        long l = Long.parseUnsignedLong(str, 16);
        double result = Double.longBitsToDouble(l);
        return result;
    }

    public static String IEEE754HexToExactValueString(String str) {// 返回十六进制IEEE754编码所表示的浮点数的精确值
        return IEEE754BinaryToExactValueString(HexToBinary(str));
    }

    public static String DoubleAddToHexIEEE754(double d1, double d2) { // 返回两个浮点数相加的和的十六进制IEEE754编码
        return BinaryToHex(IEEE754BinaryAdd(HexToBinary(doubleToIEEE754Hex(d1)), HexToBinary(doubleToIEEE754Hex(d2))));
    }

    public static String doubleExactValueString(double d) { // 返回double的IEEE754编码的精确值
        return IEEE754HexToExactValueString(doubleToIEEE754Hex(d));
    }

    public static String IEEE754BinaryAdd(String str1, String str2) {
        // 返回两个IEEE754编码相加的和的二进制IEEE754编码
        String s1 = str1.substring(0, 1); // 被加数的符号位
        String e1 = str1.substring(1, 12); // 被加数的阶码
        String m1 = str1.substring(12, 64); // 被加数的尾数
        int e1Int = 0;
        if (!(Objects.equals(e1, "00000000000")) && !(Objects.equals(e1, "11111111111"))) {
            // 当浮点数是规格化数时
            m1 = "1" + m1; // 尾数的真值
            e1Int = Integer.parseUnsignedInt(e1, 2); // 被加数的阶码
        } else if (Objects.equals(e1, "00000000000")) { // 当浮点数是非规格化数时
            e1Int = 1; // 被加数的阶码
        }
        String s2 = str2.substring(0, 1); // 加数的符号位
        String e2 = str2.substring(1, 12); // 加数的阶码
        String m2 = str2.substring(12, 64); // 加数的尾数
        int e2Int = 0;
        if (!(Objects.equals(e2, "00000000000")) && !(Objects.equals(e2, "11111111111"))) {
            // 当浮点数是规格化数时
            m2 = "1" + m2; // 尾数的真值
            e2Int = Integer.parseUnsignedInt(e2, 2); // 加数的阶码
        } else if (Objects.equals(e2, "00000000000")) {
            e2Int = 1; // 加数的阶码
        }

        long m1Long = Long.parseUnsignedLong(m1, 2); // 尾数的真值
        long m2Long = Long.parseUnsignedLong(m2, 2); // 尾数的真值
        int shiftTime;
        int i = 0;
        int eResultInt;
        // 和的阶码
        // 如果和的尾数的真值有53位，那么有阶码不全为0
        // 所以规格化后和的尾数的真值小于53位时，令eResultInt = 0，表示阶码全为0，eResultInt大于0时表示阶码不全为0
        // 所以当eResultInt = 1时，有阶码的真值为-1022,尾数的真值为53位
        // eResultInt = 0时，有阶码的真值为-1022,尾数的真值小于53位
        long shiftLong = 0L;
        // 用shiftLong保留移出的位
        // shiftLong共有64位，由于尾数的真值最多有53位
        // 当小阶的尾数的真值右移次数大于等于54次时，必有保护位为0，而shiftLong得最高位也必然为0，小阶的尾数得真值全为0
        // 所以尾数的和或差为大阶的尾数的真值，并且舍入后不变
        // 当小阶的尾数的真值右移次数小于等于52次时，小阶的尾数右移后相加或相减后的值最多为54位
        // 所以移出的位最多为53位，53 < 64，所以shiftLong可以保留所有移出的位
        // 当小阶的尾数的真值右移次数为53次时，小阶的尾数右移后相加或相减后的值最多为53位
        // 所以移出的位为53位，53 < 64，所以shiftLong可以保留所有移出的位
        // 综上所述，实际上shiftLong只需要53位就可以保证尾数舍入结果的正确性
        if (e1Int > e2Int) {
            shiftTime = e1Int - e2Int; // 小阶右移次数
            eResultInt = e1Int; // 大阶的值
            while (i < shiftTime) { // 右移次数
                shiftLong = shiftLong >>> 1; // 保留小阶尾数移出的位
                shiftLong = ((m2Long & 1L) << 63) | shiftLong; // 保留小阶尾数移出的位
                m2Long = m2Long >>> 1; // 小阶尾数右移
                i++;
            }
        } else {
            shiftTime = e2Int - e1Int; // 小阶右移次数
            eResultInt = e2Int; // 大阶的值
            while (i < shiftTime) { // 右移次数
                shiftLong = shiftLong >>> 1; // 保留小阶尾数移出的位
                shiftLong = ((m1Long & 1L) << 63) | shiftLong; // 保留小阶尾数移出的位
                m1Long = m1Long >>> 1; // 小阶尾数右移
                i++;
            }
        }

        long mResultLong;
        String sResult;
        if (Objects.equals(s1, s2)) { // 如果两浮点数均大于等于0或均小于0
            mResultLong = m1Long + m2Long; // 尾数的真值相加
            sResult = s1; // 和的符号位
        } else { // 如果其中一个浮点数大于等于0，另一个小于0
            if (m1Long > m2Long) { // 如果被加数的尾数的真值比加数的尾数的真值大
                mResultLong = m1Long - m2Long; // 符号位相反，所以和的尾数的真值为两者尾数的真值相减的绝对值
                sResult = s1; // 和的符号位为尾数真值较大的浮点数的符号位
            } else { // 如果被加数的尾数的真值小于等于加数的尾数的真值
                mResultLong = m2Long - m1Long; // 符号位相反，所以和的尾数的真值为两者尾数的真值相减的绝对值
                sResult = s2; // 和的符号位为尾数真值较大的浮点数的符号位
            }
        }

        if (mResultLong == 0L) {
            // 如果和的尾数的真值为0，说明两个浮点数阶码相同，互为相反数
            // 这是因为当阶码不同时
            // 如果小阶是规格化数，右移后小阶会用0补充隐藏位原来所在的位，而大阶的隐藏位为1，此时尾数的差不可能为0
            // 如果小阶是非规格化数，由于阶码不同，所以大阶的阶码不全为0，小阶的尾数都必然小于大阶的尾数
            // 因为大阶的尾数的隐藏位为1，而无论小阶是否右移，小阶隐藏位原来所在的位必然为0
            // 只有大阶为1时小阶才不会右移
            return "0" + "00000000000" + "0000000000000000000000000000000000000000000000000000";
            // 互为相反数的两数相加和为0
        } else { // 如果和的尾数的真值不为0，则和的尾数的真值的真值中必有1位为1
            if (mResultLong >= 0X20000000000000L) {
                // 相加后和的尾数的真值可能有54位，此时说明两浮点数符号位相同，此时要右移1位
                shiftLong = shiftLong >>> 1; // 保留右移的位
                shiftLong = ((mResultLong & 1L) << 63) | shiftLong; // 保留右移的位
                mResultLong = mResultLong >> 1;
                eResultInt++; // 右移1次后阶码要加1
                if (eResultInt == 0X7FF) { // 如果阶码全为1
                    return sResult + "11111111111" + "0000000000000000000000000000000000000000000000000000";
                    // 返回无限大
                }
            } else {
                while (mResultLong < 0X10000000000000L) {
                    // // 如果其中一个浮点数大于等于0，另一个小于0，则和的尾数的真值可能小于53位
                    // 两个非规格化数相加的和尾数的真值可能小于53位
                    // 此时要左移，直到有53位
                    // 如果如果其中一个浮点数大于等于0，另一个小于0，那么左移后的值要减去舍出的最高位
                    // 由于尾数真值不为0，所以左移后尾数的真值大于等于2，而减去的最高位最多为1
                    // 所以尾数一直大于等于1，即尾数不全为0
                    // 而最高位最多只能有53个1，一旦最高位为0，则尾数会增长一倍
                    // 所以循环完毕后要么eResultInt = 0，要么尾数的真值为53位
                    // 如果这个循环没执行，那么有尾数的真值为53位，eResultInt > 0
                    // 所以无论是否执行循环，都有要么eResultInt = 0，要么尾数的真值为53位
                    eResultInt--; // 左移后和的阶码要减1
                    if (eResultInt == 0) { // 如果阶码全为0
                        break;
                        // 如果阶码全为0，则减1之前为1，即减1之前阶码的真值为1 - 1023 = -1022，而全为0的真值为-1022
                        // 所以尾数不需要左移，此时有尾数的隐藏位为0，即尾数的真值小于53位
                    } else {
                        // 如果阶码不全为0且阶码不全为1，则左移后要减去之前舍出的最高位，这样可以尽可能减少误差
                        // 之前舍出的最高位为0时无需减0
                        mResultLong = mResultLong << 1; // 尾数的真值左移
                        if ((shiftLong & 0X8000000000000000L) == 0X8000000000000000L) {
                            // 如果之前舍出的值的最高位为1
                            mResultLong--; // 减去之前舍出的值
                        }
                        shiftLong = shiftLong << 1; // 舍出的值的最高位被保留后要右移掉
                    }
                }
            }
        }

        if ((shiftLong & 0X8000000000000000L) == 0X8000000000000000L) { // 如果保护位为1则可能需要舍入
            if ((mResultLong & 1L) == 1L) { // 最低有效位为1且保护位为1则尾数的真值要加1或减1
                if (Objects.equals(s1, s2)) {
                    // 如果两浮点数均大于等于0或均小于0，则尾数的真值加1
                    // 这是两浮点数均大于等于0或均小于0，则两尾数相加，所以舍掉后的尾数相加后的值小于等于相加后的精确值
                    mResultLong++; // 因为保护位为1，所以和的阶码最小为-1022，尾数的真值加1前为53位，是规格化数
                    if (mResultLong >= 0X20000000000000L) { // 加1后可能导致尾数的真值为54位
                        mResultLong = mResultLong >> 1;
                        eResultInt++; // 舍去后阶码要加1
                        if (eResultInt == 0X7FF) { // 如果阶码全为1
                            return sResult + "11111111111" + "0000000000000000000000000000000000000000000000000000";
                            // 返回无限大
                        }
                    }
                } else {
                    // 如果其中一个浮点数大于等于0，另一个小于0，则尾数的真值减1
                    // 这是因为其中一个浮点数大于等于0，另一个小于0，且保护位为1，所以这两数不同阶
                    // 则有大阶的尾数减小阶的尾数，而舍掉的位是小阶的尾数
                    // 所以大阶的尾数减舍掉后的小阶的尾数的值大于等于相减后的精确值
                    // 最低有效位为1且尾数的真值有53位，所以尾数减1后仍是53位，不需要判断尾数是否需要左移
                    mResultLong--;
                }
            } else if ((mResultLong & 1L) == 0L & (shiftLong << 1) != 0) {
                // 如果最低有效位为0且保护位为1且(舍入位为1或粘贴位为1)，则尾数的真值要加1或减1
                if (Objects.equals(s1, s2)) {
                    // 如果两浮点数均大于等于0或均小于0，则尾数的真值加1
                    // 这是两浮点数均大于等于0或均小于0，则两尾数相加，所以舍掉后的尾数相加后的值小于等于相加后的精确值
                    // 最低有效位为0且尾数的真值有53位，所以尾数加1后仍是53位，不需要判断尾数是否需要右移
                    mResultLong++;
                } else {
                    // 如果其中一个浮点数大于等于0，另一个小于0，则尾数的真值减1
                    // 这是因为其中一个浮点数大于等于0，另一个小于0，且保护位为1，所以这两数不同阶
                    // 则有大阶的尾数减小阶的尾数，而舍掉的位是小阶的尾数
                    // 所以大阶的尾数减舍掉后的小阶的尾数的值大于等于相减后的精确值
                    if (mResultLong == 0X10000000000000L) {
                        // 如果尾数的真值为0X10000000000000L
                        // 那么减1后为52位，需要规格化
                        eResultInt--;
                        // 尾数的真值为52位，所以规格化时和的阶码要减1
                        // 记小阶在对阶时右移了n位，则有大阶的阶码减小阶的阶码为n，小阶的阶码最小为-1022
                        // 所以和的阶码最小为(-1022 + n)
                        // 而保护位为1，所以尾数相减规格化后和的阶码最多减(n - 1)
                        // 所以规格化后大阶的阶码最小为(-1022 + n) - (n - 1) = -1021
                        // 即eResultInt最小为2
                        // 而减1后和的尾数的真值为52位
                        // 那么规格化后必有尾数的真值为53位且和的阶码大于等于-1022，所以和的阶码不全为0
                        // 即eResultInt最小为1
                        // 只有这个if分支为真，才会使eResultInt减1，可得只要保护位为1，都有eResultInt > 0
                        mResultLong = (mResultLong << 1) - 1;
                        // 因为和的阶码减1后不全为0，所以舍入操作是尾数的真值左移后减1
                        // 等价于尾数的真值为0X1FFFFFFFFFFFF;
                    } else { // 尾数的真值不等于0X10000000000000L且有53位，所以减1后仍有53位，不需要规格化
                        mResultLong--;
                    }
                }
            }
        }
        // 只要保护位为1，都有eResultInt > 0且尾数的真值为53位(在没有执行return语句的条件下)
        // 因为已经从逻辑上证明了在某些情况下mResultLong++或mResultLong--，尾数的真值都为53位
        // 而其它情况下，都做了左移或右移操作，从而使得尾数的真值都为53位

        String eResultString = Integer.toUnsignedString(eResultInt, 2);
        // 阶码
        // 由在舍入前要么eResultInt = 0，要么尾数的真值为53位(在前面有证明)且
        // 只要保护位为1，都有0X7FF > eResultInt > 0(在没有执行return语句的条件下)，尾数的真值为53位(在前面有证明)且
        // 保护位为0不做任何操作
        // 得出如果eResultInt = 0，则有尾数的真值小于53位。如果0X7FF > eResultInt > 0，则有尾数的真值为53位
        // 所以当eResultInt = 0，有阶码的真值为-1022,尾数的真值小于53位，是非规格化数，有阶码为"00000000000"
        // 当eResultInt = 1，有阶码的真值位-1022，尾数的真值为53为，是规格化数，有阶码为"00000000001"
        while (eResultString.length() < 11) {
            eResultString = "0" + eResultString; // 阶码补足11位
        }

        String mResultString = Long.toUnsignedString(mResultLong, 2);
        while (mResultString.length() < 53) {
            // 当阶码为0时，即eResultInt = 0时，有尾数的真值小于53位，此时转为字符串后要在前面加"0"补足53位
            mResultString = "0" + mResultString;
        }

        return sResult + eResultString + mResultString.substring(1, 53);
        // 返回和的IEEE754编码，其中隐藏尾数的最高位
    }

    public static String DoubleSubtractToHexIEEE754(double minuend, double subtrahend) {
        // 返回两个浮点数相减的差的十六进制IEEE754编码
        return DoubleAddToHexIEEE754(minuend, -subtrahend);
    }

    public static String IEEE754BinaryMultiply(String str1, String str2) {
        // 返回两个二进制IEEE754编码相乘的积的二进制IEEE754编码
        String s1 = str1.substring(0, 1); // 被乘数的符号位
        String e1 = str1.substring(1, 12); // 被乘数的阶码
        String m1 = str1.substring(12, 64); // 被乘数的尾数
        int e1Int = 0;
        if (!(Objects.equals(e1, "00000000000")) && !(Objects.equals(e1, "11111111111"))) {
            // 当浮点数是规格化数时
            m1 = "1" + m1; // 尾数的真值
            e1Int = Integer.parseUnsignedInt(e1, 2) - 1023; // 被乘数的阶码的真值
        } else if (Objects.equals(e1, "00000000000")) { // 当浮点数是非规格化数时
            e1Int = -1022; // 被乘数的阶码的真值
        }
        String s2 = str2.substring(0, 1); // 乘数的符号位
        String e2 = str2.substring(1, 12); // 乘数的阶码
        String m2 = str2.substring(12, 64); // 乘数的尾数
        int e2Int = 0;
        if (!(Objects.equals(e2, "00000000000")) && !(Objects.equals(e2, "11111111111"))) {
            // 当浮点数是规格化数时
            m2 = "1" + m2; // 尾数的真值
            e2Int = Integer.parseUnsignedInt(e2, 2) - 1023; // 乘数的阶码的真值
        } else if (Objects.equals(e2, "00000000000")) {
            e2Int = -1022; // 乘数的阶码的真值
        }

        String sResult;
        if (Objects.equals(s1, s2)) { // 大于等于0的两数相乘大于等于0，小于0的两数相乘大于0
            sResult = "0";
        } else {
            sResult = "1"; // 大于等于0的数乘于小于0的数小于等于0
        }

        BigInteger mbi1 = new BigInteger(m1, 2);
        BigInteger mbi2 = new BigInteger(m2, 2);
        BigInteger mResult = mbi1.multiply(mbi2);
        int eResult = e1Int + e2Int - 52;
        // 相乘的积为
        // (pow(-1, s1) * (mbi1 / pow(2, 52)) * pow(2, e1Int)) *
        // (pow(-1, s2) * (mbi2 / pow(2, 52)) * pow(2, e2Int)) =
        // (pow(-1, s1 + s2) * (mbi1 * mbi2) * pow(2, e1Int + e2Int - 52 - 52)) =
        // (pow(-1, s1 + s2) * (mbi1 * mbi2 / pow(2, 52)) * pow(2, e1Int + e2Int - 52))
        BigInteger L0X20000000000000L = new BigInteger("20000000000000", 16);
        BigInteger L0XFFFFFFFFFFFFFL = new BigInteger("FFFFFFFFFFFFF", 16);
        BigInteger I0X2 = new BigInteger("2", 16);
        BigInteger[] divideAndRemainder = new BigInteger[2];
        int guard = 0; // 保护位
        int round = 0; // 舍入位
        int sticky = 0; // 粘贴位
        int emax = 0X7FF - 1023; // 阶码最大值

        BigInteger I0X0 = new BigInteger("0", 16);
        if (mResult.equals(I0X0)) { // 如果尾数为0
            return sResult + "00000000000" + "0000000000000000000000000000000000000000000000000000";
        }

        if (mResult.equals(mResult.max(L0X20000000000000L))) { // 如果尾数大于53位
            while (mResult.equals(mResult.max(L0X20000000000000L))) {
                divideAndRemainder = mResult.divideAndRemainder(I0X2);
                sticky = sticky | round;
                round = guard;
                guard = divideAndRemainder[1].intValue();
                mResult = divideAndRemainder[0];
                eResult++;
                if (eResult >= emax) {
                    // 为真说明阶码的真值大于等于0X7FF且尾数大于等于53位
                    return sResult + "11111111111" + "0000000000000000000000000000000000000000000000000000";
                }
            }
        }

        if (mResult.equals(mResult.min(L0XFFFFFFFFFFFFFL))) { // 如果尾数的真值小于53位
            return sResult + "00000000000" + "0000000000000000000000000000000000000000000000000000";
            // 这是因为规格化数的尾数的真值共有53位，而积的尾数的真值小于53位，说明被乘数和乘数都是非规格化数
            // 则积的阶码为-2022，在尾数的真值左移52次后有尾数的真值为0，阶码为-2022 + 52 < -1022
            // 所以返回0
        }

        boolean isAllZero = false; // 用来判断阶码是否为全0
        if (eResult < -1022) { // 如果阶码的真值小于-1022则置阶码全为0
            isAllZero = true;
            while (eResult < -1022) { // 尾数右移使阶码的真值为-1022
                divideAndRemainder = mResult.divideAndRemainder(I0X2);
                sticky = sticky | round;
                round = guard;
                guard = divideAndRemainder[1].intValue();
                eResult++;
                mResult = divideAndRemainder[0];
            }
        }

        if (guard == 1) { // 如果保护位为1则可能需要舍入
            if ((mResult.divideAndRemainder(I0X2)[1].intValue() == 1)) { // 最低有效位为1且保护位为1则尾数的真值要加1
                mResult = mResult.add(new BigInteger("1", 16));
                if (mResult.equals(new BigInteger("10000000000000", 16))) {
                    // 为真说明加1前非规格化。加1后尾数的真值有53位且阶码为-1022，此时需要规格化
                    isAllZero = false;
                    eResult = -1022;
                } else if (mResult.equals(L0X20000000000000L)) { // 加1后可能导致尾数的真值为54位
                    mResult = mResult.divideAndRemainder(I0X2)[0]; // 尾数右移1位，使得尾数为53位
                    eResult++;
                    if (eResult == emax) { // 如果阶码全为1
                        return sResult + "11111111111" + "0000000000000000000000000000000000000000000000000000";
                        // 返回无限大
                    }
                }
            } else if (round == 1 || sticky == 1) { // 尾数最低有效位为0且(舍入位为1或粘贴位为1)
                mResult = mResult.add(new BigInteger("1", 16));
                // 尾数最低有效位为0，所以加1后不会尾数的真值的位数不变
            }
        }

        String eResultString;
        if (isAllZero) { // 如果阶码为全0
            eResultString = "00000000000";
        } else {
            eResultString = Integer.toUnsignedString(eResult + 1023, 2); // 阶码
        }

        while (eResultString.length() < 11) {
            eResultString = "0" + eResultString; // 阶码补足11位
        }

        String mResultString = mResult.toString(2);
        while (mResultString.length() < 53) {
            // 当阶码全为0时，有尾数的真值小于53位，此时转为字符串后要在前面加"0"补足53位
            mResultString = "0" + mResultString;
        }

        return sResult + eResultString + mResultString.substring(1, 53);
        // 返回和的IEEE754编码，其中隐藏尾数的最高位
    }

    public static String DoubleMultiplyToHexIEEE754(double d1, double d2) { // 返回两个浮点数相乘的积的十六进制IEEE754编码
        return BinaryToHex(
                IEEE754BinaryMultiply(HexToBinary(doubleToIEEE754Hex(d1)), HexToBinary(doubleToIEEE754Hex(d2))));
    }

    public static String IEEE754BinarydDivide(String dividend, String divisor) {
        // 返回两个IEEE754编码相除的商的二进制IEEE754编码
        String s1 = dividend.substring(0, 1); // 被除数的符号位
        String e1 = dividend.substring(1, 12); // 被除数的阶码
        String m1 = dividend.substring(12, 64); // 被除数的尾数
        int e1Int = 0;
        if (!(Objects.equals(e1, "00000000000")) && !(Objects.equals(e1, "11111111111"))) {
            // 当浮点数是规格化数时
            m1 = "1" + m1; // 尾数的真值
            e1Int = Integer.parseUnsignedInt(e1, 2); // 被除数的阶码
        } else if (Objects.equals(e1, "00000000000")) { // 当浮点数是非规格化数时
            e1Int = 1; // 被除数的阶码
        }
        String s2 = divisor.substring(0, 1); // 除数的符号位
        String e2 = divisor.substring(1, 12); // 除数的阶码
        String m2 = divisor.substring(12, 64); // 除数的尾数
        int e2Int = 0;
        if (!(Objects.equals(e2, "00000000000")) && !(Objects.equals(e2, "11111111111"))) {
            // 当浮点数是规格化数时
            m2 = "1" + m2; // 尾数的真值
            e2Int = Integer.parseUnsignedInt(e2, 2); // 除数的阶码
        } else if (Objects.equals(e2, "00000000000")) {
            e2Int = 1; // 除数的阶码
        }

        String sResult;
        if (Objects.equals(s1, s2)) { // 被除数大于等于0，除数大于0，商大于等于0。被除数小于等于0，除数小于0，商大于等于0
            sResult = "0";
        } else { // 被除数大于等于0，除数小于0，商小于等于0。被除数小于等于0，除数大于0，商小于等于0
            sResult = "1";
        }

        long m1Long = Long.parseUnsignedLong(m1, 2); // 被除数的尾数的真值
        long m2Long = Long.parseUnsignedLong(m2, 2); // 除数的尾数的真值

        int eResult = e1Int - e2Int; // 商的阶码
        long mResult = 0L;
        int guard = 0;
        int round = 0;
        int sticky = 0;
        if (m1Long == 0L) { // 如果被除数为0
            return sResult + "00000000000" + "0000000000000000000000000000000000000000000000000000";
        } else {
            while (m2Long < 0X10000000000000L) { // 除数的尾数的真值补足53位
                eResult++;
                m2Long = m2Long << 1;
            }

            while (m1Long < m2Long) { // 被除数的尾数的真值要大于等于除数的尾数的真值且小于2被除数的尾数的真值
                eResult--;
                m1Long = m1Long << 1;
            }

            while (mResult < 0X10000000000000L) { // 使商的尾数的真值为53位
                if (m1Long >= m2Long) {
                    mResult = (mResult << 1) + 1;
                    m1Long = (m1Long - m2Long) << 1;
                } else {
                    mResult = mResult << 1;
                    m1Long = m1Long << 1;
                }
            }

            if (m1Long >= m2Long) { // 设置保护位
                guard = 1;
                m1Long = (m1Long - m2Long) << 1;
            } else {
                m1Long = m1Long << 1;
            }

            if (m1Long >= m2Long) { // 设置舍入位
                round = 1;
                m1Long = (m1Long - m2Long) << 1;
            } else {
                m1Long = m1Long << 1;
            }

            if (m1Long != 0L) { // 设置粘贴位
                sticky = 1;
            }
        }

        if (eResult >= 0X7FF - 1023) {
            // 如果阶码的真值大于等于0X7FF，此时有尾数的真值为53位(在前面没有执行return语句的情况下)
            return sResult + "11111111111" + "0000000000000000000000000000000000000000000000000000";
        }

        boolean isAllZero = false; // 用来判断阶码是否为全0
        if (eResult < -1022) {
            // 如果为真则说明阶码的真值小于-1022且尾数的真值为53位
            // 规格化后阶码的真值为-1022,尾数的真值小于53位，所以是非规格化数
            isAllZero = true; // 非规格化数的阶码为全0
            while (eResult < -1022) { // 使阶码的真值为-1022
                sticky = sticky | round;
                round = guard;
                guard = (int) (mResult & 1L);
                mResult = mResult >> 1;
                eResult++;
            }
        }

        if (guard == 1) { // 如果保护位为1则可能需要舍入
            if ((mResult & 1L) == 1L) { // 最低有效位为1且保护位为1则尾数的真值要加1
                mResult++;
                // 加1后不会使得mResult超过53位
                // 因为除数有m2Long < 0X20000000000000L，即m2Long < pow(2, 53)
                // 并且m1Long <= 2 * m2Long - 1
                // 所以加1前在数学上有
                // mResult * 2 + guard <= m1Long * pow(2, 53) / m2Long
                // <= (2 * m2Long - 1) * pow(2, 53) / m2Long
                // = (m2Long * pow(2, 54) - pow(2, 53)) / m2Long
                // 而pow(2, 54) - 1 = (m2Long * (pow(2, 54) - 1)) / m2Long
                // = (m2Long * pow(2, 54) - m2Long) / m2Long
                // 而m2Long < pow(2, 53)，所以mResult * 2 + guard < pow(2, 54) - 1
                // 而guard = 1，所以有mResult < pow(2, 53) - 1
                // 所以加1后有mResult <= pow(2, 53) - 1，所以加1后尾数的真值不会超过53位，不需要进行右移
                if (mResult == 0X10000000000000L) {
                    // 为真说明加1前非规格化。加1后尾数的真值有53位且阶码为-1022，此时需要规格化
                    isAllZero = false;
                }
            } else if (round == 1 || sticky == 1) { // 尾数最低有效位为0且(舍入位为1或粘贴位为1)
                mResult++; // 尾数最低有效位为0，所以加1后不会尾数的真值的位数不变

            }
        }

        String eResultString;
        if (isAllZero) { // 为真则阶码全为0
            eResultString = "00000000000";
        } else {
            eResultString = Integer.toUnsignedString(eResult + 1023, 2);
            // 规格化数的阶码为阶码的真值加1023
        }
        while (eResultString.length() < 11) {
            eResultString = "0" + eResultString; // 阶码补足11位
        }

        String mResultString = Long.toBinaryString(mResult);
        while (mResultString.length() < 53) {
            // 当阶码全为0时，有尾数的真值小于53位，此时转为字符串后要在前面加"0"补足53位
            mResultString = "0" + mResultString;
        }

        return sResult + eResultString + mResultString.substring(1, 53);
        // 返回和的IEEE754编码，其中隐藏尾数的最高位
    }

    public static String DoubledDivideToHexIEEE754(double dividend, double divisor) { // 返回两个浮点数相乘的积的十六进制IEEE754编码
        return BinaryToHex(IEEE754BinarydDivide(HexToBinary(doubleToIEEE754Hex(dividend)),
                HexToBinary(doubleToIEEE754Hex(divisor))));
    }
}