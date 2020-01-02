import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Menu {
    // variáveis de instância
    private String menu;
    private int op;

    /** Apresentar o menu */
    public void showMenu() {
        switch(op){
            case 0: System.out.println("************* MENU ****************\n"+
                                       "* 1 - Iniciar Sessao              *\n"+
                                       "* 2 - Registar como Comprador     *\n"+
                                       "* 3 - Registar como Vendedor      *\n"+
                                       "* m - Mostrar o Menu              *\n"+
                                       "* 0 - Sair                        *\n"+
                                       "***********************************\n");
                    break;
            case 1: System.out.println("************* COMPRADOR ***********\n"+
                                       "* 1 - Licitar um Leilao           *\n"+
                                       "* 2 - Consultar Leiloes           *\n"+
                                       "* m - Mostrar o Menu              *\n"+
                                       "* 0 - Terminar Sessao             *\n"+
                                       "***********************************\n");
                    break;
            case 2: System.out.println("************* VENDEDOR ************\n"+
                                       "* 1 - Iniciar Leilao              *\n"+
                                       "* 2 - Consultar Leiloes           *\n"+
                                       "* 3 - Encerrar Leilao             *\n"+
                                       "* m - Mostrar o Menu              *\n"+
                                       "* 0 - Terminar Sessao             *\n"+
                                       "***********************************\n");
                    break;
        }
    }

    public int getOp(){
        return this.op;
    }

    public void setOp(int n){
        this.op=n;
    }

}
