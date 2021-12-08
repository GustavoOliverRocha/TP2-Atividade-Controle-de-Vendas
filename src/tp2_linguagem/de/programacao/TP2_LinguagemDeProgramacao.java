package tp2_linguagem.de.programacao;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Trabalho Prático 2 Linguagem de Programação
 * Profº Ciro Cirne Trindade
 * @author Gustavo Oliveira Rocha
 * 
 */

public class TP2_LinguagemDeProgramacao {  
    
    public static void main(String[] args) {
        
        Compra cmp = new Compra();
        Cliente c = new Cliente();
        int op = 0;
        Scanner sc = new Scanner(System.in);
        
         try
         {
            while(op < 1 || op > 6)
            {
               System.out.println("--------Controle de Vendas--------");
               System.out.println("Opções:");
               System.out.println("[1]- Inserir Clientes;");
               System.out.println("[2]- Listar Clientes;");
               System.out.println("[3]- Consultar Cliente por CPF;");
               System.out.println("[4]- Realizar Compra;");
               System.out.println("[5]- Listar Compras pela data;");
               System.out.println("[6]- Listar Compras pelo CPF.");
               op = sc.nextInt();
            }
         }
         catch(InputMismatchException e)
         {
            System.out.println("Error: Entre com um numero valido de 1 a 6.");
         }
         switch(op)
         {
             case 1:c.inserirCliente(sc);break;
             case 2:c.listar();break;
             case 3:c.find(sc);break;
             case 4:cmp.inserirCompra(sc);break;
             case 5:cmp.listarData(sc);break;
             case 6:cmp.listarCpf(sc);break;
         }
    }
}
