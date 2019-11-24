package Test;

import BDlogical.UsersRepository;
import BDlogical.UsersRepositoryImpl;
import Model.User;

public class Tester {
    public static void main(String[] args) {
        String s = "       ";
        String[] split = s.split(" ");

        String result = "";
        for(String i : split){
            result += i;
        }

        System.out.println(result);
        System.out.println(result.equals(""));
    }
}
