package chatTCP;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class AppServidor {

	public static void main(String[] args) {
		VentanaServidor Vserver = new VentanaServidor();
		Vserver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

class VentanaServidor extends JFrame implements Runnable {
	JTextArea AreaTexto = new JTextArea();

	public VentanaServidor() {
		setTitle("Servidor");
		setBounds(700, 300, 500, 500);

		JPanel miLamina = new JPanel();
		miLamina.setLayout(new BorderLayout());

		miLamina.add(AreaTexto, BorderLayout.CENTER);
		add(miLamina);

		setVisible(true);

		Thread HiloRecep = new Thread(this);
		HiloRecep.start();

	}

	public void run() {
		

		try {
			ServerSocket servidor = new ServerSocket(9999);
			while (true) {

				Socket miSocket = servidor.accept();
				DataInputStream flujoEntrada = new DataInputStream(miSocket.getInputStream());
				String Mensaje = flujoEntrada.readUTF();
				AreaTexto.append("\n" + Mensaje);
				miSocket.close();
				
				Socket SocketEnvio = new Socket("localhost", 9090);
				DataOutputStream flujoSalida = new DataOutputStream(SocketEnvio.getOutputStream());
				flujoSalida.writeUTF("Mensaje recibido por el sservidor");
				flujoSalida.close();
			}
			
			

		} catch (IOException IOe) {
			System.out.println(IOe.getMessage());
			IOe.printStackTrace();
		}

	}
}
