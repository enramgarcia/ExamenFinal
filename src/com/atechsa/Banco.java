package com.atechsa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.atechsa.models.Usuario;

public class Banco {

	protected BufferedReader bfr;
	protected Usuario usuarioActual;
	protected double capitalOnHold = 0;
	protected ArrayList<Usuario> usuarios;
	
	public Banco() {
		usuarios = new ArrayList<Usuario>();
		bfr = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public boolean iniciarSesion() throws IOException {
		if (usuarios.size() == 0) {
			
			System.out.println("No existen usuarios en el sistema actualmente.\n"
					+ "Redireccioniando a crear usuario.");
			return crearUsuario();
		}
		
		return intentarIniciarSesion(1);
	}
	
	private boolean intentarIniciarSesion(int intentos) throws IOException {
		if (intentos == 4) {
			return false;
		}
		
		boolean exito = false;
		String username, password;
		
		System.out.print("Usuario: ");
		username = bfr.readLine();
		System.out.print("Password: ");
		password = bfr.readLine();
		
		for (Usuario usuario: usuarios) {
			if (usuario.getUsername().equals(username) && usuario.getPassword().equals(password)) {
				usuarioActual = usuario;
				return true;
			}
		}
		
		if (!exito) {
			System.out.println("Lo sentimos los valores que ha ingresado no concuerdan con el sistema.");
			return intentarIniciarSesion(intentos + 1);
		}
		
		return false;
	}
	
	public boolean validarUsername(String username) {
		for (Usuario usuario : usuarios) {
			if (usuario.getUsername().equals(username)) {
				return false;
			}
		}		
		
		return true;
	}

	public boolean crearUsuario() throws NumberFormatException, IOException {
		System.out.println("Para poder crear su usuario debe depositar un mínimo de $1500.00,"
				+ " de no cumplir con el mínimo cualquier monto inferior que ingrese lo sacara de este menu.");
		System.out.print("Ingrese monto a depositar: ");
		
		double monto = Double.parseDouble(bfr.readLine());
		
		if (monto < 1500) {
			return false;
		}
		
		String username;
		
		do {
			System.out.print("Usuario: ");
			username = bfr.readLine();
			
			if (validarUsername(username)) {
				break;
			}
			
			System.err.println("El usuario ya existe.");
		} while (true);
		
		Usuario usuario = new Usuario(username, monto);
		this.usuarios.add(usuario);
		
		System.out.println("Su usuario se ha creado, ingrese al sistema con su clave.");
		
		return intentarIniciarSesion(1);
	}
	
	public boolean validarCantidadPrestamo(double capitalEnBanco, double monto) {
		return ((capitalEnBanco - this.capitalOnHold) * 0.90) > monto;
	}
	
	public void pedirPrestamo()
			throws NumberFormatException, IOException {
		
		if (this.usuarios.size() - 1 == 0) {
			System.err.println("Lo sentimos, pero el banco no cuenta con el capital para hacerle un prestamo.");
			return;
		}
		
		double monto, total, capitalEnBanco = 0;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Ingrese el monto a del prestamo: ");
		monto = Double.parseDouble(reader.readLine());
		
		for (Usuario usuario : usuarios) {
			if (usuario.getUsername().equals(usuarioActual.getUsername())) {
				continue;
			}
			
			capitalEnBanco += usuario.cuenta.getSaldo();
		}
		
		if (!validarCantidadPrestamo(capitalEnBanco, monto)) {
			System.err.println("Lo sentimos, pero el banco no cuenta con el capital para hacerle un prestamo.");
			return;
		}
		
		total = monto * 1.30;
		
		System.out.println(String.format(
				"Para aceptar el prestamo de $%.2f se le cobrara una comisión del 30%%, haciendo el valor total de su prestamo de $%.2f.",
				monto, total
				));
		System.out.print("Desea continuar con el prestamo? (s/N): ");

		if (reader.readLine().toLowerCase().equals("s")) {
			
			System.out.println("Gracias por aceptar el prestamo.");
			capitalOnHold += capitalEnBanco;
			usuarioActual.cuenta.agregarPrestamo(monto);
			
		} else {
			System.out.println("El prestamo ha sido denegado por su parte.");
		}
		
	}
	
}
