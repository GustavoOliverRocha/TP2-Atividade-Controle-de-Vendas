package tp2_linguagem.de.programacao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Gustavo Oliveira
 */
/**
 * @author Gustavo Oliveira
 * Serializable: é um padrão que permite que o objeto da classe
 * seja transformado em uma sequencia de bytes
 * Isso serve para gravar arquivos em disco ou em rede
 */
public class Cliente implements Serializable{
    
    //Atributos
    private String cpf_cliente;
    private String nm_cliente;
    private String tel_cliente;
    
    //Construtores
    public Cliente()
    {
        
    }
    public Cliente(String cpf,String nm,String tel)
    {
        this.cpf_cliente = cpf;
        this.nm_cliente = nm;
        this.tel_cliente = tel;
        
    }
    
    //Getters e Setters
    public String getCpf_cliente() {
        return cpf_cliente;
    }

    public void setCpf_cliente(String cpf_cliente) {
        this.cpf_cliente = cpf_cliente;
    }

    public String getNm_cliente() {
        return nm_cliente;
    }

    public void setNm_cliente(String nm_cliente) {
        this.nm_cliente = nm_cliente;
    }

    public String getTel_cliente() {
        return tel_cliente;
    }

    public void setTel_cliente(String tel_cliente) {
        this.tel_cliente = tel_cliente;
    }
    
    
    /**
     * Metodos
     */
    

   /**
    * listar(): Vai ser responsavel apenas por executar o metodo listarClientes()
    *   e renderizar o vetor retornado pelo mesmo.
    * 
    * listarClientes():vai retornar um vetor contendo um objeto do tipo Cliente
    * onde neles terão os dados do arquivo ClienteCsv.txt
    */

   public void listar()
   {
       List<Cliente> lt_clientes = this.listarClientes();
       for(Cliente c : lt_clientes)
            System.out.println(c);
   }
   /**
    * lst_cliente: é o vetor que vai conter os objetos
    * vt_linha: vai receber os atributos da linha após ser quebrada pelo .split()
    * a variavel String linha vai receber a linha do arquivo e será quebrada pelo metodo split()
    * @return 
    */
    public List listarClientes()
    {
        List<Cliente> lst_cliente = new ArrayList();
        String [] vt_linha;
           try(BufferedReader br = new BufferedReader(new FileReader("ClienteCsv.txt")))
           {
               String linha = br.readLine();
               linha = br.readLine();
               while(linha != null)
               {
                   vt_linha = linha.split(";");
                   Cliente c = new Cliente(vt_linha[0],vt_linha[1],vt_linha[2]);
                   lst_cliente.add(c);
                   linha = br.readLine();
               }
           }
           catch(IOException e)
           {
               System.out.println("Error: " + e.getMessage());
           }
            return lst_cliente;
    }
    
    /**
     * find(): é a "interface" para inserir o nome do cliente como parametro
     * do metodo findCliente()
     * @param sc 
     */
    public void find(Scanner sc)
    {
        System.out.println("Digite o primeiro Nome do Cliente: ");
        this.findCliente(sc.next());
    }
    
    /**
     * findCliente(): vai exibir os dados do cliente pelo
     * primeiro nome inserido no parametro
     * 
     * e durante este "foreach" caso a primeira palavra do
     * atributo seja igual ao nome inserido ele vai exibir na tela
     * 
     * caso o i seja igual a zero significa que o cliente com aquele primeiro
     * nome não existe
     *  
     * @param c
     */
    public void findCliente(String c)
    {
        int i = 0;
        List<Cliente> list = listarClientes();
        for(Cliente k : list)
        {
            if(k.nm_cliente.startsWith(c))
            {
                System.out.println(k);
                i++;
            }   
        }
        if(i == 0)
            System.out.println("Usuario não encontrado.");
    }
    

    /**
     * inserirCliente(): vai responsavel pelo dados que vão ser inseridos no
     * arquivo, o usuario vai digitar o CPF,Nome e o Telefone
     * CPF não pode estar existir ja no arquivo
     * e caso todos os campos não estejam vazio ele vai executar
     * o metodo save() que vai persistir de fato no arquivo ClienteCsv.txt
     */
    public void inserirCliente(Scanner sc)
    {
        sc = new Scanner(System.in);
        System.out.println("--------Inserção de cliente--------");
        System.out.println("Entre com o CPF: ");
        
        this.cpf_cliente = this.formatar(sc.nextLine());
        
        if(cpfExists(this.cpf_cliente))
        {   
            System.out.println("CPF ja cadastrado");
            System.exit(0);
        }
        
        System.out.println("Entre com o Nome: ");
        this.nm_cliente = this.formatar(sc.nextLine());
        
        System.out.println("Entre com o Telefone: ");
        this.tel_cliente = this.formatar(sc.nextLine());
        
        if(!this.cpf_cliente.equals("") &&
                !this.nm_cliente.equals("") && !this.tel_cliente.equals(""))
            this.save();
        
    }
    /**
     * save(): Aqui serão onde os dados do cliente ja inserido pelo usuario
     * vão ser persistidos no arquivo
     * 
     * Através da lista "list" ele receberá todas as linhas(registros) que ja tão no arquivo
     * e então através do metodo .add() será inserido o novo objeto
     */
    public void save()
    {
        List<Cliente> list = listarClientes();
        list.add(this);
        try (PrintStream ps = new PrintStream("ClienteCsv.txt")) {
            ps.println("cpf_cliente;nm_cliente;tel_cliente");
            for(Cliente c : list)
                ps.println(c.cpf_cliente+";"+c.nm_cliente+";"+c.tel_cliente);
            
            ps.close();
        }
        catch(IOException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    //Metodo para verificar se ja existe um CPF cadastrado no arquivo cliente
    public boolean cpfExists(String cpf)
    {
        List<Cliente> list = listarClientes();
        for(Cliente k : list)
        {
            if(k.cpf_cliente.equals(cpf))
                return true;
        }
        return false;
    }
    
    //Metodo para retirar o ';' caso o usuario tenha digitado
    //para evitar dar erro no arquivo
    public String formatar(String st)
    {
        return st.replace(";", "");
    }
   @Override
    public String toString() {
        return "CPF: " + cpf_cliente + " | Nome: " + nm_cliente + " | Telefone: " + tel_cliente;
    }
}
