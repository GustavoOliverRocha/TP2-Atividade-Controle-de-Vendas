package tp2_linguagem.de.programacao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Gustavo Oliveira
 */
public class Compra {
    
    //Atributos
    private String cpf_cliente;
    private String dt_compra;
    private double vl_compra;
    private Cliente cli;

    //Construtores
    public Compra()
    {
        this.cli = new Cliente();
    }
    public Compra(String cpf_cliente, String dt_compra, double vl_compra)
    {
        this.cpf_cliente = cpf_cliente;
        this.dt_compra = dt_compra;
        this.vl_compra = vl_compra;
    }

    //Getters e Setters
    public String getCpf_cliente() {
        return cpf_cliente;
    }

    public void setCpf_cliente(String cpf_cliente) {
        this.cpf_cliente = cpf_cliente;
    }

    public String getDt_compra() {
        return dt_compra;
    }

    public void setDt_compra(String dt_compra) {
        this.dt_compra = dt_compra;
    }

    public double getVl_compra() {
        return vl_compra;
    }

    public void setVl_compra(double vl_compra) {
        this.vl_compra = vl_compra;
    }
    
    
    /**
     * listarCompras()
     * @return 
     */
    public List listarCompras()
    {
        List<Compra> lt_compra = new ArrayList();
        String [] vt_linha;
           try(BufferedReader br = new BufferedReader(new FileReader("CompraCsv.txt")))
           {
               String linha = br.readLine();
               linha = br.readLine();
               while(linha != null)
               {
                   vt_linha = linha.split(";");
                   String cpf = vt_linha[0];
                   String data = vt_linha[1];
                   double valor = Double.parseDouble(vt_linha[2]);
                   Compra c = new Compra(cpf,data,valor);
                   lt_compra.add(c);
                   linha = br.readLine();
               }
           }
           catch(FileNotFoundException e)
           {
               System.out.println("Error: Arquvio não encontrado.");
           }
           catch(IOException e)
           {
               System.out.println("Error: " + e.getMessage());
           }
            return lt_compra;
    }
    
    /**
     * inserirCompra(Scanner sc)
     * @param sc 
     */
    public void inserirCompra(Scanner sc)
    {
        sc = new Scanner(System.in);
        
        System.out.println("---------Compra---------");
        
        System.out.println("Entre com o CPF do Cliente cadastrado: ");
        this.cpf_cliente = cli.formatar(sc.nextLine());
        
        if(!cli.cpfExists(this.cpf_cliente))
        {   
            System.out.println("Error: Este CPF não está cadastrado no sistema.");
            System.exit(0);
        }
        
        String nm_cliente = this.findCliente(this.cpf_cliente);
        System.out.println("Seja Bem vindo " + nm_cliente);
        
        System.out.println("Entre com a Data da Compra: ");
        this.dt_compra = cli.formatar(sc.nextLine());
        
        System.out.println("Entre com o Valor R$: ");
        this.vl_compra = Double.parseDouble(sc.nextLine());
        
        if(!this.cpf_cliente.equals("") &&
                !this.dt_compra.equals("") && this.vl_compra > 0)
            this.save();
        else
        {
            System.out.println("Erro nos valores inseridos.");
            System.out.println("-Campos não podem ficar vazios");
            System.out.println("-O valor da compra tem que ser maior que R$ 0");
        }
        
    }
    
    /**
     * save()
     */
    public void save()
    {
        List<Compra> list = listarCompras();
        list.add(this);
        try (PrintStream ps = new PrintStream("CompraCsv.txt")) {
            ps.println("cpf_cliente;dt_compra;vl_compra");
            for(Compra c : list)
                ps.println(c.cpf_cliente+";"+c.dt_compra+";"+c.vl_compra);
            ps.close();
        }
        catch(IOException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * findCliente(String cpf)
     * @param cpf
     * @return 
     */
    public String findCliente(String cpf)
    {
        List<Cliente> list = cli.listarClientes();
        for(Cliente k : list)
        {
            if(k.getCpf_cliente().equals(cpf))
                return k.getNm_cliente();
        }
        return null;
    }
    
    
    /**
     * listarData(Scanner sc)
     * @param sc 
     */
    public void listarData(Scanner sc)
    {
        System.out.println("Entre com a Data de Compra: ");
        listByDate(sc.next());
    }
    
    
    /**
     * listByDate(String data)
     * @param data 
     */
    public void listByDate(String data)
    {
        int i = 0;
        List<Compra> lt_compras = listarCompras();
        List<Cliente> lt_cliente = cli.listarClientes();
        double total = 0;
        System.out.println("--------Compras em " + data+"--------");
        for(Compra k : lt_compras)
        {
            if(k.dt_compra.equals(data))
            {
                String cpf = lt_cliente.get(i).getCpf_cliente();
                String nome = lt_cliente.get(i).getNm_cliente();
                System.out.println("CPF: " + cpf + " | Nome: "+ nome +" | Valor: R$ " + k.vl_compra);
                total += k.vl_compra;
                i++;
            }   
        }
        if(i > 0)
            System.out.println("Total: R$" + total);
        if(i == 0)
            System.out.println("Nenhuma Compra realizada nessa data.");
    }
    
    
    /**
     * listarCpf(Scanner sc)
     * @param sc 
     */
    public void listarCpf(Scanner sc)
    {
        System.out.println("Entre com o CPF do Cliente: ");
        listByCpf(sc.next());
    }
    
    
    /**
     * listByCpf(String cpf)
     * @param cpf 
     */
    public void listByCpf(String cpf)
    {
        int i = 0;
        List<Compra> lt_compras = listarCompras();
        List<Cliente> lt_cliente = cli.listarClientes();
        double total = 0;
        String cliente = "";
        
        if(cli.cpfExists(cpf))
        {
            for(Cliente c : lt_cliente)
            {
                if(c.getCpf_cliente().equals(cpf))
                    cliente = c.getNm_cliente();
            }
            System.out.println("--------Compras Realizadas por "+cliente+"--------");
            for(Compra k : lt_compras)
            {
                if(k.cpf_cliente.equals(cpf))
                {
                    String data = k.dt_compra;
                    double val = k.vl_compra;
                    System.out.println("Data da Compra: "+ data +" | Valor: R$" + val);
                    total += k.vl_compra;
                    i++;
                }   
            }
            if(i > 0)
                System.out.println("Total: R$ " + total);
            if(i == 0)
                System.out.println("Este cliente não realizou nenhuma compra.");
        }
        else
            System.out.println("Error: CPF não cadastrado no sistema.");
    }
    
    @Override
    public String toString() {
        return "CPF do Cliente: " + cpf_cliente + " | Data de Compra: " + dt_compra + " | Valor: R$ " + (vl_compra);
    }
    
    
    
    
    
}
