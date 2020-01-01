package Servidor;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Menu {
    private String menu;
    private int option;

    public void show() {
        switch(option){
            case 0: System.out.println("#---------------MENU--------------#\n"+
                                       "| 1 - Login                       |\n"+
                                       "| 2 - Create new User             |\n"+
                                       "| 0 - Exit                        |\n"+
                                       "#---------------------------------#\n");
                    break;
            case 1: System.out.println("#------------COMPRADOR------------#\n"+
                                       "| 1 - Upload Music File           |\n"+
                                       "| 2 - Download Music File         |\n"+
                                       "| 3 - Search Music Files          |\n"+
                                       "| 0 - Logout                      |\n"+
                                       "#---------------------------------#\n");
                    break;
        }
    }

    public int getOption(){
        return this.option;
    }

    public void setOption(int o){
        this.option = o;
    }
}

