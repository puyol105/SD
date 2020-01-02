import java.io.*;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.locks.*;

public class ThreadClienteInput extends Thread{
	
	private BufferedReader ler_teclado;
	private PrintWriter escrever_socket;
	private Socket socket;
	private Menu menu;
	private ReentrantLock lock; 
	private Condition c;

	public ThreadClienteInput(Socket socket, Menu menu, ReentrantLock l, Condition c){
		try{
			this.ler_teclado = new BufferedReader(new InputStreamReader(System.in)); 
			this.escrever_socket = new PrintWriter(socket.getOutputStream(),true);
			this.socket = socket; 
			this.menu = menu;
			this.lock = l;
			this.c = c;
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	}

	public void run(){
		
		String input = null;

		try{
			menu.showMenu();
			while((input = ler_teclado.readLine())!= null){
				if(menu.getOp() == 0){							// Não tem sessão iniciada
					if(input.equals("1")){						// Iniciar sessão
						escrever_socket.println("iniciar_sessao");
						System.out.print("Username: ");
						input = ler_teclado.readLine();
						escrever_socket.println(input);

						System.out.print("Password: ");
						input = ler_teclado.readLine();
						escrever_socket.println(input);
						
						this.lock.lock();
						c.await();
						this.lock.unlock();
						input="1";
					}
					else if(input.equals("2")){					// Registar como comprador
						escrever_socket.println("registar_comprador");
						System.out.print("Username: ");
						input = ler_teclado.readLine();
						escrever_socket.println(input);

						System.out.print("Password: ");
						input = ler_teclado.readLine();
						escrever_socket.println(input);
						input="2";
					}
					else if(input.equals("3")){					// Registar como vendedor
						escrever_socket.println("registar_vendedor");
						System.out.print("Username: ");
						input = ler_teclado.readLine();
						escrever_socket.println(input);

						System.out.print("Password: ");
						input = ler_teclado.readLine();
						escrever_socket.println(input);
						input="3";
					}
					else if(input.equals("0")){					// Sair
						break;
					}
					if(input.equals("1") || input.equals("2") || input.equals("3") || input.equals("0") || input.equals("m")){ 
						space();
						menu.showMenu();
					}
					else System.out.println("Opção inválida.");
				}
				else if(menu.getOp() == 1){						// Comprador logado.
					if(input.equals("1")){						// Licitar 
						escrever_socket.println("licitar");
						System.out.print("ID-Leilao: ");
						input = ler_teclado.readLine();
						escrever_socket.println(input);

						System.out.print("Valor: ");
						input = ler_teclado.readLine();
						escrever_socket.println(input);
						input="1";
					}
					else if(input.equals("2")){					// Consultar leilão
						escrever_socket.println("consultar_leilao");
					}
					else if(input.equals("0")){					// Terminar sessão
						break;
					}
					if(input.equals("1") || input.equals("2") || input.equals("0") || input.equals("m")){ 
						space();
						menu.showMenu();
					}
					else System.out.println("Opção inválida.");
				}
				else if(menu.getOp() == 2){						// Vendedor logado.
					if(input.equals("1")){						// Iniciar leilão
						escrever_socket.println("iniciar_leilao");
						System.out.print("Descrição: ");
						input = ler_teclado.readLine();
						escrever_socket.println(input);
						input="1";
					}
					else if(input.equals("2")){					// Consultar
						escrever_socket.println("consultar_leilao");
					}
					else if(input.equals("3")){					// Encerrar leilão
						escrever_socket.println("encerrar_leilao");
						System.out.print("ID-Leilao: ");
						input = ler_teclado.readLine();
						escrever_socket.println(input);
						input="3";
					}
					else if(input.equals("0")){					// Terminar sessão
						break;
					}
					if(input.equals("1") || input.equals("2") || input.equals("3") || input.equals("0") || input.equals("m")){ 
						space();
						menu.showMenu();		
					}
					else System.out.println("Opção inválida.");
				}
			}
			socket.shutdownOutput();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}

	}

	private void space(){
		for(int i = 0;i<40;i++)
			System.out.println();
	}
}