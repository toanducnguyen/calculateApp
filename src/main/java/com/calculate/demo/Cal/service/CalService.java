package com.calculate.demo.Cal.service;


import com.calculate.demo.Cal.entity.CalEntity;
import com.calculate.demo.Cal.repository.CalRepository;
import com.calculate.demo.Epression.entity.ExpressionEntity;
import com.calculate.demo.Epression.repository.ExpressionRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@AllArgsConstructor
public class CalService {
    private CalRepository calRepository;
    private ExpressionRepository expressionRepository;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CalInput{
        private String keyA;
        private String value;
    }

    public  void createData(CalInput calInput){
        CalEntity calEntity = new CalEntity(calInput.keyA,calInput.value);
        calRepository.save(calEntity);
    }
    public  String getAllValue(String keyA){
        List<CalEntity> calEntityList = calRepository.findByKeyA(keyA);
        if (!calEntityList.isEmpty()){
            int res=0;
            for (CalEntity c:calEntityList
            ) {
                String x = c.getValue();
                if (x.isBlank()) {
                    res += 0;
                } else{
                    if (!isNum(x)) {
                        res +=0;
                    } else {
                        res += Integer.valueOf(c.getValue());
                    }
                }
            }
            return String.valueOf(res);
        }else {
            return "0";
        }
    }

    public int calData(String expression){
        String result= "";
        String x = expression.replace("=","");
        String ex = x.replace("%2F","/");
        for (int i = 0;i<ex.length();i++){
            if (Character.isLetter(ex.charAt(i))) {
                result += getAllValue(String.valueOf(ex.charAt(i))) ;
            } else {
                result += ex.charAt(i);
            }
        }

        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int key_response= splitOp(result);
        ExpressionEntity expressionEntity = new ExpressionEntity(dateFormat.format(date),ex,key_response);
        expressionRepository.save(expressionEntity);
        return splitOp(result);
    }
    public static int splitOp(String s) {
        if (isNum(s)) {
            return Integer.parseInt(s);
        } else {
            if (s.contains("(")){
                return splitBracket(s);
            }else if (s.contains("--") || s.contains("+-") || s.contains("-+") || s.contains("*-") || s.contains("/-") ) {
                return splitOp(changeSign(s));
            }
            else if (s.contains("+")) {
                return splitPlus(s);
            }
            else if (s.contains("-")) {
                return splitMinus(s);
            }
            else if (s.contains("/")) {
                return splitDivide(s);
            }
            else if (s.contains("*")) {
                return splitMultiply(s);
            }else if (s.contains("k")) {
                return splitK(s);
            }else if (s.contains("l")) {
                return splitL(s);
            }
            return 0;
        }
    }
    public static int splitBracket(String s){
        int count = 0;

        List<Brackets> bracketsList = new ArrayList<>();
        for (char c : s.toCharArray()){
            if (String.valueOf(c).equals("(") ||String.valueOf(c).equals(")")){
                Brackets brackets= new Brackets();
                brackets.setKey(String.valueOf(c));
                brackets.setValue(count);
                bracketsList.add(brackets);
            }
            count++;
        }
        int viTriDau=0;
        int viTriCuoi=0;
        for (Brackets br:bracketsList)
        {

            if(br.getKey().equals(")"))
            {
                viTriCuoi=br.getValue();
                Brackets brs= new Brackets();
                int vitri= bracketsList.indexOf(br);
                brs= bracketsList.get(vitri-1);
                viTriDau=brs.getValue();
                break;
            }
        }

        String data= s.substring(viTriDau,viTriCuoi+1);
        String[] tokens = data.split("[()]");
        int rs = 0;
        for (String token : tokens) {
            rs += splitOp(token);
        }
        return splitOp(s.replace(data,String.valueOf(rs)));
    }
    public static  class Brackets{
        public String key;
        public int value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
    public static String changeSign(String s) {
        if (s.contains("--")) {
            return s.replace("--", "+");
        } else if (s.contains("+-")) {
            return s.replace("+-", "-");
        } else if (s.contains("-+")) {
            return s.replace("-+", "-");
        } else if (s.contains("*-")) {
            return s.replace("*-", "k");
        } else if (s.contains("/-")) {
            return s.replace("/-", "l");
        }
        return s;
    }
    public static int splitPlus(String s) {
        int res = 0;
        String[] arr = s.split("\\+");
        for (String str : arr) {
            res += splitOp(str);
        }
        return res;
    }
    public static int splitMinus(String s) {
        String[] arr = s.split("-");
        int res = splitOp(arr[0]);
        for (int i = 1 ; i < arr.length; i++) {
            res -= splitOp(arr[i]);
        }
        return res;
    }
    public static int splitDivide(String s) {
        String[] arr = s.split("/");
        return splitOp(arr[0])/splitOp(arr[1]);
    }
    public static int splitMultiply(String s) {
        String[] arr = s.split("\\*");
        int res = 1;
        for (String str : arr) {
            res *= splitOp(str);
        }
        return res;
    }
    public  static  int splitK(String s){
        String[] arr = s.split("k");
        int res = 1;
        for (String str : arr) {
            res *= splitOp(str);
        }
        return -res;
    }
    public static int splitL(String s) {
        String[] arr = s.split("l");
        return -splitOp(arr[0])/splitOp(arr[1]);
    }
    public static boolean isNum(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
