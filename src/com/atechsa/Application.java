package com.atechsa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {
	
	protected static Banco banco;
	protected static BufferedReader bfr;
	
	public static void main(String[] args) throws IOException {
		banco = new Banco();
		bfr = new BufferedReader(new InputStreamReader(System.in));
		
		menuPrincipal();

		System.out.println("Adios.");
		System.in.read();
	}
	
	public static void limpiarPantalla() {
	}
	
	public static void continuar() throws IOException {
		System.out.println("Presione enter para continuar...");
		bfr.readLine();
	}
	
	public static void menuPrincipal() throws IOException {
		int opcion;
		
		do {
			limpiarPantalla();
			
			System.out.println("\t\tBienvenido a Banco UIP");
			System.out.println("1- Iniciar Sesión");
			System.out.println("2- Crear Usuario");
			System.out.println("3- Salir");
			
			opcion = Integer.parseInt(bfr.readLine());
			
			switch(opcion) {
				case 1:
					
					if(!banco.iniciarSesion()) {
						System.err.println("El sistema saldra debido a que ha fallado ingresar muchas veces.");
						return;
					}
					
					menuUsuario();
					break;
					
				case 2:
					if (!banco.crearUsuario()) {
						System.err.println("El sistema saldra debido a que ha fallado ingresar muchas veces.");
						return;
					}
					
					menuUsuario();
					break;
					
				case 3:
					return;
			}
			
			System.out.print("Desea salir del sistema? (s/N): ");
		} while (bfr.readLine().toLowerCase().equals("n"));
	}
	
	public static void menuUsuario() throws IOException {
		int opcion;
		
		do {
			System.out.println(
					String.format("Bienvenido(a), %s ingrese una opción del menu", banco.usuarioActual.getUsername())
					);
			banco.usuarioActual.mostrarFechaIngreso();
			
			if (banco.usuarioActual.necesitaCambiarPassword()) {
				banco.usuarioActual.cambiarPassword();
			}
			
			System.out.println("1- Ver movimientos");
			System.out.println("2- Soliticar prestamo");
			System.out.println("3- Cambiar contraseña");
			System.out.println("4- Cerrar sesión");
			
			opcion = Integer.parseInt(bfr.readLine());
			
			switch (opcion) {
				case 1:
					banco.usuarioActual.cuenta.mostrarMovimientos();
					continuar();
					break;
					
				case 2:
					banco.pedirPrestamo();
					continuar();
					break;
					
				case 3:
					banco.usuarioActual.cambiarPassword();
					continuar();
					break;
					
				case 4:
					banco.usuarioActual.mostrarFechaEgreso();
					banco.usuarioActual = null;
					return;
			}
			
		} while(true);
	}
}
