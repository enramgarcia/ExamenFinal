package com.atechsa.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Usuario {

	private int maxPassword = 999999;
	private int minPassword = 100000;
	private int minPasswordLength = 6;
	
	protected String username = "";
	protected String password = "";
	protected String passwordInicial;
	protected String fechaIngreso;
	protected String fechaEgreso;
	
	public Cuenta cuenta;
	
	public Usuario(String username, double saldo) {
		crearUsuario();
		this.username = username;
		cuenta = new Cuenta(saldo);
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	private String generarPassword() {
		return "" + ((int) ((Math.random() * (this.maxPassword - this.minPassword)) + this.minPassword));
	}
	
	private void crearUsuario() {
		this.passwordInicial = this.password = generarPassword();
		System.out.println(String.format("Su contraseña es: %s.", this.passwordInicial));
	}
	
	private String generarFecha() {
		return (new SimpleDateFormat("Y-MM-dd HH:mm:ss")).format(new Date());
	}
	
	private void mostrarFecha(String str) {
		String fecha = (new SimpleDateFormat("Y-MM-dd HH:mm:ss")).format(new Date());
		
		System.out.println(String.format(str, fecha));
	}
	
	public void mostrarFechaIngreso() {
		this.fechaIngreso = generarFecha();
		mostrarFecha(String.format("La fecha de su ingreso es: %s.", this.fechaIngreso));
	}
	
	public void mostrarFechaEgreso() {
		this.fechaEgreso = generarFecha();
		mostrarFecha(String.format("La fecha de su egreso es: %s.", this.fechaEgreso));
	}
	
	public boolean necesitaCambiarPassword() {
		return password.equals(passwordInicial);
	}
	
	public void cambiarPassword() throws IOException {
		BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
		String password, confirmarPassword;
		
		System.out.println("Por favor ingrese su nueva contraseña: ");
		password = bfr.readLine();
		
		if (password.length() < this.minPasswordLength) {
			
			System.out.println(
					String.format("Su contraseña debe de ser al menos %s caracteres.", this.minPasswordLength)
					);
			cambiarPassword();
			
			return;
		}

		if (password.equals(this.passwordInicial)) {
			
			System.out.println("Su contraseña no puede ser la misma que la inicial.");
			cambiarPassword();
			
			return;
		}
		
		if (password.equals(this.password)) {
			
			System.out.println("Su contraseña no puede ser la misma que la actual: ");
			cambiarPassword();
			
			return;
		}
		
		System.out.println("Por favor confirme su contraseña: ");
		confirmarPassword = bfr.readLine();
		
		if (!password.equals(confirmarPassword)) {
			
			System.out.println("Sus contraseñas no coinciden, por favor vuelva a intentar.");
			cambiarPassword();
			return;
		}
		
		this.password = password;
		System.out.println("Su contraseña ha sido actualizada.");
	}
}
